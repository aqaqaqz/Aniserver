import { createRouter, createWebHashHistory, RouteRecordRaw } from "vue-router";
import Main from "../views/main/Main.vue";
import Login from "../views/login/Login.vue";
import List from "../views/content/List.vue";
import Player from "../views/content/Player.vue";

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
  {
    path: "/list/:path",
    name: "List",
    component: List,
    props: true
  },
  {
    path: "/player/:path",
    name: "Player",
    component: Player,
    props: true
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
