<template>
    <el-upload
        class="avatar-uploader"
        :data="uploadData"
        action="http://localhost:8001/api/user/update/"
        :on-success="handleAvatarSuccess"
    >
        <img v-if="data.avatar_url" :src="data.avatar_url" class="avatar" />
        <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
    </el-upload>

    <el-descriptions
        title="User Information"
        direction="vertical"
        :column="4"
        size="large"
        border>
        <el-descriptions-item label="Username">
            {{ data.username }}
        </el-descriptions-item>
        <el-descriptions-item label="Nickname">
            {{ data.nickname }}
        </el-descriptions-item>
        <el-descriptions-item label="Email">
            {{ data.email }}
        </el-descriptions-item>
        <el-descriptions-item label="Phone">
            {{ data.phone }}
        </el-descriptions-item>
        <el-descriptions-item label="Address">
            {{ data.address }}
        </el-descriptions-item>
  </el-descriptions>
</template>
  
<script setup>
import { ref, reactive, onMounted, inject } from 'vue';
import useUserStore from "../store/user"

import { Plus } from '@element-plus/icons-vue'

const $axios = inject('$axios')

// 实例化 store
const userStore = useUserStore()

// username: '',
//     nickname: '',
//     email: '',
//     phone: '',
//     address: '',
//     create_time: ''
const data = ref([])

const uploadData = reactive({
    'id': userStore.id
})

onMounted(() => {
    load();
})

const load = () => {
    $axios.get('/api/user/id', {params: {id: userStore.id}}).then(res => {
        console.log(res);
        if (res.code === '200') {
            data.value = res.result
            console.log(data.value);
        }
        
    }).catch(err => console.log("ERROR" + err))
}

const handleAvatarSuccess = (val) => {
    console.log(val);
}
</script>
  
<style scoped>
.avatar-uploader .avatar {
    width: 178px;
    height: 178px;
    display: block;
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>