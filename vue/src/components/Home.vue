<template>
    <el-row :gutter="10">
        <el-col :span="6">
            <el-card class="box-card" style="color: #409EFF">
                <template #header>
                <div class="card-header" style="text-align: center;">
                    <span>Total Events</span>
                </div>
                </template>
                <div>5,585,605,181</div>
            </el-card>
        </el-col>
        <el-col :span="6">
            <el-card class="box-card" style="color: #F56C6C">
                <template #header>
                <div class="card-header" style="text-align: center;">
                    <span>Total number of users</span>
                </div>
                </template>
                <div>{{ users_number }}</div>
            </el-card>
        </el-col>
        <el-col :span="6">
            <el-card class="box-card" style="color: #E6A23C">
                <template #header>
                <div class="card-header" style="text-align: center;">
                    <span>Total number of users</span>
                </div>
                </template>
                <div>{{ users_number }}</div>
            </el-card>
        </el-col>
    </el-row>

    <div id="chart" ref="chart"></div>
</template>
  
<script setup>
import { ref, onMounted, onUnmounted, inject } from 'vue';
import { useUserStore } from "../store/user"
import * as echarts from 'echarts';

const $axios = inject('$axios')
/// 声明定义一下echart
let echart = echarts;
const chartRef = ref(null);
const chartInstance = ref(null);
const users_number = ref(null)

onMounted(() => {
    findAll();
    initChart();
});

onUnmounted(() => {
    echarts.dispose;
});

const findAll = () => {
    $axios.get('/api/user').then(res => {
        console.log(res);
        users_number.value = res.result
    })
}

const initChart = () => {
    let myChart = echart.init(document.getElementById("chart"));
    // configure and render your chart

    myChart.setOption({
    
        title: {
            text: 'Activity levels of popular topics'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
            type: 'shadow'
            }
        },
        legend: {},
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'value',
            boundaryGap: [0, 0.01]
        },
        yAxis: {
            type: 'category',
            data: ['AI', 'Database', 'GitHub Actions', 'Web3', 'Low Code']
        },
        series: [
            {
            name: '2022',
            type: 'bar',
            data: [29.13, 31.94, 60.18, 63.85, 76.3]
            },
        ]
    });

}


</script>
  
<style scoped>
#chart {
  width: 600px;
  height: 400px;
}
</style>