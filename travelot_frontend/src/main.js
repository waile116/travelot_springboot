import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import "./common.css";

import "font-awesome/css/font-awesome.min.css";
import axios from "axios";
import qs from "qs";
import {
  getCurDate,
  setSessionStorage,
  getSessionStorage,
  removeSessionStorage,
  setLocalStorage,
  getLocalStorage,
  removeLocalStorage,
} from "./common.js";

// axios base URL for backend server
axios.defaults.baseURL =
  "https://travelotspringboot-production.up.railway.app:8080"; //"http://localhost:8080/travelot/";

const app = createApp(App);

// add global properties (so can use this.axios)
app.config.globalProperties.$axios = axios;
app.config.globalProperties.$qs = qs;
app.config.globalProperties.$getCurDate = getCurDate;
app.config.globalProperties.$setSessionStorage = setSessionStorage;
app.config.globalProperties.$getSessionStorage = getSessionStorage;
app.config.globalProperties.$removeSessionStorage = removeSessionStorage;
app.config.globalProperties.$setLocalStorage = setLocalStorage;
app.config.globalProperties.$getLocalStorage = getLocalStorage;
app.config.globalProperties.$removeLocalStorage = removeLocalStorage;

// add router guard
router.beforeEach((to, from, next) => {
  const user = JSON.parse(sessionStorage.getItem("user")); //convert JSON string to object

  if (to.meta.requiresAdmin) {
    // if user is admin, go to admin page
    if (user && user.isAdmin) next();
    // if user is not admin, go to index
    else next("/index");
  } else {
    // if user no login, go to login page
    if (
      !user &&
      !(
        to.path === "/" ||
        to.path === "/index" ||
        to.path === "/stateList" ||
        to.path === "/stateInfo" ||
        to.path === "/attractionList" ||
        to.path === "/hotelList" ||
        to.path === "/restaurantList" ||
        to.path === "/user/login" ||
        to.path === "/user/register"
      )
    )
      next("/user/login");
    else {
      next();
    }
  }
  // if (to.meta.requiresAdmin) {
  //   // if user is admin, continue
  //   if (user && user.isAdmin) next();
  //   // if user is login but not admin, redirect to index
  //   else if (user) next("/index");
  // } else {
  // }
});

app.use(router).mount("#app");
