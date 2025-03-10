<template>
  <div class="wrapper">
    <div class="nav">
      <div class="left">
        <img src="../../assets/logo.png" alt="logo" />
      </div>
      <div class="right">
        <p v-if="!isLogin" @click="toLogin">登录</p>
        <button v-if="!isLogin" @click="toRegister">注册</button>
        <p v-if="isLogin" @click="toLogout">
          退出登录<i class="fa fa-chevron-right"></i>
        </p>
      </div>
    </div>
    <div class="content">
      <div class="category">
        <!-- :class is vue's class binding, when category matches, it will render  class "active"-->
        <p
          :class="{ active: category === 'attraction' }"
          @click="category = 'attraction'"
        >
          景点
        </p>
        <p
          :class="{ active: category === 'hotel' }"
          @click="category = 'hotel'"
        >
          酒店
        </p>
        <p
          :class="{ active: category === 'restaurant' }"
          @click="category = 'restaurant'"
        >
          餐厅
        </p>
      </div>
      <table class="form">
        <tr>
          <td style="width: 15%">State Id:</td>
          <td><input v-model="data.stateId" type="text" /></td>
        </tr>
        <tr>
          <td>Name:</td>
          <td><input v-model="data.name" type="text" /></td>
        </tr>
        <tr>
          <td>Description:</td>
          <td><input v-model="data.desc" type="text" /></td>
        </tr>
        <tr>
          <td>Location:</td>
          <td><input v-model="data.location" type="text" /></td>
        </tr>
        <tr>
          <td>Price:</td>
          <td><input v-model="data.price" type="text" /></td>
        </tr>
        <tr v-if="category !== 'hotel'">
          <td>Opening Time:</td>
          <td><input v-model="data.openTime" type="text" /></td>
        </tr>
        <tr>
          <td>Rating:</td>
          <td><input v-model="data.rating" type="text" /></td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  name: "Insert",

  data() {
    return {
      user: {},
      isLogin: false,
      category: "attraction",
      data: {
        stateId: "",
        name: "",
        desc: "",
        location: "",
        price: "",
        openTime: "",
        rating: "",
      },
    };
  },
  created() {
    this.user = this.$getSessionStorage("user");
    if (this.user != null) {
      this.isLogin = true;
    }
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
  position: relative;
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

/*************** nav right side button animation *****************/
.wrapper .nav .right p::before {
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

.wrapper .nav .right p:hover::before {
  transform: translateX(-5%) translateY(100%);
}

.wrapper .nav .right button::before {
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

.wrapper .nav .right button:hover::before {
  transform: translateX(0);
}

/*************** content *****************/
.wrapper .content {
  display: flex;
  flex-direction: column;
  margin: 1.5vw 4vw 0 4vw;
}

/*************** category *****************/
.wrapper .content .category {
  display: flex;
  font-size: 1.5vw;
  color: var(--color-text2);
  align-items: center;
  margin-bottom: 1vw;
}

.wrapper .content .category p {
  margin-right: 2vw;
  cursor: pointer;
}

.wrapper .content .category .active {
  font-size: 2vw;
  color: var(--color-text);
}

/*************** card container *****************/
.wrapper .content table.form {
  justify-content: center;
  padding: 1vw;
  width: 100%;
  border-radius: 1vw;
  box-shadow: 3px 3px 3px #c3c3c3;
}

/*************** each individual form *****************/
.wrapper .content table.form tr {
  align-items: center;
  height: 2vw;
  font-size: 1vw;
  overflow: hidden;
}

.wrapper .content table.form tr input {
  height: 2vw;
  width: 100%;
  border: 0.1vw solid var(--color-text);
  color: var(--color-text);
  font-family: "Manrope";
  font-size: 1vw;
}

.wrapper .content table.form tr input:focus {
  border: 0.2vw solid var(--color-text);
  outline: none;
}
</style>
