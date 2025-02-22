<template>
  <div class="wrapper">
    <div class="header">
      <div class="left">
        <img src="../assets/logo.png" alt="logo" />
      </div>
      <div class="right">
        <p @click="toRegister">注册</p>
        <p @click="toIndex">首页</p>
      </div>
    </div>
    <div class="login">
      <div class="box">
        <div class="up">
          <p class="title">手机号码登录</p>
          <ul class="form">
            <li>
              <input type="text" v-model="userId" placeholder="手机号码" />
            </li>
            <li>
              <input type="password" v-model="password" placeholder="密码" />
            </li>
          </ul>
          <div class="error" v-if="isError">
            <i class="fa fa-exclamation"></i>
            <p>{{ errorMsg }}</p>
          </div>
        </div>
        <div class="down">
          <div class="button-login">
            <button @click="login">登 录</button>
          </div>
          <div class="tnc">
            <input type="checkbox" class="checkbox" />
            <p class="text">
              阅读并同意旅客之家的<span class="highlight">服务协议</span>
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      userId: "",
      password: "",
      isError: false,
      errorMsg: "",
      prePath: "", //previous page url
    };
  },
  methods: {
    // invoked before this page instances are created
    // next() allow page to continue create instance
    beforeRouteEnter(to, from, next) {
      next((thisPage) => {
        thisPage.prePath = from.path; //get previous page url
      });
    },
    toIndex() {
      this.$router.push({ path: "/index" });
    },
    toRegister() {
      this.$router.push({
        path: "/register",
      });
    },
    // login user
    login() {
      this.isError = false;

      // show error message
      this.errorMsg = "";
      if (this.userId == "") {
        this.isError = true;
        this.errorMsg = "请输入手机号码";
        return;
      }
      if (this.password == "") {
        this.isError = true;
        this.errorMsg = "请输入密码";
        return;
      }

      // get user id from backend
      this.$axios
        .get(`UserController/getUserByIdPass/${this.userId}/${this.password}`)
        .then((response) => {
          let user = response.data.result;
          console.log(user);
          if (user == null) {
            this.isError = true;
            this.errorMsg = "用户名或密码不正确";
            this.userId = "";
            this.password = "";
          } else {
            //prevent data overflow in sessionStorage, so don't put userImg
            user.userImg = "";
            this.$setSessionStorage("user", user);
            // if user come from register page, redirect to index
            if (this.prePath == "/register") {
              this.$router.push({
                path: "/index",
              });
            } else {
              this.$router.go(-1);
            }
          }
        })
        .catch((error) => {
          console.error(error);
        });
    },
  },
};
</script>

<style scoped>
.wrapper {
  height: 100%;
  width: 100%;
}

/*************** nav bar *****************/
.wrapper .header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 4vw;
  background-color: var(--color-blue1);
}

.wrapper .header .left {
  display: flex;
}

.wrapper .header .left img {
  object-fit: contain;
  width: 10vw;
  padding-right: 2vw;
}

.wrapper .header .right {
  display: flex;
}

.wrapper .header .right p {
  margin: 2vw 1.5vw;
  color: white;
  font-family: var(--font-family);
  font-size: 1.5vw;
  line-height: 25px;
  cursor: pointer;
  position: relative;
}

/*************** index button animation *****************/
.wrapper .header .right p::before {
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

.wrapper .header .right p:hover::before {
  transform: translateX(-10%) translateY(100%);
}

/*************** login box *****************/
.wrapper .login {
  display: flex;
  justify-content: center;
  margin: 3vw;
  padding-bottom: 3vw;
}

.wrapper .login .box {
  height: 100%;
  width: 30vw;
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

.wrapper .login .box {
  font-size: 1.5vw;
}

/*************** form box *****************/
.wrapper .login .box .form {
  width: 100%;
  padding-top: 1vw;
}

.wrapper .login .box .form li {
  box-sizing: border-box;
  display: flex;
  align-items: center;
  padding: 1vw 0 0;
}

.wrapper .login .box .form li input {
  width: 100%;
  box-sizing: border-box;
  padding: 1.5vw 1vw;
  border-radius: 0.5vw;

  font-size: 1vw;
  border: 1px solid #c3c3c3;
  outline: none;
}

/*************** error msg *****************/
.wrapper .login .box .error {
  display: flex;
  align-items: center;
  margin: 1vw 0 0;
  padding: 0 1vw;
  height: 2vw;
  background-color: var(--color-orange2);
  border: 1px solid var(--color-orange);
  border-radius: 5px;
  color: var(--color-orange);
  font-weight: normal;
  font-size: 1vw;
}
.wrapper .login .box .error i {
  display: flex;
  justify-content: center;
  width: 1vw;
  margin-right: 0.5vw;
  font-size: 1vw;
  padding: 0.1vw;
  background-color: var(--color-orange);
  color: white;
  border-radius: 5vw;
}

/*************** login button *****************/
.wrapper .login .box .button-login {
  width: 100%;
  padding: 2vw 0 1vw;
}

.wrapper .login .box .button-login button {
  width: 100%;
  height: 4vw;
  background-color: var(--color-orange);
  border: none;
  outline: none;
  border-radius: 0.5vw;

  font-size: 1.5vw;
  font-weight: bold;
  color: white;

  cursor: pointer;
  position: relative;
  overflow: hidden;
  z-index: 1;
}

/*************** tnc button *****************/
.wrapper .login .box .tnc {
  display: flex;
  align-items: center;
}
.wrapper .login .box .tnc .checkbox {
  cursor: pointer;
  width: 1vw;
  margin: 0 0.5vw 0 0;
}
.wrapper .login .box .tnc .text {
  font-size: 1vw;
  font-weight: normal;
  color: var(--color-text);
}
.wrapper .login .box .tnc .text .highlight {
  color: var(--color-text2);
  cursor: pointer;
}

/*************** register button *****************/
.wrapper .register {
  position: absolute;
  bottom: 0;
  right: 0;
  font-size: 1vw;
  font-weight: normal;
  cursor: pointer;
  color: var(--color-text2);
  margin: 0 2vw 2vw 0;
}

/*************** login button animation *****************/
.wrapper .login .box .button-login button::before {
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

.wrapper .login .box .button-login button:hover::before {
  transform: translateX(0);
}

/*************** register button animation *****************/
.wrapper .register::before {
  content: "";
  position: absolute;
  bottom: 0;
  left: 0%;
  transform: translateX(-100%) translateY(100%) scale(0);
  transition: transform 0.3s ease-in-out;
  height: 0.1vw;
  width: 100%;
  border-radius: 2px;
  background-color: var(--color-blue1);
}

.wrapper .register:hover::before {
  transform: translateX(0%) translateY(100%);
}
</style>
