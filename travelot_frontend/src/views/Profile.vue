<template>
  <div class="wrapper">
    <div class="nav">
      <div class="left">
        <p v-if="isLogin" @click="toBack">
          <i class="fa fa-chevron-left"></i>返回
        </p>
      </div>
    </div>
    <div class="container">
      <div class="box">
        <p class="title">个人信息设置</p>
        <div class="content">
          <div class="photo">
            <div class="pic">
              <img :src="pic" alt="profile picture" />
              <label class="change">
                <input type="file" @change="uploadImg" accept="image/*" />
                <i class="fa fa-pencil-square-o"></i>
              </label>
            </div>
            <button v-if="pic != user.userImg" @click="updateImg">保存</button>
          </div>
          <ul class="info">
            <li>
              <p>手机号码</p>
              <span>{{ user.userId }}</span>
            </li>
            <li>
              <p>姓名</p>
              <span>{{ user.userName }}</span>
            </li>
            <li>
              <p>昵称</p>
              <span>{{ user.userAlias }}</span>
            </li>
            <li>
              <p>性别</p>
              <span>{{ user.userSex ? "男" : "女" }}</span>
            </li>
          </ul>
        </div>
        <ul class="tool">
          <li @click="toOrderList">
            <i class="fa fa-file"></i>
            <p>我的订单</p>
          </li>
          <li @click="openInfo">
            <i class="fa fa-address-card"></i>
            <p>更改个人信息</p>
          </li>
          <li @click="openPass">
            <i class="fa fa-lock"></i>
            <p>更改密码</p>
          </li>
        </ul>
        <div class="logout" @click="toLogout">
          <button><i class="fa fa-sign-out"></i>退出登录</button>
        </div>
      </div>

      <div v-if="showInfo" class="info-box">
        <div class="info-content">
          <div class="header">
            <div></div>
            <p>更改个人信息</p>
            <i class="fa fa-close" @click="closeInfo"></i>
          </div>
          <ul class="info">
            <li>
              <p>姓名</p>
              <input type="text" v-model="temp.userName" />
            </li>
            <li>
              <p>昵称</p>
              <input type="text" v-model="temp.userAlias" />
            </li>
            <li>
              <p>性别</p>
              <div class="select">
                <input
                  type="radio"
                  name="sex"
                  v-model="temp.userSex"
                  value="1"
                />男
                <input
                  type="radio"
                  name="sex"
                  v-model="temp.userSex"
                  value="0"
                />女
              </div>
            </li>
          </ul>
          <div class="error" v-if="isError">
            <i class="fa fa-exclamation"></i>
            <p>{{ errorMsg }}</p>
          </div>
          <div class="save" @click="saveInfo"><button>确认</button></div>
        </div>
      </div>

      <div v-if="showPass" class="info-box">
        <div class="info-content">
          <div class="header">
            <div></div>
            <p>更改密码</p>
            <i class="fa fa-close" @click="closePass"></i>
          </div>
          <ul class="info">
            <li>
              <p>旧密码</p>
              <input type="password" v-model="temp.oldPass" />
            </li>
            <li>
              <p>新密码</p>
              <input type="password" v-model="temp.newPass" />
            </li>
            <li>
              <p>确认密码</p>
              <input type="password" v-model="temp.confirmPass" />
            </li>
          </ul>
          <div class="error" v-if="isError">
            <i class="fa fa-exclamation"></i>
            <p>{{ errorMsg }}</p>
          </div>
          <div class="save" @click="savePass"><button>确认</button></div>
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
      isLogin: false,
      user: {},
      pic: "",
      showInfo: false,
      showPass: false,
      isError: false,
      errorMsg: "",
      temp: {
        userName: "",
        userAlias: "",
        userSex: "",
        oldPass: "",
        newPass: "",
        confirmPass: "",
      },
    };
  },
  created() {
    this.user = this.$getSessionStorage("user");
    // check if login
    if (this.user != null) {
      this.isLogin = true;
    }
    this.pic = this.user.userImg;
    this.temp.userName = this.user.userName;
    this.temp.userAlias = this.user.userAlias;
    this.temp.userSex = this.user.userSex;
  },
  components: {
    Nav,
  },
  methods: {
    uploadImg(event) {
      const file = event.target.files[0]; //get first selected file
      if (!file) return;

      const reader = new FileReader();
      reader.readAsDataURL(file); //converts file into base64 string
      reader.onload = () => {
        this.pic = reader.result;
      };
    },

    updateImg() {
      this.$axios
        .post(`UserController/updateUserImgById/${this.user.userId}`, {
          userImg: this.pic,
        }) //send image as JSON body because too big
        .then((response) => {
          if (response.data.result > 0) {
            console.log(response.data.message);

            //update user in session storage
            this.$axios
              .get(`UserController/getUserById/${this.user.userId}`)
              .then((response) => {
                let user = response.data.result;
                this.$setSessionStorage("user", user);
              });
            alert("更新用户头像成功");
            this.$router.go();
          } else {
            alert("更新用户头像失败");
          }
        })
        .catch((error) => {
          console.error(error);
        });
    },

    openInfo() {
      this.showInfo = true;
    },
    closeInfo() {
      this.showInfo = false;
      this.isError = false;
    },
    openPass() {
      this.showPass = true;
    },
    closePass() {
      this.showPass = false;
      this.isError = false;
    },

    saveInfo() {
      if (this.temp.userName == "") {
        this.isError = true;
        this.errorMsg = "姓名不能为空";
        return;
      }
      if (this.temp.userAlias == "") {
        this.isError = true;
        this.errorMsg = "昵称不能为空";
        return;
      }

      //update user info through backend
      this.$axios
        .post(
          `UserController/saveUser/${this.user.userId}/-1/${this.temp.userName}/${this.temp.userAlias}/${this.temp.userSex}`
        )
        .then((response) => {
          if (response.data.result > 0) {
            console.log(response.data.message);

            //update user in session storage
            this.$axios
              .get(`UserController/getUserById/${this.user.userId}`)
              .then((response) => {
                let user = response.data.result;
                this.$setSessionStorage("user", user);
              });

            alert("更改个人信息成功");
            this.$router.go();
          } else {
            alert("更改个人信息失败");
          }
        })
        .catch((error) => {
          console.error(error);
        });
    },

    savePass() {
      if (this.temp.oldPass == "") {
        this.isError = true;
        this.errorMsg = "旧密码不能为空";
        return;
      }
      if (this.temp.newPass == "") {
        this.isError = true;
        this.errorMsg = "新密码不能为空";
        return;
      }
      if (this.temp.confirmPass == "") {
        this.isError = true;
        this.errorMsg = "确认密码不能为空";
        return;
      }

      // get user id from backend
      this.$axios
        .get(
          `UserController/getUserByIdPass/${this.user.userId}/${this.temp.oldPass}`
        )
        .then((response) => {
          let user = response.data.result;

          if (user == null) {
            this.isError = true;
            this.errorMsg = "旧密码不正确";
            // reset input
            this.temp.oldPass = "";
            this.temp.newPass = "";
            this.temp.confirmPass = "";
            return;
          }
          if (this.temp.oldPass == this.temp.newPass) {
            this.isError = true;
            this.errorMsg = "旧密码与新密码不能相同";
            // reset input
            this.temp.oldPass = "";
            this.temp.newPass = "";
            this.temp.confirmPass = "";
            return;
          }

          //update password through backend
          this.$axios
            .post(
              `UserController/updateUserPassword/${this.user.userId}/${this.temp.newPass}`
            )
            .then((response) => {
              if (response.data.result > 0) {
                console.log(response.data.message);
                alert("更改密码成功");
                this.$router.go();
              } else {
                alert("更改密码失败");
              }
            });
        })
        .catch((error) => {
          console.error(error);
        });
    },

    toBack() {
      this.$router.go(-1);
    },
    toOrderList() {
      this.$router.push({ path: "/orderList" });
    },
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
.wrapper {
  position: relative;
}

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

/*************** profile box *****************/
.wrapper .container {
  display: flex;
  justify-content: center;
  margin: 3vw;
  padding-bottom: 3vw;
}

.wrapper .container .box {
  height: 100%;
  width: 50vw;
  border-radius: 1vw;
  box-shadow: 3px 3px 3px #c3c3c3;
  border: 0.1vw solid #c3c3c3;
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
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.wrapper .container .box .content .photo img {
  width: 10vw;
  height: 10vw;
  border-radius: 10vw;
  border: 0.1vw solid #c3c3c3;
  object-fit: cover;
}

.wrapper .container .box .content .photo .change {
  cursor: pointer;
  font-size: 1.5vw;
  padding-bottom: 1vw;
}

.wrapper .container .box .content .photo button {
  margin: 1vw 0 1vw 2vw;
  width: 6vw;
  height: 2vw;
  background-color: var(--color-orange);
  border: none;
  outline: none;
  border-radius: 0.5vw;
  font-size: 1vw;
  font-weight: bold;
  color: white;
  cursor: pointer;
}

.wrapper .container .box .content .photo .change input[type="file"] {
  display: none;
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
  font-size: 1.25vw;
  margin-bottom: 1vw;
}

.wrapper .container .box .content .info li p {
  flex: 1;
}

.wrapper .container .box .content .info li span {
  font-weight: normal;
  flex: 4;
}

/*************** tool card *****************/
.wrapper .container .box .tool {
  display: flex;
  flex-direction: column;
  margin: 0;
}
.wrapper .container .box .tool li {
  padding: 3vw 2vw;
  margin-bottom: 1vw;
  font-size: 1.25vw;
  display: flex;
  align-items: center;
  border-radius: 1vw;
  border: 0.1vw solid #c3c3c3;
  cursor: pointer;
  position: relative;
  z-index: 1;
  overflow: hidden;
  transition: color 0.2s ease-in-out;
}
.wrapper .container .box .tool li i {
  margin-right: 1.5vw;
}

/*************** tool card transition *****************/
.wrapper .container .box .tool li::before {
  content: "";
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%) scale(0);
  transition: transform 0.2s ease-in-out;
  height: 100%;
  width: 100%;
  background-color: var(--color-blue1);
  z-index: -1;
}
.wrapper .container .box .tool li:hover::before {
  transform: translateX(-50%) scale(1);
}
.wrapper .container .box .tool li:hover {
  background-color: transparent;
  color: white;
}

/*************** info box *****************/
.wrapper .container .info-box {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.2);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}
.wrapper .container .info-box .info-content {
  background: white;
  width: 40vw;
  border-radius: 1vw;
  padding: 2vw;
}
.wrapper .container .info-box .info-content .header {
  display: flex;
  justify-content: space-between;
}
.wrapper .container .info-box .info-content .header p {
  font-size: 1.5vw;
}
.wrapper .container .info-box .info-content .header i {
  cursor: pointer;
  font-size: 2vw;
}
.wrapper .container .info-box .info-content .info {
  margin: 1vw 5vw;
  display: flex;
  flex-direction: column;
}
.wrapper .container .info-box .info-content .info li {
  display: flex;
  margin-bottom: 1vw;
  align-items: center;
}
.wrapper .container .info-box .info-content .info li p {
  font-size: 1.2vw;
  margin-right: 1vw;
  font-weight: normal;
  width: 7vw;
}
.wrapper .container .info-box .info-content .info li input {
  padding: 1vw;
  box-sizing: border-box;
  font-size: 1.2vw;
  width: 100%;
  border-radius: 1vw;
  border: 1px solid #c3c3c3;
  color: var(--color-text);
  font-family: var(--font-family);
  outline: none;
  resize: none;
}
.wrapper .container .info-box .info-content .info li .select {
  display: flex;
  align-items: center;
  font-size: 1vw;
  font-weight: normal;
}
.wrapper .container .info-box .info-content .info li .select input {
  margin: 0 0.5vw 0 1vw;
  height: 1vw;
  cursor: pointer;
  accent-color: var(--color-text);
}

/*************** button *****************/
.wrapper .container .info-box .info-content .save,
.wrapper .container .box .logout {
  display: flex;
  justify-content: center;
}
.wrapper .container .info-box .info-content .save button,
.wrapper .container .box .logout button {
  width: 14vw;
  height: 3vw;
  background-color: var(--color-orange);
  border: none;
  outline: none;
  border-radius: 0.5vw;
  font-size: 1.25vw;
  font-weight: bold;
  color: white;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  z-index: 1;
}
.wrapper .container .box .logout button {
  height: 5vw;
  border-radius: 1vw;
}
.wrapper .container .box .logout i {
  margin-right: 1.5vw;
}

/*************** button transition *****************/
.wrapper .container .info-box .info-content .save button::before,
.wrapper .container .box .logout button::before {
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
.wrapper .container .info-box .info-content .save button:hover::before,
.wrapper .container .box .logout button:hover::before {
  transform: translateX(0);
}

/*************** error msg *****************/
.wrapper .container .info-box .info-content .error {
  display: flex;
  align-items: center;
  padding: 0 1vw;
  margin: 0 4vw 1vw;
  height: 2vw;
  background-color: var(--color-orange2);
  border: 1px solid var(--color-orange);
  border-radius: 0.5vw;
  color: var(--color-orange);
  font-weight: normal;
  font-size: 1vw;
}
.wrapper .container .info-box .info-content .error i {
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
</style>
