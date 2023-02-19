import { createRouter, createWebHashHistory } from "vue-router";


// 2. 定义一些路由
// 每个路由都需要映射到一个组件。
// 我们后面再讨论嵌套路由。
const routes = [
  {
    path: "/",
    name: "index",
    component: () => import("../views/Index.vue"),
    children: [
      {
        path: "home",
        name: "home",
        component: () => import("../components/Home.vue"),
      },
      {
        path: "system",
        name: "system",
        component: () => import("../components/SystemInfo.vue"),
      },
      {
        path: "user",
        name: "user",
        component: () => import("../components/UserInfo.vue"),
      },
      {
        path: "role",
        name: "role",
        component: () => import("../components/RoleInfo.vue"),
      },
      {
        path: "menu",
        name: "menu",
        component: () => import("../components/MenuInfo.vue"),
      },
      {
        path: "file",
        name: "file",
        component: () => import("../components/FileInfo.vue"),
      },
    ],
  },
  {
    path: "/login",
    name: "login",
    component: () => import("../components/Login.vue"),
  },
  {
    path: "/register",
    name: "register",
    component: () => import("../components/Register.vue"),
  },
];

// 3. 创建路由实例并传递 `routes` 配置
// 你可以在这里输入更多的配置，但我们在这里
// 暂时保持简单
const router = createRouter({
  // 4. 内部提供了 history 模式的实现。为了简单起见，我们在这里使用 hash 模式。
  history: createWebHashHistory(),
  routes, // `routes: routes` 的缩写
});

export default router;
