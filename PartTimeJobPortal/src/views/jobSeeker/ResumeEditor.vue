<template>
    <div>
        <div id="progress"></div>
        <div class="resume-editor" ref="editorFrom">
            <h1>编辑简历</h1>
            <div>
                <!-- 基础信息 -->
                <div class="resumeSection">
                    <div>
                        <h2 class="resumeSection__title">基础信息</h2>
                    </div>
                    <div class="resumeSection__form">
                        <el-form
                            :model="resumeDetail"
                            :rules="validatorRules.basic"
                            ref="basicForm"
                        >
                            <el-form-item label="姓名" prop="resName">
                                <el-input v-model="resumeDetail.resName" placeholder="请输入姓名"></el-input>
                            </el-form-item>
                            <el-form-item label="性别">
                                <br>
                                <el-radio-group v-model="resumeDetail.resSex">
                                    <el-radio label="0">男</el-radio>
                                    <el-radio label="1">女</el-radio>
                                </el-radio-group>
                            </el-form-item>
                            <el-form-item label="手机号码" prop="resPhone">
                                <el-input v-model="resumeDetail.resPhone" placeholder="请输入手机号码"></el-input>
                            </el-form-item>
                            <el-form-item label="邮箱" prop="resEmail">
                                <el-input v-model="resumeDetail.resEmail" placeholder="请输入邮箱"></el-input>
                            </el-form-item>
                        </el-form>
                    </div>
                </div>

                <!-- 自我评价 -->
                <div class="resumeSection">
                    <div>
                        <h2 class="resumeSection__title">自我评价</h2>
                    </div>
                    <div class="resumeSection__form">
                        <div v-if="!shouldEvaluate" class="bottom-action">
                            <i
                                @click="shouldEvaluate = !shouldEvaluate"
                                class="el-icon-plus el-icon-plus-top"
                            >添加</i
                            >
                        </div>

                        <el-form v-else>
                            <el-form-item label="自我评价">
                                <el-input
                                    type="textarea"
                                    v-model="resumeDetail.resDesc"
                                    :rows="5"
                                ></el-input>
                            </el-form-item>
                            <el-form-item>
                                <div class="bottom-action">
                                    <i
                                        @click="
                      () => {
                        shouldEvaluate = !shouldEvaluate;
                        resume.self_evaluation = '';
                      }
                    "
                                        class="el-icon-delete"
                                    ></i>
                                </div>
                            </el-form-item>
                        </el-form>
                    </div>
                </div>

                <!-- 简历 -->
                <div class="resumeSection">
                    <div>
                        <h2 class="resumeSection__title">简历</h2>
                    </div>
                    <div class="resumeSection__form">
                        <el-form>
                            <el-form-item>
                                <!--                :action="`/atsx/blob/${uploadToken}/`"-->
                                <el-upload
                                    class="uploadFile"
                                    ref="resumeUploader"
                                    drag
                                    action
                                    name="content"
                                    accept=".pdf, .doc, .docx, .ppt, .pptx, .png, jpg, .jpeg"
                                    :file-list="fileList"
                                    :show-file-list="false"
                                    :before-upload="handleBeforeUpload"
                                    :on-success="handleResumeUploadSuccess"
                                    :on-change="handleResumeUploadChange"
                                    :on-progress="handleResumeUploadProgress"
                                    :on-error="handleResumeUploadError"
                                >
                                    <div class="uploadFile__resume">
                                        <div class="beforeUpload" :hidden="!hasResumeAttach">
                                            <h3>将你的简历拖拽到此处</h3>
                                            <div class="upload-button">
                                                <!--                        <bytedance-button round type="primary"-->
                                                <!--                          >选择文件</bytedance-button-->
                                                <!--                        >-->
                                                <el-button round type="primary">选择文件</el-button>
                                            </div>
                                            <div>
                                                请上传 .pdf, .doc, .docx,.ppt,.pptx,.png,jepg,jpg 文件
                                            </div>
                                        </div>
                                        <div class="afterUpload" :hidden="hasResumeAttach">
                                            <div class="fileicon">
                                                <file-icon :fileType="resumeDetail.aliyunOss.type"></file-icon>
                                            </div>
                                            <h3>
                                                {{ resumeDetail.aliyunOss.fileName + '.' + resumeDetail.aliyunOss.type }}</h3>
                                            <!--                      上次上传：{{ uploadData.create_time | formatDate }}-->
                                            <time class="uploadTime">
                                                上次上传：
                                            </time>
                                            <div class="afterUpload__actionButton">
                                                <span class="afterUpload__actionButton-update">更新</span>
                                                <span class="afterUpload__actionButton-dividerLine">|</span>
                                                <span @click.stop="handleRemoveUploadResume">删除</span>
                                            </div>
                                        </div>
                                    </div>
                                </el-upload>
                            </el-form-item>
                        </el-form>
                    </div>
                </div>
            </div>
        </div>
        <div
            ref="footerAction"
            :class="{ 'resumeEditor-footerAction-fix': footerActionFixed }"
            class="resumeEditor-footerAction"
        >
            <el-button round @click="$router.push('/resume')">取消</el-button>
            <el-button
                round
                :loading="submitLoading"
                size="medium"
                @click="save()"
                type="primary"
            >保存</el-button>
        </div>
    </div>
</template>

<script>
import {
    fetchResume,
    fetchSaveResume,
    fetchCommonSettings,
    fetchUploadToken,
    fetchResumeParseTaskToken,
    fetchResumeParseTaskData,
    fetchResumeAttachmentToken,
} from "@/helper/requestWithToken";
import {getOffsetTop} from "@/helper/utilities";
import store from "@/store";
import {submitUpload, fileDownLoad} from '@/utils/ossCommonMethods'


export default {
    name: "resume-editor",
    data() {
        return {
            submitLoading: false,
            uploadData: {},
            resumeUploadUpdateHintVisible: false,
            shouldEvaluate: true,
            resume: {},
            setting: {},
            withoutCareer: false,
            careerList: [],
            footerActionFixed: false,
            uploadToken: "",
            resumeUploaded: false,
            validatorRules: {
                basic: {
                    resName: {required: true, trigger: "blur", message: "请输入姓名"},
                    resPhone: [
                        {
                            required: true,
                            trigger: "blur",
                            message: "请输入手机号",
                        },
                        {
                            validator(rule, value, callback) {
                                if (!/^\d{11}$/.test(value)) {
                                    callback(new Error("请输入合法的手机号"));
                                }
                                callback();
                            },

                            trigger: "blur",
                        },
                    ],
                    resEmail: [
                        {required: true, trigger: "blur", message: "请输入邮箱"},
                        {type: "email", trigger: "blur", message: "请输入合法的邮箱"},
                    ],
                },
                award: {
                    title: {
                        required: true,
                        trigger: "submit",
                        message: "请输入获奖名称",
                    },
                },
                sns: {
                    link: {required: true, trigger: "submit", message: "请输入URL/ID"},
                    sns_type: {
                        required: true,
                        trigger: "submit",
                        message: "请选择社交平台",
                    },
                },
                languageSkill: {
                    language: [
                        {
                            required: true,
                            trigger: "submit",
                            message: "请选择语言技能",
                        },
                    ],
                    proficiency: {
                        required: true,
                        trigger: "submit",
                        message: "请选择语言精通程度",
                    },
                },
                career: {
                    company: [
                        {required: true, trigger: "submit", message: "请输入公司"},
                    ],
                    title: {required: true, trigger: "submit", message: "请输入职位"},
                    daterange: [
                        {required: true, trigger: "blur", message: "请输入日期"},
                    ],
                },
                internship: {
                    company: [
                        {required: true, trigger: "submit", message: "请输入公司"},
                    ],
                    position: {
                        required: true,
                        trigger: "submit",
                        message: "请输入职位",
                    },
                    daterange: [
                        {required: true, trigger: "blur", message: "请输入日期"},
                    ],
                },
                project: {
                    description: [
                        {required: true, trigger: "submit", message: "请输入项目描述"},
                    ],
                    name: {
                        required: true,
                        trigger: "submit",
                        message: "请输入项目名称",
                    },
                    daterange: [
                        {required: true, trigger: "blur", message: "请选择起止时间"},
                    ],
                },
                award: {
                    name: {required: true, trigger: "submit", message: "请输入公司"},
                },
                education: {
                    school: {
                        required: true,
                        trigger: "submit",
                        message: "请输入学校名称",
                    },
                    degree: {
                        required: true,
                        trigger: "submit",
                        message: "请选择学历",
                    },
                    daterange: {
                        required: true,
                        trigger: "submit",
                        message: "请选择起止时间",
                    },
                },
            },
            resumeDetail: {
                resSex: "0"
            },
            state: store.state,
            fileList: [],
            jobseekerId: 0,//记录求职者id
            hasResumeAttach: false,//是否有简历附件
        };
    },
    computed: {
        resumeFileType() {
            const patharr = this.resume.resume_attachment.name.split(".");
            return patharr[patharr.length - 1];
        },
    },

    created() {
        this.getUserResume();
    },
    mounted() {
        window.addEventListener("scroll", this.onPageScroll);

        window.addEventListener("beforeunload", this.onbeforeunloadAlert);
    },
    destroyed() {
        window.removeEventListener("scroll", this.onPageScroll);
        window.removeEventListener("beforeunload", this.onbeforeunloadAlert);
    },
    methods: {
        onPageScroll() {
            this.footerActionFixed =
                window.scrollY <
                this.footerActionShouldFixedThreshold - window.innerHeight;
        },
        async handleWorksUploadChange(file, item, index) {

        },
        handleWorksUploadProgress(event, item) {
            if (this.$refs.worksUploadProgress) {
                this.$refs.worksUploadProgress.percentage = event.percent;
            }
        },
        handleWorksBeforeUpload(file, item) {
            if (file.size > 1024 * 1024 * 100) {
                item.uploadStatus = "rejected";
                return false;
            }

            return fetchUploadToken().then((res) => {
                this.uploadToken = res.data.token;
                return true;
            });
        },
        handleWorksUploadSuccess(response, item) {
            item.works_attachment = {
                name: response.data.name,
                create_time: Date.now(),
            };
            item.uploadStatus = "resolved";
            this.$messageBox.close();

            fetchResumeAttachmentToken({attachment_id: response.data.id})
                .then((res) => {
                    item.portal_attachment_id = res.data.portal_attachment_id;
                })
                .catch((err) => {
                    throw err;
                });
        },
        handleRemoveUploadResume() {
            this.uploadData = {};
            this.resume.resume_attachment = {};
            delete this.resume.portal_attachment_id;
            this.resumeUploaded = false;
            this.resumeUploadUpdateHintVisible = false;
        },
        resolveResume() {
        },
        handleBeforeUpload() {
            console.log('handleBeforeUpload')
            // return fetchUploadToken().then((res) => {
            //   this.uploadToken = res.data.token;
            //   return true;
            // });
        },
        handleResumeUploadError(err) {
            console.log('handleResumeUploadError')
            this.$message({
                message: err.message || "网络中断",
                dangerouslyUseHTMLString: true,
                type: "error",
                // duration: 0
            });
            this.$resumeUploadPopupProgress.close();
        },
        handleResumeUploadProgress(progressEvent) {
            console.log('handleResumeUploadProgress')
            this.$resumeUploadPopupProgress.value = progressEvent.percent;
        },
        async handleResumeUploadChange(file) {
            this.fileList.push(file.raw);
            this.submitUpload();
        },
        //点击提交云端
        async submitUpload() {
            var result = await submitUpload(this.fileList, 'jobSeeker/' + this.jobseekerId + '/', 'jobSeekerResume', {
                used: '2',
                jobseekerId: this.jobseekerId
            });
            if (result.code == 200) {
                this.fileList = [];//防止提交重复数据
                //只查询简历附件回显
                this.$axios.post("/resume/getOne", {}).then(res => {
                    if (res.code == 200) {
                        this.hasResumeAttach = false
                        this.$set(this.resumeDetail.aliyunOss, 'fileName', res.data.aliyunOss['fileName'])
                        this.$set(this.resumeDetail.aliyunOss, 'filePath', res.data.aliyunOss['filePath'])
                        this.$set(this.resumeDetail.aliyunOss, 'type', res.data.aliyunOss['type'])
                    }
                })
            }
        },
        handleResumeUploadSuccess({data}) {
            console.log('handleResumeUploadSuccess')

            this.resumeUploaded = true;
            this.resumeUploadUpdateHintVisible = true;
            this.uploadData = {...data, create_time: Date.now()};

            this.resume.resume_attachment = data;

            this.$resumeUploadPopupProgress.close();
            this.$message.success({
                message: "上传成功",

                center: true,
            });
        },


        mapResumeData(data) {
            this.careerList = data.career_list;
            if (data.resume_attachment && data.resume_attachment.id) {
                data.portal_attachment_id = data.resume_attachment.id;
            }
            data.works_list &&
            data.works_list.forEach((item) => {
                if (item.works_attachment && item.works_attachment.id) {
                    item.uploadStatus = "resolved";
                } else {
                    item.uploadStatus = "pending";
                }
            });

            for (let key in data) {
                if (key.endsWith("_list")) {
                    if (!Array.isArray(data[key])) {
                        data[key] = [];
                    }

                    data[key].forEach((item) => {
                        if (typeof item !== "object") {
                            return;
                        }

                        if (
                            item.hasOwnProperty("start_time") &&
                            item.hasOwnProperty("end_time")
                        ) {
                            let {start_time, end_time} = item;

                            start_time = start_time === -1 ? Date.now() : start_time;
                            end_time = end_time === -1 ? Date.now() : end_time;

                            this.$set(item, "daterange", [
                                new Date(start_time).getTime(),
                                new Date(end_time).getTime(),
                            ]);
                        }
                    });
                }
            }

            return data;
        },
        onbeforeunloadAlert(e) {
            e.preventDefault();
            e.returnValue = false;
        },

        // -------------zhou----------------------
        getUserResume() {
            let row = sessionStorage.getItem("row")

            const loading = this.$loading();
            console.log('state', this.state)

            let obj = {}
            if(row!=null){
                let parse2 = JSON.parse(row);
                obj.id = parse2.id
            }

            this.$axios.post("/resume/getOne", obj ).then(res => {
                if (res.code == 200) {
                    loading.close();
                    this.resumeDetail = res.data;
                    console.log('this.resumeDetail', this.resumeDetail)
                    this.jobseekerId = res.data.jobSeekerId
                    //aliyunOss为空
                    if (this.resumeDetail.aliyunOss == null) {
                        this.resumeDetail.aliyunOss = {}//赋默认值
                        this.hasResumeAttach = true
                    }
                }
                console.log('222222222222222222')
            }).catch((err) => {
                loading.close();
            });
        },
        //保存
        save() {
            this.$refs['basicForm'].validate((valid) => {
                console.log('5555555' + JSON.stringify(this.ruleForm));

                if (valid) {
                    console.log('this.resumeDetailvvvvvvvvvvvv', this.resumeDetail)
                    this.$axios.post("/resume/edit", this.resumeDetail).then(res => {
                        if (res.code == 200) {
                            this.$router.push("/resume")
                            this.$message.success("修改成功");

                        } else {
                            this.$message.error("修改失败");
                        }
                    })
                    console.log('this.resumeDetail', this.resumeDetail)
                } else {
                    return false;
                }
            });

        },
    },
};
</script>
<style lang="less">
.el-upload {
    width: 100%;

    .uploadFile__works {
        .uploadWorks__fileicon {
            width: 50px;
            height: 50px;
            margin: auto;
        }

        .el-icon-warning {
            font-size: 30px;
        }
    }

    &:focus {
        border-color: inherit;
        color: inherit;
    }

    &-dragger {
        display: table-cell;
        vertical-align: middle;

        border: none;
        background: @bg-base-color;
        width: 600px;

        .upload-button {
            display: inline-block;
            width: 100px;
            margin: 10px 0;
        }

        .afterUpload {
            .fileicon {
                width: 50px;
                height: 50px;
                margin: auto;
            }

            time {
                color: @secondary-text-color;
            }

            &__actionButton {
                color: @main-color;

                &-dividerLine {
                    margin: 0 14px;
                }
            }
        }
    }
}

.upload-success-tip {
    display: flex;
    background: @bg-base-color;
    padding: 0 7px;
    align-items: center;

    .el-icon-warning {
        color: @main-color;
    }

    .tips-resolve {
        color: @main-color;
        cursor: pointer;
        margin-left: 6px;
    }

    .el-icon-close {
        cursor: pointer;
        margin-left: auto;
    }
}

// }
.resumeSection {
    .el-select,
    .el-date-editor {
        width: 100%;
    }

    .bottom-action {
        display: flex;

        .el-icon-plus,
        .el-icon-delete {
            font-size: 22px;
            cursor: pointer;

            &-top {
                margin-left: auto;
            }
        }

        .el-icon-delete {
            margin-left: auto;
        }
    }

    .el-form {
        margin-bottom: 40px;

        &:not(:last-child) {
            border-bottom: 1px solid @border-light-color;
        }
    }
}
</style>

<style lang="less" scoped>
.resume-editor {
    width: 900px;
    margin: auto;
    padding: 30px 0;
}

.resumeEditor-footerAction {
    padding: 30px 190px;
    z-index: 33;
    text-align: right;

    &-fix {
        width: 100%;
        background: #fff;
        position: fixed;
        bottom: 0;
        left: 0;

        box-shadow: 0 -2px 10px #eee;
    }
}

.resumeSection {
    padding: 50px 0;
    display: flex;
    justify-content: space-between;
    border-bottom: 1px solid @border-base-color;

    &__title {
        margin-right: 200px;

        padding-bottom: 3px;
        border-bottom: 2px solid @main-color;
    }

    &__form {
        width: 600px;
    }
}
</style>
