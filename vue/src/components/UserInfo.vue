<template>
  <el-upload
    class="avatar-uploader"
    :data="uploadData"
    action="http://localhost:8001/api/file/upload"
    :on-success="handleAvatarSuccess"
    :headers="{ Authorization: token }"
  >
    <img v-if="data.avatar" :src="data.avatar" class="avatar" />
    <el-icon v-else class="avatar-uploader-icon">
      <Plus />
    </el-icon>
  </el-upload>

  <el-descriptions
    title="User Information"
    direction="vertical"
    :column="4"
    size="large"
    border
  >
    <el-descriptions-item label="Username">
      {{ data.username }}
    </el-descriptions-item>
    <el-descriptions-item label="Name">
      {{ data.name }}
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
import { ref, reactive, onMounted, inject } from "vue";
import { useUserStore } from "../store/user";
import { storeToRefs } from "pinia";
import { Plus } from "@element-plus/icons-vue";

const $axios = inject("$axios");
const userStore = useUserStore();
const data = reactive({
    'id': '',
    'username': '',
    'name': '',
    'email': '',
    'phone': '',
    'address': '',
    'avatar': ''
});

const uploadData = reactive({
  id: userStore.getUserId,
});

const token = userStore.getBearerToken;

onMounted(() => {
  load();
});

const load = () => {
  const id = userStore.getUserId;
  console.log(id);
  $axios
    .get(`/api/user/${id}`)
    .then((res) => {
      console.log(res);
      if (res.code === "200") {
        data.id = res.result.id;
        data.username = res.result.username;
        data.name = res.result.name;
        data.email = res.result.email;
        data.phone = res.result.phone;
        data.address = res.result.address;
        data.avatar = res.result.avatar;
        console.log(data);
      }
    })
    .catch((err) => console.log("ERROR" + err));
};

const handleAvatarSuccess = (val) => {
  console.log(val);
  data.avatar = val;
  console.log(data);
  $axios.post('/api/user/update/avatar', data).then((res) => {
    if (res.code === "200") {

    }
  }).catch(err => console.log("ERROR" + err));
};
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
