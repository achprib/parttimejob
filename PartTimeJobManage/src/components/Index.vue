<template>
    <section class="index">
        <figure class="quick-port">
            <el-row :gutter="24">
<!--                <el-col :span="2.5">-->
<!--                    <router-link class="item" :style="{'background-color':quikPortSetting.color1}" to="/basicData/menus-management">-->
<!--                        <img src="../static/images/icon_quick_port_3.png" alt="">-->
<!--                        <h2>权限管理</h2>-->
<!--                    </router-link>-->
<!--                </el-col>-->
<!--                <el-col :span="2.5">-->
<!--                    <router-link class="item" :style="{'background-color':quikPortSetting.color2}" to="/basicData/permission">-->
<!--                        <img src="../static/images/icon_quick_port_3.png" alt="">-->
<!--                        <h2>角色管理</h2>-->
<!--                    </router-link>-->
<!--                </el-col>-->
                <el-col :span="2.7">
                    <router-link class="item" :style="{'background-color':quikPortSetting.color3}" to="/basicData/personnel-list">
                        <img src="../static/images/icon_quick_port_3.png" alt="">
                        <h2>管理员管理</h2>
                    </router-link>
                </el-col>
                <el-col :span="2.7">
                    <router-link class="item" :style="{'background-color':quikPortSetting.color4}" to="/basicData/jobSeeker">
                        <img src="../static/images/icon_quick_port_3.png" alt="">
                        <h2>求职者管理</h2>
                    </router-link>
                </el-col>
                <el-col :span="2.7">
                    <router-link class="item" :style="{'background-color':quikPortSetting.color5}" to="/basicData/hrList">
                        <img src="../static/images/icon_quick_port_3.png" alt="">
                        <h2>招聘人员管理</h2>
                    </router-link>
                </el-col>
                <el-col :span="2.7">
                    <router-link class="item" :style="{'background-color':quikPortSetting.color6}" to="/basicData/recruitmentUnit">
                        <img src="../static/images/icon_quick_port_3.png" alt="">
                        <h2>企业信息管理</h2>
                    </router-link>
                </el-col>
                <el-col :span="2.7">
                    <router-link class="item" :style="{'background-color':quikPortSetting.color7}" to="/jobManagement/jobInfo-list">
                        <img src="../static/images/icon_quick_port_3.png" alt="">
                        <h2>岗位信息管理</h2>
                    </router-link>
                </el-col>
                <el-col :span="2.7">
                    <router-link class="item" :style="{'background-color':quikPortSetting.color8}" to="/jobManagement/label-list">
                        <img src="../static/images/icon_quick_port_3.png" alt="">
                        <h2>岗位标签管理</h2>
                    </router-link>
                </el-col>
                <el-col :span="2.7">
                    <router-link class="item" :style="{'background-color':quikPortSetting.color9}" to="/jobManagement/label-list">
                        <img src="../static/images/icon_quick_port_3.png" alt="">
                        <h2>意见与反馈管理</h2>
                    </router-link>
                </el-col>

            </el-row>
        </figure>


        <!-- start 全局统计卡片展示 -->
        <el-row :gutter="10" class="cards-group">
            <el-col :xs="12" :sm="6" :md="6" :lg="6" :xl="6" style="margin-right:-200px;">
                <el-card class="box-card" :class="countDict['userCount'].style" style="width: 150px;">
                    <p class="title">{{ countDict['userCount'].name }}</p>
                    <p>_________</p>
                    <h2 class="number">{{ statisticObj.qznum }}人</h2>
                </el-card>
            </el-col>
            <el-col :xs="12" :sm="6" :md="6" :lg="6" :xl="6" style="margin-right:-200px;">
                <el-card class="box-card" :class="countDict['supplierCount'].style"
                         style="width: 150px;margin-left: 10px">
                    <p class="title">{{ countDict['supplierCount'].name }}</p>
                    <p>_________</p>
                    <h2 class="number">{{ statisticObj.dwnum }}家</h2>
                </el-card>
            </el-col>
            <el-col :xs="12" :sm="6" :md="6" :lg="6" :xl="6" style="margin-right:-200px;">
                <el-card class="box-card" :class="countDict['productCount'].style"
                         style="width: 150px;margin-left: 10px">
                    <p class="title">{{ countDict['productCount'].name }}</p>
                    <p>_________</p>
                    <h2 class="number">{{ statisticObj.gwnum }}个</h2>
                </el-card>
            </el-col>
            <el-col :xs="12" :sm="6" :md="6" :lg="6" :xl="6" style="margin-right:55px;">
                <el-card class="box-card" :class="countDict['rmgw'].style"
                         style="width: 430px;margin-left: 10px">
                    <p class="title">{{ countDict['rmgw'].name }}</p>
                    <p>______________________________________________________</p>
                    <h2 class="number">{{ statisticObj11.rmgw }}</h2>
                </el-card>
            </el-col>
            <el-col :xs="12" :sm="6" :md="6" :lg="6" :xl="6" style="margin-right:-200px;">
                <el-card class="box-card" :class="countDict['lmgw'].style"
                         style="width: 445px;">
                    <p class="title">{{ countDict['lmgw'].name }}</p>
                    <p>______________________________________________________</p>
                    <h2 class="number">{{ statisticObj11.lmgw }}</h2>
                </el-card>
            </el-col>
        </el-row>

        <el-card>
            <div style="height: 500px; display: flex;">
                <div id="echarts1" style="width: 49%; height: 500px; ">
                    11111
                </div>
                <div style="width: 2%;"></div>
                <div id="echarts2" style="width: 49%; height: 500px; ">
                    22222
                </div>
            </div>
        </el-card>
    </section>
</template>

<script>
import ChartsLine from '@/components/ChartsLine.vue'
import Radar from '@/components/Radar.vue'

import {countDict} from "@/static/countDict"

export default {
    name: 'index',
    components: {
        // ChartsLine,
        // Radar
    },
    data() {
        return {
            statisticObj: {},
            statisticObj11: {
                rmgw:"",
                lmgw:""
            },
            countDict: countDict,
            quikPortSetting: {
                color0: '#29CE8B',
                color1: '#29CE8B',
                color2: '#3E82EE',
                color3: '#C77BE6',
                color4: '#FD9E8A',
                color5: '#FCA713',
                color6: '#05C3EE',
                color7: '#729FFF',
                color8: '#13c2c2',
                color9: '#c27200',
            }
        }
    },
    created() {
        this.selectStatistic()
    },

    methods: {
        linkToMoreOrders() {
            this.$router.push('/production-management/production-schedule-list');
        },

        selectStatistic() {
            this.$axios.get("/statistic/selectStatistic", {}).then((res) => {
                if (res.code == 200) {
                    this.statisticObj = res.data[0];
                    this.getGwtd();
                } else {
                    this.$message({
                        type: 'erro',
                        message: '查询失败!'
                    });
                }
            });
        },

        getGwtd() {
            this.$axios.get("/statistic/getGwtd", {}).then((res) => {
                let data = res.data;
                let xDataArr = [];
                let yDataArr1 = [];
                let yDataArr2 = [];
                let yDataArr3 = [];

                if( data.length>2 ){
                    this.statisticObj11.rmgw=data[0].name+","+data[1].name
                    this.statisticObj11.lmgw=data[data.length-1].name+","+data[data.length-2].name
                    console.log(this.statisticObj11,"OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO")
                }

                for (let i = 0; i < data.length; i++) {
                    xDataArr.push(data[i].name)
                    yDataArr1.push(data[i].num)
                    yDataArr2.push(data[i].tg)
                    yDataArr3.push(data[i].jj)
                }
                if (res.code == 200) {
                    let myChart = this.$echarts.init( document.getElementById("echarts1"), "light" );
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


                    let myChart2 = this.$echarts.init( document.getElementById("echarts2"), "light" );
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
                            },{
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
                                    },{
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
    },
    filters: {}
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="less">
.index {
    padding: 16px;
    height: 100%;
    overflow: hidden;
    overflow-y: auto;
    background-color: @color-body-bg;

    .box-card {
        margin-bottom: 16px;
    }

    .order-charts-list {
        h3 {
            margin-top: 12px;
            font-weight: 800;
            margin-bottom: 6px;
            white-space: nowrap;
            overflow: hidden;
            width: 100%;
            text-overflow: ellipsis;
        }
    }

    .quick-port {
        margin-bottom: 16px;

        .item {
            background-color: #FFF;
            padding: 22px 16px;
            display: flex;
            align-items: center;
            border-radius: 4px;
            transition: all 0.2s;
            cursor: pointer;
            position: relative;
            overflow: hidden;

            &::before {
                content: "";
                position: absolute;
                transition: all 0.2s;
                width: 100%;
                left: 0;
                top: 95%;
                height: 100%;
                background-color: rgba(0, 0, 0, .15);
            }

            &:hover {
                box-shadow: 0 5px 15px rgba(0, 0, 0, .15);

                > img {
                    transform: scale(.65);
                }

                &::before {
                    top: 0;
                    z-index: 0;
                }

            }

            &:active {
                box-shadow: 0 5px 0 rgba(0, 0, 0, .15);
            }

            > img {
                margin-right: 12px;
                width: 56px;
                height: 56px;
                transition: all 0.2s;
                position: relative;
                z-index: 5;
            }

            > h2 {
                font-size: 17px;
                font-weight: 800;
                color: #FFF;
                flex: 1;
                position: relative;
                z-index: 5;
            }

            &.item_plus {
            }

            &.color1 {
                background-color: #29CE8B;
            }

            &.color2 {
                background-color: #3E82EE;
            }

            &.color3 {
                background-color: #C77BE6;
            }

            &.color4 {
                background-color: #FD9E8A;
            }

            &.color5 {
                background-color: #FCA713;
            }

            &.color6 {
                background-color: #05C3EE;
            }

            &.color7 {
                background-color: #729FFF;
            }

            &.color8 {
                background-color: #606266;
            }
        }
    }
}

// 首页的快捷入口管理
.menu-list {
    > li {
        display: flex;
        align-items: center;
        padding: 12px 16px;
        position: relative;

        &::after {
            content: "";
            position: absolute;
            left: 16px;
            bottom: 0;
            border-bottom: 1px solid @color-border-lighter;
            right: 0;
        }

        &:last-child::after {
            display: none;
        }

        &:hover {
            background-color: #f5f7fa;
        }

        > span {
            margin-left: 12px;

            &.menu-name {
                margin-left: 0;
                flex: 1;
                font-size: 16px;
                font-weight: 800;
            }
        }
    }
}


.index {
    height: 100%;

    .cards-group {
        .box-card {
            height: 104px;
        }
    }

    .quick-links-group {
        /deep/ .box-card {
            .el-card__body {
                display: flex;
                align-items: center;

                .el-avatar {
                    margin-right: 10px;
                    background-color: #fff;
                }

                .item-content {
                    h2 {
                        font-weight: 800;
                        margin-bottom: 5px;
                    }

                    p {
                        font-size: 12px;
                        color: @color-placeholder;
                    }
                }
            }
        }
    }
}

.box-card {
    line-height: 1;
    margin-bottom: 15px;

    .card-header {
        display: flex;
        justify-content: space-between;
        align-items: center;

        .title {
            color: @color-primary;
        }
    }

    .sub-text {
        i {
            margin-left: 5px;
        }
    }

    .number {
        font-size: 24px;
        font-weight: 800;
        padding: 5px 0;
    }

    &.bg1 {
        color: #fff;
        background-image: linear-gradient(120deg, #3e82ee 0, #00c8ee 100%);
        //background-color: #8c939d;
    }

    &.bg2 {
        color: #fff;
        background-image: linear-gradient(120deg, #6cdeb0 0, #b9f39c 100%);
        //background-color: #8c939d;
    }

    &.bg3 {
        color: #fff;
        background-image: linear-gradient(120deg, #c77be6 0, #ea9aff 100%);
    }

    &.bg4 {
        color: #fff;
        background-image: linear-gradient(120deg, #fd9e8a 0, #ffecca 100%);
    }

    &.bg5{
        color: #fff;
        background-image: linear-gradient(120deg, #c77be6 0, #b9f39c 100%);
    }
}

</style>
