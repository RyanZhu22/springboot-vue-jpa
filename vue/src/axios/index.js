// 1. 引入
import axios from "axios";
import { ElMessage } from 'element-plus'
import router from "../router";

// Request
axios.interceptors.request.use(config => {
    let user = localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : {}
    if (user) {
        config.headers['token'] = user.token;
    }
    return config
}, err => {
    return Promise.reject(err)
})

// Response
axios.interceptors.response.use(response => {
    const res = response.data

    if (res.code === '401') {
        ElMessage({
            message: res.message,
            type: 'error',
        })
        router.push('/login')
    }
    return res
}, err => {
    return Promise.reject(err)
})
  


// 4. 导出
export default axios