<template>
    <el-menu default-active="2" class="el-menu-vertical-demo" :collapse="isCollapse" @open="handleOpen"
        @close="handleClose">

        <el-radio-group v-model="isCollapse" style="margin-bottom: 20px; align-items: flex-start;">
            <el-radio-button :label="false" size="small">expand</el-radio-button>
            <el-radio-button :label="true"  size="small">collapse</el-radio-button>
        </el-radio-group>

        <el-menu-item index="1">
            <el-icon><location /></el-icon>
            <template #title>Home</template>
        </el-menu-item>
        <el-menu-item index="2">
            <el-icon><icon-menu /></el-icon>
            <template #title>System Management</template>
        </el-menu-item>
        <el-menu-item index="3">
            <el-icon><document /></el-icon>
            <template #title>User Management</template>
        </el-menu-item>
        <el-menu-item index="4">
            <el-icon><setting /></el-icon>
            <template #title>File Management</template>
        </el-menu-item>
    </el-menu>
</template>
  
<script setup>
import { ref } from 'vue'
import {
    Document,
    Menu as IconMenu,
    Location,
    Setting,
} from '@element-plus/icons-vue'

const isCollapse = ref(true)
const handleOpen = (key, keyPath) => {
    console.log(key, keyPath)
}
const handleClose = (key, keyPath) => {
    console.log(key, keyPath)
}
</script>
  
<style>
.el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
}
</style>
  