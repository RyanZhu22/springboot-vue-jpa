// 1. 引入
import axios from "axios";
import {ElMessage} from "element-plus";
import router from "../router";
import {useUserStore} from "../store/user";

// Request
axios.interceptors.request.use(
  (config) => {
    config.headers["Content-Type"] = "application/json;charset=utf-8";
    config.headers["Authorization"] = useUserStore().getBearerToken; // 设置请求头
    return config;
  },
  (err) => {
    return Promise.reject(err);
  }
);

// Response
axios.interceptors.response.use(
  (response) => {
    let res = response.data;

    // 返回文件
    if (response.config.responseType === "blob") {
      return res;
    }
    // 兼容服务端返回的字符串数据
    if (typeof res === "string") {
      res = res ? JSON.parse(res) : res;
    }
    // 权限不通过弹出提示
    if (res.code === "401") {
      ElMessage.error(res.msg)
      router.push('/login')
    }
    return res;
  },
  (err) => {
    console.log("ERROR" + err); // for debug
    return Promise.reject(err);
  }
);

// 4. 导出
export default axios;
