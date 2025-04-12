<template>
  <div class="wrapper">
    <div class="nav">
      <div class="left">
        <img src="../assets/logo.png" alt="logo" />
      </div>
      <div class="right">
        <p v-if="isRegisterPage" @click="toLogin">登录</p>
        <p v-if="isLoginPage" @click="toRegister">注册</p>
        <p @click="toIndex">首页</p>
        <p v-if="isAdminPage" @click="toLogout">退出登录</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Nav2",
  data() {
    return {
      user: {},
      isLogin: false,
    };
  },
  created() {
    this.user = this.$getSessionStorage("user");
    if (this.user != null) {
      this.isLogin = true;
    }
  },
  computed: {
    isLoginPage() {
      return this.$route.path === "/user/login";
    },
    isRegisterPage() {
      return this.$route.path === "/user/register";
    },
    isAdminPage() {
      return this.$route.path === "/admin";
    },
  },
  methods: {
    toIndex() {
      this.$router.push({ path: "/index" });
    },
    toRegister() {
      this.$router.push({
        path: "/user/register",
      });
    },
    toLogin() {
      this.$router.push({
        path: "/user/login",
      });
    },
    toLogout() {
      this.$removeSessionStorage("user");
      this.$router.push({
        path: "/user/login",
      });
    },
  },
};
</script>

<style scoped>
/*************** nav bar *****************/
.wrapper .nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 4vw;
  background-color: var(--color-blue1);
}

.wrapper .nav .left {
  display: flex;
}

.wrapper .nav .left img {
  object-fit: contain;
  width: 10vw;
  padding-right: 2vw;
}

.wrapper .nav .right {
  display: flex;
}

.wrapper .nav .right p {
  margin: 2vw 1.5vw;
  color: white;
  font-family: var(--font-family);
  font-size: 1.5vw;
  line-height: 25px;
  cursor: pointer;
  position: relative;
}

/*************** nav button animation *****************/
.wrapper .nav .right p::before {
  content: "";
  position: absolute;
  bottom: 0;
  left: 0%;
  transform: translateX(-100%) translateY(100%) scale(0);
  transition: transform 0.3s ease-in-out;
  height: 0.2vw;
  width: 130%;
  border-radius: 2px;
  background-color: white;
}

.wrapper .nav .right p:hover::before {
  transform: translateX(-10%) translateY(100%);
}
</style>
