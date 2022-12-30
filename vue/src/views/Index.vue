<template>
    <el-container class="layout-container-demo">
        <Aside />
        <el-container id="container">
            <Header />
            <Main />
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