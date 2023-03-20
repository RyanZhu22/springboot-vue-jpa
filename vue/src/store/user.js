// store/user.js
import { defineStore } from "pinia";

// 创建 store
export const useUserStore = defineStore("user", {
  // 状态持久化
  persist: true,
  // 定义状态：一个函数，返回一个对象
  state: () => ({
    managerInfo: {},
  }),

  // 定义 getters，等同于组件的计算属性
  getters: {
    // getter 函数接收 state 作为参数，推荐使用箭头函数
    getUserId() {
      return this.managerInfo.user ? this.managerInfo.user.id : 0
    },
    getUser() {
      return this.managerInfo.user || {}
    },
    getBearerToken() { // TODO 不懂！！！！！！！！！！！！！
      return this.managerInfo.token ? 'Bearer ' + this.managerInfo.token : ''
    },
    getToken() {
      return this.managerInfo.token || ''
    },

  },

  // 定义 actions，有同步和异步两种类型
  actions: {
    // async login(userData) {
    //   await axios
    //     .post("/api/user/login", userData)
    //     .then((res) => {
    //       console.log(res);
    //       if (res.code === "200") {
    //         // action 中修改状态
    //         this.id = res.result.id;
    //         this.nickname = res.result.nickname;
    //         this.avatarUrl = res.result.avatarUrl;
    //         this.token = res.result.token;
    //         ElMessage({
    //           message: "Login Successfully",
    //           type: "success",
    //         });
    //         console.log(this.id);
    //         console.log(this.nickname);
    //         console.log(this.avatarUrl);
    //         console.log(this.token);
    //       }
    //     })
    //     .catch((err) => {
    //       console.log("ERROR" + err);
    //       ElMessage({
    //         message: "Login Fail",
    //         type: "error",
    //       });
    //     });
    // },
    // logout() {
    //   this.id = "";
    //   this.nickname = "";
    //   this.avatarUrl = "";
    //   this.token = "";
    //   localStorage.removeItem("user");
    // },
    setManagerInfo(managerInfo) {
      this.managerInfo = managerInfo
    },
    setUser(user) {
      this.managerInfo.user = JSON.parse(JSON.stringify(user))
    }
  },
});


