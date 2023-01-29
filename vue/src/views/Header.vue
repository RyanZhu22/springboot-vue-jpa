<template>
    <el-header style="text-align: right; font-size: 12px; background-color: #ffffff">
        <div class="toolbar">
            <el-dropdown>
                <el-icon style="margin-right: 8px; margin-top: 1px">
                    <setting />
                </el-icon>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item>Account Information</el-dropdown-item>
                        <el-dropdown-item @click="signOut">Sign out</el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
            <span id="userName">{{ userStore.nickname }}</span>
        </div>
    </el-header>
</template>

<script setup>
import { useRouter } from 'vue-router'
import useUserStore from "../store/user";
import { Setting } from "@element-plus/icons-vue";
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()

const signOut = () => {
    userStore.logout();
    ElMessage({
        message: 'Logout Successfully',
        type: 'success',
    })
    router.push('login')
}

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
