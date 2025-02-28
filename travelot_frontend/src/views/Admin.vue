<template>
  <div class="wrapper">
    <div class="left">
      <img src="../assets/logo.png" alt="logo" />
      <Menu></Menu>
    </div>
    <div class="right">
      <p v-if="!isLogin" @click="toLogin">登录</p>
      <button v-if="!isLogin" @click="toRegister">注册</button>
      <p v-if="isLogin" @click="toLogout">
        退出登录<i class="fa fa-chevron-right"></i>
      </p>
    </div>
  </div>
  Admin page
</template>

<script>
import Nav from "../components/Nav.vue";

export default {
  name: "Admin",

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
  components: {
    Nav,
  },
  methods: {
    toLogout() {
      this.$removeSessionStorage("user");
      this.$router.push({
        path: "/login",
      });
    },
  },
};
</script>

<style scoped>
.wrapper {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 4vw;
  background-color: var(--color-blue1);
  height: 6vw;
}

/*************** left side *****************/
.wrapper .left {
  display: flex;
}

.wrapper .left img {
  object-fit: contain;
  width: 10vw;
  padding-right: 2vw;
}

/*************** right side *****************/
.wrapper .right {
  display: flex;
  align-items: center;
}

.wrapper .right p {
  display: flex;
  align-items: center;
  color: white;
  font-family: var(--font-family);
  font-size: 1.5vw;
  line-height: 25px;
  cursor: pointer;
  margin: 0 2vw;
  position: relative;
}

.wrapper .right p i {
  font-size: 1.5vw;
  margin-left: 1vw;
}

.wrapper .right button {
  color: white;
  font-family: var(--font-family);
  font-weight: 700;
  font-size: 1.5vw;
  background-color: var(--color-orange);
  padding: 1vw 2.5vw;

  outline: none;
  border: none;
  border-radius: 1vw;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  z-index: 1;
}

/*************** right side button animation *****************/
.wrapper .right p::before {
  content: "";
  position: absolute;
  bottom: 0;
  left: 0%;
  transform: translateX(-100%) translateY(100%) scale(0);
  transition: transform 0.3s ease-in-out;
  height: 0.2vw;
  width: 110%;
  border-radius: 2px;
  background-color: white;
}

.wrapper .right p:hover::before {
  transform: translateX(-5%) translateY(100%);
}

.wrapper .right button::before {
  content: "";
  position: absolute;
  bottom: 0;
  left: 0;
  transform: translateX(-100%);
  transition: transform 0.2s ease-in-out;
  height: 100%;
  width: 100%;
  background-color: #d94500;
  z-index: -1;
}

.wrapper .right button:hover::before {
  transform: translateX(0);
}
</style>
