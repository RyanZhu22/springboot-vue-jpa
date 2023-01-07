<template>
    <el-row class="row-bg" justify="center" id="wrapper">
        <el-col :span="6" v-if="showLogin">
            <div class="container_login">
                <div class="header" style="text-align: -webkit-center">LOGIN</div>
                <el-form status-icon label-width="70px"  style="margin-top: 20px;"
                    ref="loginFormRef" :model="loginForm" :rules="loginRules">
                    <el-form-item label="Username" prop="username" style="margin-top: 40px;">
                        <el-input v-model="loginForm.username" type="username"
                            autocomplete="off" />
                    </el-form-item>

                    <el-form-item label="Password" prop="password" style="margin-top: 20px;">
                        <el-input v-model="loginForm.password" type="password" autocomplete="off" />
                    </el-form-item>
                    <el-form-item style="margin-top: 40px;">
                        <el-button type="primary" @click="submitLoginForm(loginFormRef)">Login</el-button>
                        <el-button type="warning" @click="reset(loginFormRef)">Reset</el-button>
                        <el-button type="success" @click="toRegister">Register</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </el-col>

        <el-col :span="6" v-else>
            <div class="container_register">
                <div class="header" style="text-align: -webkit-center">LOGIN</div>
                <el-form status-icon label-width="70px" style="margin-top: 20px;"
                    ref="registerFormRef" :model="registerForm" :rules="registerRules">
                    <el-form-item label="Username" prop="username" style="margin-top: 40px;">
                        <el-input v-model="registerForm.username" type="username"
                            autocomplete="off" />
                    </el-form-item>

                    <el-form-item label="Password" prop="password" style="margin-top: 20px;">
                        <el-input v-model="registerForm.password" type="password" autocomplete="off" />
                    </el-form-item>

                    <el-form-item label="ConfirmPassword" prop="password" style="margin-top: 20px;">
                        <el-input v-model="registerForm.confirmPassword" type="password" autocomplete="off" />
                    </el-form-item>

                    <el-form-item label="Nickname" prop="nickname" style="margin-top: 20px;">
                        <el-input v-model="registerForm.nickname" type="text" autocomplete="off" />
                    </el-form-item>

                    <el-form-item label="Email" prop="Email" style="margin-top: 20px;">
                        <el-input v-model="registerForm.email" type="email" autocomplete="off" />
                    </el-form-item>

                    <el-form-item label="Phone" prop="phone" style="margin-top: 20px;">
                        <el-input v-model="registerForm.phone" type="text" autocomplete="off" />
                    </el-form-item>

                    <el-form-item label="Address" prop="address" style="margin-top: 20px;">
                        <el-input v-model="registerForm.address" type="text" autocomplete="off" />
                    </el-form-item>

                    <el-form-item style="margin-top: 40px;">
                        <el-button type="primary" @click="submitRegisterForm(registerFormRef)">Submit</el-button>
                        <el-button type="warning" @click="reset(registerFormRef)">Reset</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </el-col>
    </el-row>
</template>
  
<script setup>
import { reactive, ref, inject} from 'vue';
import { ElMessage } from 'element-plus'
import router from '../router'
const $axios = inject('$axios')

const loginFormRef = ref()
const registerFormRef = ref()
const showLogin = ref(true)

const loginForm = reactive({
    username: '',
    password: '',
})

const registerForm = reactive({
    username: '',
    password: '',
    confirmPassword: '',
    nickname: '',
    email: '',
    phone: '',
    address: '',
})

// username validation
const loginUsernameValidate = (rule, value, callback) => {
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
const loginPasswordValidate = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('Please input the password'))
    } else if (value !== loginForm.password) {
        callback(new Error("Input value is mismatch!"))
    } else {
        callback()
    }
}

// username validation
const registerUsernameValidate = (rule, value, callback) => {
    // If username do not input the value
    if (value === '') {
        callback(new Error('Please input the password'))
    } else if (value !== registerForm.username) {
        callback(new Error("Input value is mismatch!"))
    } else {
        callback()
    }
}

// password validation
const registerPasswordValidate = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('Please input the password'))
    } else if (value !== registerForm.password) {
        callback(new Error("Input value is mismatch!"))
    } else {
        callback()
    }
}

const confirmValidate = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('Please input the password'))
    } else if (value !== registerForm.password) {
        callback(new Error("Input value is mismatch!"))
    } else if (registerForm.password !== registerForm.confirmPassword) {
        callback(new Error("Two input password is mismatch!"))
    } else {
        callback()
    }
}

const loginRules = reactive({
    username: [{ validator: loginUsernameValidate, trigger: 'blur' }],
    password: [{ validator: loginPasswordValidate, trigger: 'blur' }],
})

const registerRules = reactive({
    username: [{ validator: registerUsernameValidate, trigger: 'blur' }],
    password: [{ validator: registerPasswordValidate, trigger: 'blur' }],
    confirmPassword: [{ validator: confirmValidate, trigger: 'blur' }],
})

const validatation = (formEl) => {
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

const submitLoginForm = (formEl) => {
    // Front-end validate
    validatation(formEl)

    // Post request to Back-end validate
    $axios.post('/api/user/login', loginForm).then(res => {
        console.log(res);
        if (res.code === "200") {
            ElMessage({
                message: 'Login Successfully',
                type: 'success',
            })
            router.push('/home')
        } else if (res.code === "500") {
            ElMessage({
                message: 'Login Failed',
                type: 'error',
            })
            loginForm.username = ''
            loginForm.password = ''
        }
    }).catch(err => console.log(err))
}

const submitRegisterForm = (formEl) => {
    // Front-end validate
    validatation(formEl)
    console.log(registerForm);
    // Post request to Back-end validate
    $axios.post('/api/user', registerForm).then(res => {
        console.log(res);
        if (res.code === "200") {
            ElMessage({
                message: 'Register Successfully',
                type: 'success',
            })
            router.push('/home')
        } else if (res.code === '500') {
            ElMessage({
                message: 'Register Failed',
                type: 'error',
            })
        }
    }).catch(err => console.log(err))
}

const reset = (formEl) => {
    if (!formEl) return
    formEl.resetFields()
}

const toRegister = () => {
    showLogin.value = false
}


</script>
  
<style>
#wrapper {
    height: 100vh;
    background-image: linear-gradient(to bottom right, #FC466B, #3F5EFB);
    overflow: hidden;
}

.container_login {
    width: 350px;
    height: 280px;
    background-color: #fff;
    margin: 200px auto;

    padding: 20px;
    border-radius: 10px
}

.container_register {
    width: 400px;
    height: 500px;
    background-color: #fff;
    margin: 200px auto;
    padding: 20px;
    border-radius: 10px
}
</style>