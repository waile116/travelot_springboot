<template>
  <div class="wrapper">
    <div class="nav">
      <div class="left">
        <p @click="toBack"><i class="fa fa-chevron-left"></i>返回</p>
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
  methods: {
    toBack() {
      this.$router.go(-1);
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

.wrapper .nav .left {
  display: flex;
}

.wrapper .nav .left p {
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

.wrapper .nav .left p i {
  font-size: 1.5vw;
  margin-right: 1vw;
}
</style>
