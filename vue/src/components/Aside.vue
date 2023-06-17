<template>
  <el-menu default-active="2" class="el-menu-demo" style="border: none" router>
    <div v-for="item in menus" :key="item.id">
      <div v-if="item.type === 1">
        <el-menu-item :index="item.path" v-if="!item.hide">
          <el-icon v-if="item.icon">
            <component :is="item.icon"></component>
          </el-icon>
          <span>{{ item.name }}</span>
        </el-menu-item>
      </div>
      <div v-else-if="item.type === 2">
        <el-menu-item :index="item.path" v-if="!item.hide">
          <el-icon v-if="item.icon">
            <component :is="item.icon"></component>
          </el-icon>
          <span>{{ item.name }}</span>
        </el-menu-item>
      </div>
    </div>
  </el-menu>
</template>

<script setup>
import {useUserStore} from "../store/user";

// 实例化 store
const userStore = useUserStore();

const menus = userStore.getPermissions;
console.log(menus);
</script>

<style>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
</style>
