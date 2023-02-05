<template>
    <el-container class="layout-container-demo">
        <el-container>
            <Aside />
            <el-container id="container">
                <el-header><Header /></el-header>
                <el-main><Main /></el-main>
            </el-container>
        </el-container>
    </el-container>
</template>
  
<script setup>
import Aside from './Aside.vue';
import Header from './Header.vue';
import Main from './Main.vue';


</script>
  
<style>
#container {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
}
</style>