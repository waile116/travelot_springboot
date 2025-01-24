<template>
  <ul>
    <li><p @click="toIndex">首页</p></li>
    <li><p @click="toStateList">州属</p></li>
    <li><p @click="toAttraction">景点</p></li>
    <li><p @click="toFood">美食</p></li>
    <li><p @click="toHotel">住宿</p></li>
  </ul>
</template>

<script>
export default {
  name: "Menu",
  methods: {
    toIndex() {
      this.$router.push({
        path: "/index",
      });
    },
    toStateList() {
      this.$router.push({
        path: "/stateList",
      });
    },
    toAttraction() {
      this.$router.push({
        path: "/attraction",
      });
    },
    toHotel() {
      this.$router.push({
        path: "/hotel",
      });
    },
    toFood() {
      this.$router.push({
        path: "/food",
      });
    },
  },
};
</script>

<style scoped>
ul {
  display: flex;
  align-items: center;
}

ul li {
  padding: 2vw;
}

p {
  color: white;
  font-family: var(--font-family);
  font-size: 1.5vw;
  line-height: 25px;
  cursor: pointer;
  position: relative;
}

p::before {
  content: "";
  position: absolute;
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

p:hover::before {
  transform: translateX(-10%) translateY(100%);
}
</style>
