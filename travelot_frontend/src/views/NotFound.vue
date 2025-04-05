<template>
  <div class="wrapper">
    <Nav2></Nav2>
    <div class="content">
      <div class="text">
        <p class="t1">404</p>
        <p class="t2">该页面不存在</p>
      </div>
    </div>
  </div>
</template>

<script>
import Nav2 from "../components/Nav2.vue";
export default {
  name: "NotFound",
  components: {
    Nav2,
  },
};
</script>

<style scoped>
.wrapper .content {
  display: flex;
  justify-content: center;
  padding: 5vw;
}
.wrapper .content .text {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.wrapper .content .text .t1 {
  font-size: 4vw;
}
.wrapper .content .text .t2 {
  font-size: 2vw;
}
</style>
