<template>
  <div class="wrapper">
    <div class="header">
      <div class="left">
        <img src="../assets/logo.png" alt="logo" />
      </div>
      <div class="right">
        <p @click="toLogin">登录</p>
        <p @click="toIndex">首页</p>
      </div>
    </div>
    <div class="container">
      <div class="box">
        <div class="up">
          <p class="title">用户注册</p>
          <ul class="form">
            <li>
              <input type="text" v-model="userId" placeholder="手机号码" />
            </li>
            <li>
              <input type="password" v-model="password" placeholder="密码" />
            </li>
            <li>
              <input
                type="password"
                v-model="confirmPassword"
                placeholder="确认密码"
              />
            </li>
            <li>
              <input type="text" v-model="username" placeholder="用户名称" />
            </li>
            <li class="sex">
              <div class="title">性别：</div>
              <div class="select">
                <input type="radio" name="sex" v-model="userSex" value="1" />男
                <input type="radio" name="sex" v-model="userSex" value="0" />女
              </div>
            </li>
          </ul>
          <div class="error" v-if="isError">
            <i class="fa fa-exclamation"></i>
            <p>{{ errorMsg }}</p>
          </div>
        </div>
        <div class="down">
          <div class="button-register">
            <button @click="register">注 册</button>
          </div>
          <div class="tnc">
            <input type="checkbox" class="checkbox" id="checkbox" />
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
  name: "Register",
  data() {
    return {
      userId: "",
      password: "",
      confirmPassword: "",
      username: "",
      userSex: 1,
      isError: false,
      errorMsg: "",
    };
  },
  methods: {
    toIndex() {
      this.$router.push({ path: "/index" });
    },
    toLogin() {
      this.$router.push({
        path: "/login",
      });
    },
    // register user
    register() {
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
      if (this.password != this.confirmPassword) {
        this.isError = true;
        this.errorMsg = "密码不一致";
        return;
      }
      if (this.username == "") {
        this.isError = true;
        this.errorMsg = "请输入用户名";
        return;
      }
      if (!document.getElementById("checkbox").checked) {
        this.isError = true;
        this.errorMsg = "请阅读并同意旅客之家的服务协议";
        return;
      }

      this.$axios
        .get(`UserController/getUserById/${this.userId}`)
        .then((response) => {
          if (response.data.result) {
            this.isError = true;
            this.errorMsg = "此手机号码已存在";
            console.log("Register failed");
            //reset input
            this.userId = "";
            this.password = "";
            this.confirmPassword = "";
            this.username = "";
          }
        });

      //update user table through backend
      this.$axios
        .post(
          `UserController/saveUser/${this.userId}/${this.password}/${this.username}/${this.userSex}`
        )
        .then((response) => {
          if (response.data.result > 0) {
            alert("注册成功");
            console.log(response.data.message);
            this.$router.push("/login");
          } else {
            alert("注册失败");
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

/*************** register box *****************/
.wrapper .container {
  display: flex;
  justify-content: center;
  margin: 3vw;
  padding-bottom: 3vw;
}

.wrapper .container .box {
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

.wrapper .container .box .title {
  font-size: 1.5vw;
}

/*************** form box *****************/
.wrapper .container .box .form {
  width: 100%;
  padding-top: 1vw;
}

.wrapper .container .box .form li {
  box-sizing: border-box;
  display: flex;
  align-items: center;
  padding: 1vw 0 0;
}

.wrapper .container .box .form li input {
  width: 100%;
  box-sizing: border-box;
  padding: 1.5vw 1vw;
  border-radius: 0.5vw;

  font-size: 1vw;
  border: 1px solid #c3c3c3;
  outline: none;
}

/*************** sex selection *****************/
.wrapper .container .box .form .sex .title {
  font-size: 1vw;
  font-weight: normal;
}
.wrapper .container .box .form .sex .select {
  display: flex;
  align-items: center;
  font-size: 1vw;
  font-weight: normal;
}
.wrapper .container .box .form .sex .select input {
  margin: 0 0.5vw 0 1vw;
  width: 1.5vw;
  cursor: pointer;
  accent-color: var(--color-text);
}

/*************** error msg *****************/
.wrapper .container .box .error {
  display: flex;
  align-items: center;
  margin: 1vw 0 0;
  padding: 0 1vw;
  height: 2vw;
  background-color: var(--color-orange2);
  border: 1px solid var(--color-orange);
  border-radius: 0.5vw;
  color: var(--color-orange);
  font-weight: normal;
  font-size: 1vw;
}
.wrapper .container .box .error i {
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

/*************** register button *****************/
.wrapper .container .box .button-register {
  width: 100%;
  padding: 2vw 0 1vw;
}

.wrapper .container .box .button-register button {
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
.wrapper .container .box .tnc {
  display: flex;
  align-items: center;
}
.wrapper .container .box .tnc .checkbox {
  cursor: pointer;
  width: 1vw;
  margin: 0 0.5vw 0 0;
}
.wrapper .container .box .tnc .text {
  font-size: 1vw;
  font-weight: normal;
  color: var(--color-text);
}
.wrapper .container .box .tnc .text .highlight {
  color: var(--color-text2);
  cursor: pointer;
}

/*************** register button animation *****************/
.wrapper .container .box .button-register button::before {
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

.wrapper .container .box .button-register button:hover::before {
  transform: translateX(0);
}

/*************** login button animation *****************/
.wrapper .login::before {
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

.wrapper .login:hover::before {
  transform: translateX(0%) translateY(100%);
}
</style>
