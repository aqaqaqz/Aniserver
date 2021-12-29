import { createRouter, createWebHashHistory, RouteRecordRaw } from "vue-router";
import Main from "../views/main/Main.vue";
import Login from "../views/login/Login.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "App",
    component: Main,
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
