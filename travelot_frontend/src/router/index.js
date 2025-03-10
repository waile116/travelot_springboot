import { createRouter, createWebHistory } from "vue-router";
import Index from "../views/Index.vue";
import Attraction from "../views/Attraction.vue";
import Hotel from "../views/Hotel.vue";
import Food from "../views/Food.vue";
import Profile from "../views/Profile.vue";
import OrderList from "../views/OrderList.vue";
import StateList from "../views/StateList.vue";
import StateInfo from "../views/StateInfo.vue";
import Login from "../views/Login.vue";
import Register from "../views/Register.vue";
import NotFound from "../views/NotFound.vue";
import Admin from "../views/admin/Admin.vue";
import Insert from "../views/admin/Insert.vue";

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
    path: "/profile",
    name: "Profile",
    component: Profile,
  },
  {
    path: "/orderList",
    name: "OrderList",
    component: OrderList,
  },
  {
    path: "/stateList",
    name: "StateList",
    component: StateList,
  },
  {
    path: "/stateInfo",
    name: "StateInfo",
    component: StateInfo,
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
  {
    path: "/admin",
    name: "Admin",
    component: Admin,
    meta: {
      requiresAdmin: true,
    },
  },
  {
    path: "/admin/insert",
    name: "Insert",
    component: Insert,
    meta: {
      requiresAdmin: true,
    },
  },
  // catch all routes (show not found page)
  {
    path: "/:pathMatch(.*)*",
    component: NotFound,
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
