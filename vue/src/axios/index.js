// 1. 引入
import axios from "axios";


axios.interceptors.request.use(config => {
    const token = localStorage.getItem('token')
    if (token) {
        config.headers.Authorization = token;
    }
    return config
}, err => {
    return Promise.reject(err)
})

// 响应拦截
axios.interceptors.response.use(response => {
    const res = response.data
    return res
}, err => {
    return Promise.reject(err)
})
  


// 4. 导出
export default axios