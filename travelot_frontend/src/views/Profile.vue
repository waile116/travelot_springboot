<template>
  <div class="wrapper">
    <Nav></Nav>
    <div class="container">
      <div class="box">
        <p class="title">个人信息设置</p>
        <div class="content">
          <div class="photo">Photo</div>
          <ul class="info">
            <li>
              <p>手机号码</p>
              <span>{{ user.userId }}</span>
            </li>
            <li>
              <p>姓名</p>
              <span>{{ user.username }}</span>
            </li>
            <li>
              <p>性别</p>
              <span>{{ user.userSex ? "男" : "女" }}</span>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Nav from "../components/Nav.vue";

export default {
  name: "Profile",
  data() {
    return {
      user: {},
    };
  },
  created() {
    this.user = this.$getSessionStorage("user");
  },
  components: {
    Nav,
  },
};
</script>

<style scoped>
.wrapper {
  position: relative;
}

/*************** profile box *****************/
.wrapper .container {
  display: flex;
  justify-content: center;
  margin: 3vw;
  padding-bottom: 3vw;
}

.wrapper .container .box {
  height: 100%;
  width: 70vw;
  border-radius: 1vw;
  box-shadow: 3px 3px 3px #c3c3c3;
  border: 1px solid #c3c3c3;
  box-sizing: border-box;
  padding: 2vw;
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.wrapper .container .box .title {
  font-size: 1.5vw;
}

.wrapper .container .box .content {
  display: flex;
}

/*************** profile photo box *****************/
.wrapper .container .box .content .photo {
  flex: 2;
  padding: 1vw 1vw 1vw 0;
}

/*************** profile info box *****************/
.wrapper .container .box .content .info {
  padding: 1vw 0 1vw 1vw;
  flex: 5;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
}

.wrapper .container .box .content .info li {
  display: flex;
  font-size: 1.5vw;
  margin-bottom: 1vw;
}

.wrapper .container .box .content .info li p {
  flex: 1;
}

.wrapper .container .box .content .info li span {
  font-weight: normal;
  flex: 4;
}
</style>
