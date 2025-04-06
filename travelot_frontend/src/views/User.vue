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
  name: "User",
  components: {
    Nav2,
    Nav3,
  },
  computed: {
    componentNav() {
      const path = this.$route.path;

      if (path.includes("/login") || path.includes("/register")) return "Nav3";
      return "Nav2";
    },
  },
};
</script>

<style scoped></style>
