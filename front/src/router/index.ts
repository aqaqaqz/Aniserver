import { createRouter, createWebHashHistory, RouteRecordRaw } from "vue-router";
import Main from "../views/main/Main.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "App",
    component: Main,
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
