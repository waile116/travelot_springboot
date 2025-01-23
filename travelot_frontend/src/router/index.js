import { createRouter, createWebHistory } from "vue-router";
import Index from "../views/Index.vue";
import Attraction from "../views/Attraction.vue";
import Hotel from "../views/Hotel.vue";
import Food from "../views/Food.vue";
import Login from "../views/Login.vue";
import Register from "../views/Register.vue";

const routes = [
  {
    path: "/",
    name: "Home",
    component: Index,
  },
  {
    path: "/index",
    name: "Index",
    component: Index,
  },
  {
    path: "/attraction",
    name: "Attraction",
    component: Attraction,
  },
  {
    path: "/hotel",
    name: "Hotel",
    component: Hotel,
  },
  {
    path: "/food",
    name: "Food",
    component: Food,
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
  },
  {
    path: "/register",
    name: "Register",
    component: Register,
  },
];

const router = createRouter({
  history: createWebHistory("/"),
  routes,
});

//解决重复路由报异常问题
const originalPush = router.push;
router.push = function push(location) {
  return originalPush.call(this, location).catch((err) => {
    return err;
  });
};

export default router;
