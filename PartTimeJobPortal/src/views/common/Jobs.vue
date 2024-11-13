<template>

    <div class="jobs">
        <!--    <div class="banner">兼职系统欢迎你</div>-->
        <el-carousel :interval="4000" type="card" height="360px" style="background-color: rgb(231,236,240)">
            <el-carousel-item v-for="(item,index) in imageList" :key="index">
                <!--        <h3 class="medium">{{ item }}</h3>-->
                <el-image :src="item" style="height: 100%;width: 100%"></el-image>
            </el-carousel-item>
        </el-carousel>
        <!-- 搜索 -->
        <div ref="searchBar" :class="{ fixedTop: searchBarFixedTop }" class="search-wrapper">
            <!--      <input-search-->
            <!--        :size="searchBarFixedTop ? 'small' : 'medium'"-->
            <!--        placeholder="搜索职位"-->
            <!--        v-model="searchKeyword"-->
            <!--      ></input-search>-->

            <el-input placeholder="输入搜索内容" class="input-with-select" v-model="searchKeyword"
                      @keyup.enter.native="queryJobList2">
                <el-button type="primary" slot="append" @click="queryJobList2" style="background: #3370ff;color: #fff">
                    搜索
                </el-button>
            </el-input>
        </div>

        <div style="margin-top: 55px">
            <YHeaderList @my-event="queryJobList"></YHeaderList>
        </div>

        <div style="display: flex; width: 60%; margin: auto;">
            <div id="echarts3" style="width: 49%; height: 200px; ">
                11111
            </div>
            <div style="width: 2%;"></div>
            <div id="echarts4" style="width: 49%; height: 200px; ">
                22222
            </div>
        </div>

        <div class="main clearfix" style="margin: 60px auto auto; padding: 0; width: 1150px;">
            <!-- 主体内容 -->
            <!--      <div class="content" v-loading:#ffffff7d.scrollFixed="loading">-->
            <!--        <h2 class="content-title">开启新的职位 ({{ results.count }})</h2>-->
            <ul class="content-list">
                <li class="content-item" v-for="item in jobListData" :key="item.id"
                    style="background-color: floralwhite;border-radius: 20px;">
                    <router-link :to="`/jobs/${item.id}`">
                        <h3 class="title">{{ item.jobName }}</h3>

                        <div class="subTitle">
                            <span class="city">{{ item.recruitUnit.name }}</span>&nbsp;|
                            <span class="job_category">{{ item.workTime }}</span>&nbsp;|
                            <span class="recruitment_channel">{{ item.salaryTreatment }}</span>
                        </div>
                        <p class="desc">{{ item.detail }}</p>
                    </router-link>
                    <div style="float:right;margin-top: -55px">
                        <el-button style="right: 5px" @click="selectResume(item)" v-if="loginInfo.userInfo.type != 2">
                            选择简历
                        </el-button>
                        <br/>
                        <!--                <el-button style="right: 5px" @click="delivery(item)" v-if="loginInfo.userInfo.type != 2">申请岗位</el-button> <br/>-->
                        <!--              <el-button style="right: 5px" @click="goToAsk" v-if="loginInfo.userInfo.type != 2">在线咨询</el-button><br>-->

                        <span>发布时间:{{ item.createTime }}</span>
                    </div>
                </li>
            </ul>
            <!-- 分页器 -->
            <!--        <div v-show="!loading" class="pagination-wrapper">-->
            <!--          <pagination :current-page.sync="currentPage" :total="results.count"></pagination>-->
            <!--        </div>-->

            <el-pagination
                @current-change="handleCurrentChange"
                :current-page="pageCurrent"
                :page-size="pageSize"
                layout="total, prev, pager, next, jumper"
                :total="total"
                style="float: right">
            </el-pagination>
            <!--      </div>-->
        </div>
        <el-dialog :visible.sync="viewResume">
            <el-select v-model="optId" placeholder="请选择">
                <el-option
                    v-for="item in selectData"
                    :key="item.id"
                    :label="item.resName"
                    :value="item.id">
                </el-option>
            </el-select>

            <el-button style="right: 5px" @click="delivery()" v-if="loginInfo.userInfo.type != 2">申请岗位</el-button>

        </el-dialog>
    </div>
</template>
<script>
import {getOffsetTop} from "@/helper/utilities";
import YHeaderList from './HeaderList'

let positionY = 0;
let searchBarClientHeight = 0;
import store from "@/store";
import {ossUrl} from "@/utils/pub";

export default {
    name: "job",
    components: {
        YHeaderList,
    },
    data() {
        const {searchInput, job_category_id} = this.$route.params;
        return {
            item:{},
            optId: "",
            viewResume: false,
            selectData: [],
            searchKeyword: searchInput || "",
            currentPage: 1,
            job_category_id_list: job_category_id ? [job_category_id] : [],
            jobCategories: [],
            jobCities: [],
            location_code_list: [],
            cityList: [],
            cities: [],
            results: [],
            searchBarFixedTop: false,
            // loading: false,
            jobListData: [],
            loginInfo: store.state,
            imageList: [
                ossUrl + 'background/zzz.jpeg',
                ossUrl + 'background/zzz.jpeg',
                ossUrl + 'background/zzz.jpeg'
            ],
            queryVal: [0],//记录子组件传过来的条件标签
            pageCurrent: 1,
            pageSize: 10,//每页显示数目
            total: 0,
        };
    },

    created() {
        this.getGwtd();
        const jobConfigRequest = this.request
            .get("/job-filters")
            .then(response => {
                this.jobCities = response.city_list;
                this.jobCategories = response.job_type_list;
            })
            .catch();

        const dataRequest = this.fetchList();

        // this.loadingIns = this.$loading();
        Promise.all([jobConfigRequest, dataRequest]).then(() => {
            // this.loadingIns.close();
        });
    },
    mounted() {
        this.queryJobList2();
    },
    computed: {
        queryFilter() {
            return {
                job_category_id_list: this.job_category_id_list,
                location_code_list: this.location_code_list,
                keyword: this.searchKeyword,
                offset: Math.max(0, this.currentPage - 1) * 10
            };
        },
        clearable() {
            return (
                this.job_category_id_list.length !== 0 ||
                this.location_code_list.length !== 0
            );
        },
    },
    watch: {
        queryFilter: function (newVal, oldVal) {
            this.searchBarFixedTop && window.scrollTo(0, positionY);
            this.fetchList();
        }
    },
    methods: {
        clearFilter() {
            if (this.job_category_id_list.length) {
                this.job_category_id_list = [];
            }
            if (this.location_code_list.length) {
                this.location_code_list = [];
            }
        },
        fetchList() {
            // this.loading = true;

            return this.request
                .post("/jobs", this.queryFilter)
                .then(response => {
                    if (this.results.count !== response.count) {
                        this.currentPage = 1;
                    }
                    this.results = response;
                    // this.loading = false;
                })
                .catch(() => {
                    // this.loading = false;
                });
        },
        //子组件调用的搜索
        queryJobList(queryVal) {
            console.log('valsssssssssss111', queryVal)
            this.queryVal = queryVal
            this.queryJobList2();
        },

        //当前页面搜索
        queryJobList2() {
            this.$axios.post("/jobInfo/list", {
                status: 1,
                label: JSON.stringify(this.queryVal),
                jobName: this.searchKeyword,
                pageCurrent: this.pageCurrent,
                pageSize: this.pageSize
            }).then(res => {
                if (res.code === 200) {
                    this.jobListData = res.data.records;

                    // console.log('this.jobListData' + JSON.stringify(this.jobListData))
                    this.total = res.data.total
                    //发布时间只显示日期
                    for (let i = 0; i < this.jobListData.length; i++) {
                        if (this.jobListData[i].createTime != null && this.jobListData[i].createTime != '') {
                            this.jobListData[i].createTime = this.jobListData[i].createTime.substring(0, 10)
                        }
                    }
                }
            })
        },

        selectResume(item) {
            this.$axios.post("/resume/getMyOne", {}).then(res => {
                if (res.code == 200) {
                    this.selectData = res.data.records;
                    this.viewResume = true;
                    this.item = item
                }
            }).catch((err) => {
            });
        },


        delivery() {
            let val =  this.item;
            if( this.optId!=null && this.optId!='' ){
                this.$axios.post("/jobseekerJobRelation/add", {recruitUnitId: val.recruitUnitId, jobId: val.id, resumeId: this.optId})
                    .then(res => {
                        if (res.code == 200) {
                            this.$message.success(res.data.msg);
                            this.viewResume = false;
                        }
                    })
            }else{
                this.$message.error('请先选择简历');
            }
        },

        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
            this.pageCurrent = val;
            this.queryUnitList2()
        },
        goToAsk() {
            //   //location.href=`http://${res.data.ip}/#/sys-check?t=${res.data.token}`
            //   sessionStorage.setItem('params.name','param.data');
            //   localStorage.setItem('params.name','param.data');
            //   let return_url = window.location.href;
            //   // console.log('dd' +this.baseIMURL)
            //   //this.$message.success(process.env.VUE_APP_BASE_API_IM)
            //   //this.$message.success(process.env.VUE_APP_BASE_API)
            //
            //   //查hr的用户名
            //   let hruserid = this.jobDetail.creator;
            //   this.$axios.get("/user/getInfoById/"+hruserid).then(res=>{
            //     if (res.code == 200){
            //       let hrusername = res.data.username;
            //       location.href=`${process.env.VUE_APP_BASE_API_IM}/sys-check?t=${sessionStorage.getItem('JWT_TOKEN')}&to=${hrusername}&return_url=${return_url}`
            //     }
            //   })
            //
            //
            //
            //   //  var form = document.createElement("form");
            //   //  form.action = "http://localhost:8081";
            //   // // form.method = 'post';
            //   //  form.style.display = "none";
            //   //  document.body.appendChild(form);
            //   // // params.forEach(function (param) {
            //   //    sessionStorage.setItem('params.name','param.data');
            //   // // });
            //   //  form.submit();
            //   //  return form;
            //
        },

        getGwtd() {
            this.$axios.get("/statistic/getGwtd", {}).then((res) => {
                let data = res.data;
                let xDataArr = [];
                let yDataArr1 = [];
                let yDataArr2 = [];
                let yDataArr3 = [];

                for (let i = 0; i < data.length; i++) {
                    xDataArr.push(data[i].name)
                    yDataArr1.push(data[i].num)
                    yDataArr2.push(data[i].tg)
                    yDataArr3.push(data[i].jj)
                }
                if (res.code == 200) {
                    let elementById3 = document.getElementById("echarts3");
                    let myChart = this.$echarts.init(elementById3, "light");
                    myChart.setOption({
                        tooltip: {
                            trigger: "axis",
                            axisPointer: {
                                type: "cross",
                                label: {
                                    backgroundColor: "rgba(66, 139, 202, 0.7)"
                                }
                            }
                        },
                        legend: {
                            textStyle: {
                                color: "#999",
                                fontSize: 14,
                            },
                            data: [{
                                name: "岗位热度",
                                icon: 'circle'
                            }]
                        },
                        grid: {
                            left: "4%",
                            right: "4%",
                            bottom: "0%",
                            containLabel: true
                        },
                        xAxis: [{
                            type: "category",
                            boundaryGap: false,
                            axisLine: {
                                show: true
                            }, //轴线不显示
                            axisTick: {
                                show: false
                            }, //
                            splitLine: {
                                show: true,
                                lineStyle: {
                                    color: ["#eee"],
                                    width: 1,
                                    type: "dashed"
                                }
                            },
                            data: xDataArr
                        }],
                        yAxis: [{
                            type: "value",
                            axisLine: {
                                show: true
                            }, //轴线不显示
                            axisLabel: {
                                show: false
                            },
                            axisTick: {
                                show: false
                            }, //
                            splitLine: {
                                show: true,
                                lineStyle: {
                                    color: ["#eee"],
                                    width: 1,
                                    type: "dashed"
                                }
                            },
                        }],
                        series: [{
                            name: "岗位热度",
                            type: "line",
                            // stack: "总量",
                            itemStyle: {
                                color: "#FD9E8A"
                            },
                            label: {
                                normal: {
                                    show: true,
                                    position: "top"
                                }
                            },
                            smooth: true,
                            areaStyle: {
                                color: {
                                    type: "linear",
                                    x: 0,
                                    y: 0,
                                    x2: 0,
                                    y2: 1,
                                    colorStops: [{
                                        offset: 0,
                                        color: "#FD9E8A" // 0% 处的颜色
                                    },
                                        {
                                            offset: 1,
                                            color: "#ffe" // 100% 处的颜色
                                        }
                                    ]
                                }
                            },
                            data: yDataArr1
                        }]
                    });

                    let elementById4 = document.getElementById("echarts4");
                    let myChart2 = this.$echarts.init(elementById4, "light");
                    myChart2.setOption({
                        tooltip: {
                            trigger: "axis",
                            axisPointer: {
                                type: "cross",
                                label: {
                                    backgroundColor: "rgba(66, 139, 202, 0.7)"
                                }
                            }
                        },
                        legend: {
                            textStyle: {
                                color: "#999",
                                fontSize: 14,
                            },
                            data: [{
                                name: "岗位通过率",
                                icon: 'circle'
                            }, {
                                name: "岗位拒绝率",
                                icon: 'circle'
                            }]
                        },
                        grid: {
                            left: "4%",
                            right: "4%",
                            bottom: "0%",
                            containLabel: true
                        },
                        xAxis: [{
                            type: "category",
                            boundaryGap: false,
                            axisLine: {
                                show: true
                            }, //轴线不显示
                            axisTick: {
                                show: false
                            }, //
                            splitLine: {
                                show: true,
                                lineStyle: {
                                    color: ["#eee"],
                                    width: 1,
                                    type: "dashed"
                                }
                            },
                            data: xDataArr
                        }],
                        yAxis: [{
                            type: "value",
                            axisLine: {
                                show: true
                            }, //轴线不显示
                            axisLabel: {
                                show: false
                            },
                            axisTick: {
                                show: false
                            }, //
                            splitLine: {
                                show: true,
                                lineStyle: {
                                    color: ["#eee"],
                                    width: 1,
                                    type: "dashed"
                                }
                            },
                        }],
                        series: [{
                            name: "岗位通过率",
                            type: "line",
                            // stack: "总量",
                            itemStyle: {
                                color: "rgba(2,255,91,0.3)"
                            },
                            label: {
                                normal: {
                                    show: true,
                                    position: "top"
                                }
                            },
                            smooth: true,
                            areaStyle: {
                                color: {
                                    type: "linear",
                                    x: 0,
                                    y: 0,
                                    x2: 0,
                                    y2: 1,
                                    colorStops: [{
                                        offset: 0,
                                        color: "rgba(2,255,91,0.3)" // 0% 处的颜色
                                    },
                                        {
                                            offset: 1,
                                            color: "#ffe" // 100% 处的颜色
                                        }
                                    ]
                                }
                            },
                            data: yDataArr2
                        }, {
                            name: "岗位拒绝率",
                            type: "line",
                            // stack: "总量",
                            itemStyle: {
                                color: "rgba(2,255,91,0.3)"
                            },
                            label: {
                                normal: {
                                    show: true,
                                    position: "top"
                                }
                            },
                            smooth: true,
                            areaStyle: {
                                color: {
                                    type: "linear",
                                    x: 0,
                                    y: 0,
                                    x2: 0,
                                    y2: 1,
                                    colorStops: [{
                                        offset: 0,
                                        color: "rgba(2,255,91,0.3)" // 0% 处的颜色
                                    }, {
                                        offset: 1,
                                        color: "#ffe" // 100% 处的颜色
                                    }]
                                }
                            },
                            data: yDataArr3
                        }]
                    });
                } else {
                    this.$message({
                        type: 'erro',
                        message: '查询失败!'
                    });
                }
            });
        }

    }
};
</script>
<style lang="less" scoped>
.banner {
    height: 400px;
    line-height: 400px;
    color: #fff;
    width: 100%;
    min-width: @main-width;
    /*background-image: url("//sf1-ttcdn-tos.pstatp.com/obj/ttfe/ATSX/mainland/joblistbanner2x.jpg");*/
    background-image: url("../../static/images/zzz.jpeg");
    background-size: cover;
    background-repeat: no-repeat;
    background-position: center;
    text-align: center;
    font-size: @font-size-larger;
}

.search-wrapper {
    /*position: absolute;*/
    /*width: 100%;*/
    /*padding: 0 200px;*/

    width: 500px;
    margin: 0 auto;


    transform: translateY(-50%);
    z-index: 100;

    &.fixedTop {
        position: fixed;

        box-shadow: 0 5px 4px rgba(0, 0, 0, 0.1);
        top: 0;
        padding: 20px 130px;
        z-index: 1000;
        background-color: #fff;
        transform: translateY(0);
    }
}

.main {
    width: @main-width;
    margin: 0 auto;
    margin-top: 100px;
    padding: 0 100px 0 200px;

    .aside-filter {
        float: left;
        width: 300px;
        padding-right: 20px;

        .header {
            font-size: 14px;
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
            border-bottom: 1px solid @border-lighter-color;
            padding-bottom: 10px;
            width: 100%;

            .clear {
                &.clearable {
                    color: @main-color;
                    cursor: pointer;
                }
            }
        }

        .job-filter-block {
            margin-bottom: 40px;
        }
    }

    .content {
        min-height: 500px;
        padding-left: 20px;
        border-left: 1px solid @border-lighter-color;
        margin-left: 300px;

        &-title {
            margin-bottom: 30px;
            margin-left: 20px;
        }

        &-item {
            margin-bottom: 10px;
            padding: 10px 20px;
            cursor: pointer;

            .title {
                margin: 12px 0;
            }

            .subTitle {
                color: @regular-text-color;
            }

            .desc {
                white-space: pre-line;
                font-size: 14px;
                color: @secondary-text-color;
                .text-overflow-visible-line(2);
                text-overflow: clip;
            }

            &:hover {
                border-radius: 3px;
                background: #fff;
                box-shadow: 0 10px 30px 0 rgba(136, 150, 171, 0.15);
            }
        }

        .pagination-wrapper {
            margin-top: 50px;
            text-align: right;
        }
    }
}


/*-----start--------轮播图---------------------------*/
.el-carousel__item {
    color: #475669;
    font-size: 14px;
    /*opacity: 0.75;*/ /*这个是设置透明度*/
    line-height: 200px;
    margin: 0;
    height: 360px;
}

.el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
}

/*-----end--------轮播图---------------------------*/


</style>
