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
                <div></div>
            </el-card>
        </el-col>
        <el-col :span="6">
            <el-card class="box-card" style="color: #E6A23C">
                <template #header>
                <div class="card-header" style="text-align: center;">
                    <span>Total number of users</span>
                </div>
                </template>
                <div></div>
            </el-card>
        </el-col>
    </el-row>

    <div id="chart" ref="chart"></div>
</template>
  
<script setup>
import { ref, onMounted, onUnmounted, inject } from 'vue';
import * as echarts from 'echarts';

const $axios = inject('$axios')
/// 声明定义一下echart
let echart = echarts;
const chartRef = ref(null);
const chartInstance = ref(null);
const tableData = ref(null)

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
        tableData.value = res
    })
}

const initChart = () => {
    let myChart = echart.init(document.getElementById("chart"));
    // configure and render your chart

    myChart.setOption({
        // parallelAxis: [
        //     { dim: 0, name: '2019', data: ["1"," 2", "3", "4", "5", "6", "7"," 8", "9", "10"] },
        //     { dim: 1, name: '2020' },
        //     { dim: 2, name: '2021' },
        //     { dim: 3, name: '2022' },
        //     {
        //     dim: 4,
        //     name: 'Language name',
        //     type: 'category',
        //     data: ['c', 'Rust', 'Shell', 'Ruby', 'Php', 'C#', 'C++', 'Go', 'Java', 'Python']
        //     }
        // ],
        // series: {
        //     type: 'parallel',
        //     lineStyle: {
        //         width: 5
        //     },
        //     data: [
        //     [1, 1, 1, 1, 'Python'],
        //     [2, 2, 2, 2, 'Java'],
        //     [4, 4, 3, 3, 'Go']
        //     ]
        // }
        // xAxis: {
        //     type: 'category',
        //     data: ['2019', '2020', '2021', '2022']
        // },
        // yAxis: {
        //     type: 'value'
        // },
        // series: [
        //     {
        //         name: 'Python',
        //         type: 'line',
        //         step: 'Python',
        //         data: [1, 1, 1, 1]
        //     },
        //     {
        //         name: 'Step Middle',
        //         type: 'line',
        //         step: 'middle',
        //         data: [220, 282, 201, 234, 290, 430, 410]
        //         },
        //     {
        //         name: 'Step End',
        //         type: 'line',
        //         step: 'end',
        //         data: [450, 432, 401, 454, 590, 530, 510]
        //     }
        // ]
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