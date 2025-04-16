import { createRouter, createWebHistory } from "vue-router";
import Index from "../views/Index.vue";
import StateList from "../views/StateList.vue";
import StateInfo from "../views/StateInfo.vue";
import AttractionList from "../views/AttractionList.vue";
import AttractionInfo from "../views/AttractionInfo.vue";
import HotelList from "../views/HotelList.vue";
import HotelInfo from "../views/HotelInfo.vue";
import RestaurantList from "../views/RestaurantList.vue";
import RestaurantInfo from "../views/RestaurantInfo.vue";
import Payment from "../views/Payment.vue";
import User from "../views/User.vue";
import UserLogin from "../views/user/UserLogin.vue";
import UserRegister from "../views/user/UserRegister.vue";
import UserProfile from "../views/user/UserProfile.vue";
import UserOrderList from "../views/user/UserOrderList.vue";
import UserCommentList from "../views/user/UserCommentList.vue";
import UserFavouriteList from "../views/user/UserFavouriteList.vue";
import Admin from "../views/Admin.vue";
import AdminInsert from "../views/admin/AdminInsert.vue";
import AdminUpdate from "../views/admin/AdminUpdate.vue";
import AdminIndex from "../views/admin/AdminIndex.vue";
import NotFound from "../views/NotFound.vue";

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
    path: "/attractionList",
    name: "AttractionList",
    component: AttractionList,
  },
  {
    path: "/attractionInfo",
    name: "AttractionInfo",
    component: AttractionInfo,
  },
  {
    path: "/hotelList",
    name: "HotelList",
    component: HotelList,
  },
  {
    path: "/hotelInfo",
    name: "HotelInfo",
    component: HotelInfo,
  },
  {
    path: "/restaurantList",
    name: "RestaurantList",
    component: RestaurantList,
  },
  {
    path: "/restaurantInfo",
    name: "RestaurantInfo",
    component: RestaurantInfo,
  },
  {
    path: "/payment",
    name: "Payment",
    component: Payment,
  },
  {
    path: "/user",
    component: User,
    children: [
      {
        path: "login",
        name: "UserLogin",
        component: UserLogin,
      },
      {
        path: "register",
        name: "UserRegister",
        component: UserRegister,
      },
      {
        path: "profile",
        name: "UserProfile",
        component: UserProfile,
      },
      {
        path: "orderList",
        name: "UserOrderList",
        component: UserOrderList,
      },
      {
        path: "commentList",
        name: "UserCommentList",
        component: UserCommentList,
      },
      {
        path: "favouriteList",
        name: "UserFavouriteList",
        component: UserFavouriteList,
      },
    ],
  },
  {
    path: "/admin",
    component: Admin,
    meta: {
      requiresAdmin: true,
    },
    children: [
      {
        path: "",
        name: "AdminIndex",
        component: AdminIndex,
      },
      {
        path: "insert",
        name: "AdminInsert",
        component: AdminInsert,
      },
      {
        path: "update",
        name: "AdminUpdate",
        component: AdminUpdate,
      },
    ],
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
  scrollBehavior(to, from, savedPosition) {
    return { top: 0 }; // go to top everytime goes to web page
  },
});

//解决重复路由报异常问题
const originalPush = router.push;
router.push = function push(location) {
  return originalPush.call(this, location).catch((err) => {
    return err;
  });
};

export default router;
