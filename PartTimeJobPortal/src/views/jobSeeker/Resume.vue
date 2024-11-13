<template>
    <div class="resume">
        <div class="banner"></div>

        <div class="profile">
            <div class="profile-header">
                <div class="profile-header__title">
                    <h1 style="color: #34373b">我的简历</h1>
                    <br/> <br/>
                    <!--          <div>-->
                    <!--            <label class style="color: #34373b">-->
                    <!--              <i class="el-icon-time"></i>-->
                    <!--              最近更新:{{ resumeDetail.updateTime!=""&& resumeDetail.updateTime!=null?resumeDetail.updateTime:resumeDetail.createTime}}-->
                    <!--            </label>-->
                    <!--          </div>-->
                </div>
                <div class="profile-header__editor-button">
                    <router-link to="/resume/add">
                        <el-button plain round icon="el-icon-plus"></el-button>
                    </router-link>
                </div>

            </div>

            <el-table align="center" :data="tableData" border style="width: 100%">
                <el-table-column align="center" type="index" label="序号" width="60"></el-table-column>
                <el-table-column align="center" prop="resName" label="简历姓名" width="90"></el-table-column>
                <el-table-column align="center" prop="resEmail" label="邮箱" width="250"></el-table-column>
                <el-table-column align="center" prop="resPhone" label="电话" width="160"></el-table-column>
                <el-table-column align="center" prop="resDesc" label="自我描述"></el-table-column>
                <el-table-column align="center" prop="remark" label="备注"></el-table-column>
                <el-table-column align="center" prop="updateTime" label="更新时间" width="190"></el-table-column>
                <el-table-column align="center" label="操作" width="90">
                    <template slot-scope="scope">
                        <el-button type="danger" @click="clickDelete(scope.row)" size="mini" plain icon="el-icon-delete"
                                   circle title="删除"></el-button>
                        <el-button size="mini" @click="clickEdit(scope.row)" circle icon="el-icon-edit"
                                   title="查看"></el-button>
                    </template>
                </el-table-column>
            </el-table>

            <el-dialog :visible.sync="viewResume">

                <div class="profile-header__editor-button" style="float: right;">
                    <router-link to="/resume/edit">
                        <el-button plain round icon="el-icon-edit"></el-button>
                    </router-link>
                </div>
                <div class="resumeForm">
                    <!-- 基本信息 -->
                    <div class="resumeViewSection">
                        <h2 class="resumeViewSection__title">基本信息</h2>

                        <div class="resumeViewForm">
                            <div class="resumeViewItem">
                                <div class="resumeViewItem__label">姓名</div>
                                <div class="resumeViewItem__content">{{ resumeDetail.resName }}</div>
                            </div>
                            <div class="resumeViewItem">
                                <div class="resumeViewItem__label">手机号</div>
                                <div class="resumeViewItem__content">
                                    {{ resumeDetail.resPhone }}
                                </div>
                            </div>
                            <div class="resumeViewItem">
                                <div class="resumeViewItem__label">邮箱</div>
                                <div class="resumeViewItem__content">
                                    {{ resumeDetail.resEmail }}
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="resumeViewSection">
                        <h2 class="resumeViewSection__title">自我评价</h2>
                        <div class="resumeViewForm">
                            <div class="resumeViewItem resumeViewItem-desc">
                                {{ resumeDetail.resDesc }}
                            </div>
                        </div>
                    </div>
                    <div class="resumeViewSection">
                        <h2 class="resumeViewSection__title">简历</h2>
                        <div class="resumeViewItem resumeViewItem__resumeAttachment">
                            <div class="fileIcon">
                            </div>
                            <div class="content">
                                <h3>{{ resumeDetail.aliyunOss.fileName + '.' + resumeDetail.aliyunOss.type }}</h3>
                                <time>
                                    上传时间：2021-02-12 14:28:46
                                </time>
                            </div>
                            <div class="download-link">
                                <a
                                    ref="resumeDownloadLink"
                                    :href="ossUrl + resumeDetail.aliyunOss.filePath"
                                    :download="resumeAttachmentLink"
                                    @click="downloadResume()"
                                >
                                    <i class="el-icon-download"></i>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </el-dialog>
        </div>
    </div>
</template>
<script>
import {
    fetchResume,
    fetchResumeWorksDownloadLink,
} from "@/helper/requestWithToken.js";
import store from "@/store";

const {ossUrl} = require("@/utils/pub");
export default {
    name: "resume",
    data() {
        return {
            viewResume: false,
            resumeDetail: {},
            resumeAttachmentLink: "",
            state: store.state,
            hasResumeAttach: false,//是否有简历附件
            tableData: []
        };
    },
    computed: {
        resumeFileType() {
            const pathArr = this.resumeDetail.resume_attachment.name.split(".");
            return pathArr[pathArr.length - 1];
        },
    },
    methods: {
        downloadResume() {

        },

        clickDelete(row){
            this.$axios.delete("/resume/delete/"+row.id).then((res) => {
                if (res.code == 200) {
                    this.getUserResume();
                    this.$message.success("操作成功");
                }else{
                    this.$message.error("操作失败");
                }
            });
        },

        clickEdit(row) {
            this.resumeDetail = row
            //aliyunOss为空则赋默认值
            if (this.resumeDetail.aliyunOss == null) {
                this.resumeDetail.aliyunOss = {}
            }
            sessionStorage.setItem("row", JSON.stringify(row))
            this.viewResume = true
        },


        getUserResume() {
            const loading = this.$loading();
            console.log('state', this.state)
            this.$axios.post("/resume/getMyOne", {}).then(res => {
                if (res.code == 200) {
                    loading.close();

                    console.log(res.data.records, "LLLLLLLLLLLLLLL")
                    this.tableData = res.data.records;

                    this.resumeDetail = res.data;
                    //aliyunOss为空则赋默认值
                    if (this.resumeDetail.aliyunOss == null) {
                        this.resumeDetail.aliyunOss = {}
                    }
                    console.log('this.resumeDetail', this.resumeDetail)
                }
                console.log('222222222222222222')
            }).catch((err) => {
                loading.close();
            });
        }
    },
    created() {
        this.getUserResume();
    },
};
</script>

<style lang="less" scoped>
.banner {
    /*width: 1200px;*/
    /*background-image: url("//sf1-ttcdn-tos.pstatp.com/obj/ttfe/ATSX/my_resume_banner_1577084307030.jpg");*/
    /*background-size: 100% 100%;*/
    height: 300px;
    background-repeat: no-repeat;
}

.profile {
    width: 1500px;
    margin: auto;
    margin-top: -200px;

    &-header {
        color: #fff;
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-left: 50px;
    }

    .resumeForm {
        margin-top: 30px;
        background: #fff;
        box-shadow: 0px 9px 20px 0px #c2c1c180;
        border-radius: 4px;
        padding: 30px;

        .resumeViewSection {
            min-height: 200px;
            margin-bottom: 30px;

            &__title {
                margin-bottom: 20px;
            }

            .resumeViewForm {
                margin-bottom: 30px;
            }

            .resumeViewItem {
                float: left;
                width: 50%;
                margin-bottom: 20px;
                min-height: 50px;

                &__resumeAttachment {
                    display: flex;
                    padding: 10px;
                    background-color: @bg-base-color;
                    align-items: center;

                    time {
                        color: @regular-text-color;
                        font-size: @font-size-base;
                    }

                    .fileIcon {
                        margin-right: 10px;
                        width: 50px;
                        height: 50px;
                    }

                    .download-link {
                        margin-left: auto;

                        .el-icon-download {
                            font-size: @font-size-large;
                            border-radius: 50%;
                            border: 1px solid @border-base-color;
                            padding: 4px;
                        }

                        &:hover {
                            color: @main-color;
                        }
                    }
                }

                &__content.is-link {
                    color: @main-color;
                    cursor: pointer;
                }

                &__label {
                    color: @secondary-text-color;
                    font-size: @font-size-base;
                    margin-bottom: 7px;
                }

                &__content-link {
                    color: @main-color;
                }

                &-desc {
                    overflow: hidden;
                    width: 70%;
                    white-space: pre-line;
                }
            }
        }
    }
}
</style>
