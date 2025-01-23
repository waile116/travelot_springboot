<template>
  <div class="wrapper">
    <div class="left">
      <img src="../assets/logo.png" alt="logo" />
      <Menu></Menu>
    </div>
    <div class="right">
      <p @click="toLogin">登录</p>
      <button @click="toRegister">注册</button>
    </div>
  </div>
</template>

<script>
import Menu from "./Menu.vue";
export default {
  name: "Nav",
  components: { Menu },
  methods: {
    toLogin() {
      this.$router.push({ path: "/login" });
    },
    toRegister() {
      this.$router.push({ path: "/register" });
    },
  },
};
</script>

<style scoped>
.wrapper {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1vw 4vw;
  background-color: var(--color-blue1);
}

.wrapper .left {
  display: flex;
}

.wrapper .left img {
  object-fit: cover;
  width: 10vw;
  padding-right: 2vw;
}

.wrapper .right {
  display: flex;
  align-items: center;
}

.wrapper .right p {
  color: white;
  font-family: var(--font-family);
  font-size: 18px;
  line-height: 25px;
  cursor: pointer;
  margin: 0 2vw;
  position: relative;
}

.wrapper .right p::before {
  content: "";
  position: absolute;
  bottom: 0;
  left: 0%;
  transform: translateX(-100%) translateY(100%) scale(0);
  transition: transform 0.3s ease-in-out;
  height: 2px;
  width: 130%;
  border-radius: 2px;
  background-color: white;
}

.wrapper .right p:hover::before {
  transform: translateX(-10%) translateY(100%);
}

.wrapper .right button {
  color: white;
  font-family: var(--font-family);
  font-weight: 700;
  font-size: 18px;
  background-color: var(--color-orange);
  padding: 1vw 2.5vw;

  outline: none;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  z-index: 1;
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
