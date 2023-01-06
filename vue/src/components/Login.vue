<template>
    <el-row class="row-bg" justify="center" id="wrapper">
        <el-col :span="6">
            <div class="container">
                <div class="header" style="text-align: -webkit-center">LOGIN</div>
                <el-form status-icon label-width="70px" class="demo-ruleForm" style="margin-top: 20px;"
                    ref="loginFormRef" :model="loginForm" :rules="rules">
                    <el-form-item label="Username" prop="username" style="margin-top: 40px;">
                        <el-input v-model="loginForm.username" type="username"
                            autocomplete="off" />
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
import { reactive, ref, } from 'vue';

const loginFormRef = ref()

// username validation
const usernameValidate = (rule, value, callback) => {
    // If username do not input the value
    if (value === '') {
        callback(new Error('Please input the password'))
    } else if (value !== loginForm.username) {
        callback(new Error("Input value is mismatch!"))
    } else {
        callback()
    }
}

// password validation
const passwordValidate = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('Please input the password'))
    } else if (value !== loginForm.password) {
        callback(new Error("Input value is mismatch!"))
    } else {
        callback()
    }
}

const loginForm = reactive({
    username: '',
    password: '',
})

const rules = reactive({
    username: [{ validator: usernameValidate, trigger: 'blur' }],
    password: [{ validator: passwordValidate, trigger: 'blur' }],
})

const validate = (formEl) => {
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

const submitForm = (formEl) => {
    // Front-end validate
    validate(formEl)

    // Post request to Back-end validate
    $axios.post('/api/login', loginForm).then(res => {
        console.log(res);
    }).catch(err => console.log(err))

}

const reset = (formEl) => {
    if (!formEl) return
    formEl.resetFields()
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