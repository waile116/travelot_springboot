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

// axios base URL for server
axios.defaults.baseURL = "http://localhost:8080/travelot/";

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
  const user = sessionStorage.getItem("user");
  // redirect to login if the user is not authenticated to access vertain website
  if (
    !(
      to.path === "/" ||
      to.path === "/index" ||
      to.path === "/stateList" ||
      to.path === "/attraction" ||
      to.path === "/hotel" ||
      to.path === "/food" ||
      to.path === "/login" ||
      to.path === "/register"
    )
  ) {
    if (!user) {
      return router.push("/login").then(() => location.reload());
    }
  }
  next();
});

app.use(router).mount("#app");
