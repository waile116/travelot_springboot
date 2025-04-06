<template>
  <component :is="componentNav" />
  <div class="wrapper">
    <router-view></router-view>
  </div>
</template>

<script>
import Nav2 from "../components/Nav2.vue";
import Nav3 from "../components/Nav3.vue";

export default {
  name: "Admin",
  components: {
    Nav2,
    Nav3,
  },
  computed: {
    componentNav() {
      const path = this.$route.path;

      if (path.includes("/insert") || path.includes("/update")) return "Nav2";
      else return Nav3;
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
  height: 6vw;
}

/*************** nav left side *****************/
.wrapper .nav .left {
  display: flex;
}

.wrapper .nav .left img {
  object-fit: contain;
  width: 10vw;
  padding-right: 2vw;
}

/*************** nav menu *****************/
.wrapper .nav .menu {
  align-items: center;
  color: white;
  font-family: var(--font-family);
  font-size: 1.5vw;
  line-height: 25px;
  cursor: pointer;
  margin: 0 2vw;
}

/***************  nav right side *****************/
.wrapper .nav .right {
  display: flex;
  align-items: center;
}

.wrapper .nav .right p {
  display: flex;
  align-items: center;
  color: white;
  font-family: var(--font-family);
  font-size: 1.5vw;
  line-height: 25px;
  cursor: pointer;
  margin: 0 2vw;
}

.wrapper .nav .right p i {
  font-size: 1.5vw;
  margin-left: 1vw;
}

.wrapper .nav .right button {
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
</style>
