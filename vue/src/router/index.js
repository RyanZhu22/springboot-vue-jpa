import { createRouter, createWebHistory } from "vue-router";
import { useUserStore } from "../store/user";
const views = import.meta.glob("../views/*.vue");

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/",
      name: "Index",
      component: () => import("../components/Index.vue"),
      children: [],
    },
    {
      path: "/login",
      name: "Login",
      component: () => import("../views/Login.vue"),
    },
    {
      path: "/register",
      name: "Register",
      component: () => import("../views/Register.vue"),
    },
    {
      path: "/404",
      name: "404",
      component: () => import("../views/404.vue"),
    },
  ],
});

// dynamic routes
export const setDynamicRoutes = (permissions) => {
  // if permissions will not imported, get the permissions from localStorage
  if (!permissions || permissions.length === 0) {
    const managerInfo = localStorage.getItem("user");
    if (!managerInfo) {
      return false;
    }
    permissions = JSON.parse(managerInfo).managerInfo.permissions;
  }

  console.log(permissions);
  if (permissions.length > 0) {
    // 遍历permissions 渲染路由
    permissions.forEach((p) => {
      // check the path of permissions
      if (p.path) {
        // dynamically add routes
        const dynamicRoute = {
          path: p.path,
          name: p.page,
          component: views[`../views/${p.page}.vue`],
        };
        router.addRoute("Index", dynamicRoute);
      }
    });
  }
  // see current routes
  console.log(router.getRoutes());
};

setDynamicRoutes();

// 路由守卫
router.beforeEach((to, from, next) => {
  const store = useUserStore(); // 得到用户对象信息
  const user = store.managerInfo.user;
  const hasUser = user && user.id;
  const noPermissionPaths = ["/login", "/register", "/404"]; // 定义无需登录路由
  if (!hasUser && !noPermissionPaths.includes(to.path)) {
    // 用户没登录
    // 获取缓存的用户数据
    // 如果to.path === '/login'的时候 ！noPermissionPaths.includes(to.path) 是返回false的，也就不会进next("/login")
    next("/login");
  } else {
    next();
  }
});

export default router;
