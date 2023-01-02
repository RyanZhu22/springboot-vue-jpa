<template>
    <el-row class="row-bg" justify="center" id="wrapper">
        <el-col :span="6">
            <div class="container">
                <div class="header" style="text-align: -webkit-center">LOGIN</div>
                <el-form status-icon label-width="70px" class="demo-ruleForm" style="margin-top: 20px;"
                    ref="ruleFormRef" :model="loginForm" :rules="rules">
                    <el-form-item label="Username" prop="username" style="margin-top: 40px;">
                        <el-input v-model="loginForm.username" type="username" autocomplete="off" />
                    </el-form-item>
                    <el-form-item label="Password" prop="password" style="margin-top: 20px;">
                        <el-input v-model="loginForm.password" type="password" autocomplete="off" />
                    </el-form-item>
                    <el-form-item style="margin-top: 40px;">
                        <el-button type="primary" @click="submitForm">Login</el-button>
                        <el-button type="warning" @click="reset">Reset</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </el-col>
    </el-row>
</template>
  
<script setup>
import { reactive, ref } from 'vue';

const ruleFormRef = ref()

const loginForm = reactive({
    username: '',
    password: ''
})

const rules = reactive({
    username: [{ validator: validatePass, trigger: 'blur' }],
    password: [{ validator: validatePass2, trigger: 'blur' }],
})

const validatePass = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('Please input the username'))
    } else {
        // input value is not Null
        if (loginForm.username !== '') {
            if (!ruleFormRef.value) return ruleFormRef.value.validateField('username', () => null)
        }
        callback()
    }
}

const validatePass2 = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('Please input the password'))
    } else {
        // input value is not Null
        if (loginForm.password !== '') {
            if (!ruleFormRef.value) return ruleFormRef.value.validateField('password', () => null)
        }
        callback()
    }
}

const submitForm = (formEl) => {
    if (!formEl) return
    formEl.validate((valid) => {
        if (valid) {
            console.log('submit!')
        } else {
            console.log('error submit!')
            return false
        }
    })


}

</script>
  
<style>
#wrapper {
    height: 100vh;
    background-image: linear-gradient(to bottom right, #FC466B, #3F5EFB);
    overflow: hidden;
}

.container {
    width: 350px;
    height: 280px;
    background-color: #fff;
    margin: 200px auto;

    padding: 20px;
    border-radius: 10px
}
</style>