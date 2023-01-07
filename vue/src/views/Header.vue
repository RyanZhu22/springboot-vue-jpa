<template>
    <el-header style="text-align: right; font-size: 12px; background-color: #ffffff">
        <div class="toolbar">
            <el-dropdown>
                <el-icon style="margin-right: 8px; margin-top: 1px">
                    <setting />
                </el-icon>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item>View</el-dropdown-item>
                        <el-dropdown-item>Add</el-dropdown-item>
                        <el-dropdown-item>Delete</el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
            <span id="userName">Tom</span>
        </div>
    </el-header>
</template>

<script setup>
import { Setting } from "@element-plus/icons-vue";

</script>

<style>
.el-header {
    width: 100%;
    background-color: var(--el-color-primary-light-7);
    color: var(--el-text-color-primary);
}

.toolbar {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    height: 100%;
    right: 20px;
}

#userName {
    font-size: large;
    font-weight: bold;
}
</style>
