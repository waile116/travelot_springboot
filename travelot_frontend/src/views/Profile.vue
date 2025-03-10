<template>
  <div class="wrapper">
    <Nav></Nav>
    <div class="container">
      <div class="box">
        <p class="title">个人信息设置</p>
        <div class="content">
          <div class="photo">
            <img :src="user.userImg" alt="profile picture" />
            <label class="change">
              <input type="file" @change="FileUpload" accept="image/*" />
              <i class="fa fa-pencil-square-o"></i>
            </label>
          </div>
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
              <p>昵称</p>
              <span>{{ user.alias }}</span>
            </li>
            <li>
              <p>性别</p>
              <span>{{ user.userSex ? "男" : "女" }}</span>
            </li>
          </ul>
        </div>
        <ul class="tool">
          <li @click="toLogout"><p>退出登录</p></li>
        </ul>
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
  methods: {
    FileUpload(event) {
      const file = event.target.files[0]; //get first selected file
      if (!file) return;

      const reader = new FileReader();
      reader.readAsDataURL(file); //converts file into base64 string
      reader.onload = () => {
        this.user.userImg = reader.result;

        this.$axios
          .post(`UserController/updateUserImgById/${this.user.userId}`, {
            userImg: this.user.userImg,
          }) //send image as JSON body because too big
          .then((response) => {
            console.log(response.data.message);

            //update user in session storage
            this.$axios
              .get(
                `UserController/getUserByIdPass/${this.user.userId}/${this.user.password}`
              )
              .then((response) => {
                let user = response.data.result;
                this.$setSessionStorage("user", user);
              });
          })
          .catch((error) => {
            console.error(error);
          });
      };
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
  align-items: center;
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
  position: absolute;
  bottom: 0;
  right: 40%;
  padding-bottom: 1vw;
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

/*************** tool box *****************/
.wrapper .container .box .tool {
  display: flex;
  flex-direction: column;
  margin: 0;
}

.wrapper .container .box .tool li {
  padding: 3vw 2vw;
  margin-bottom: 1vw;
  font-size: 1.25vw;

  border-radius: 1vw;
  border: 0.1vw solid #c3c3c3;
  cursor: pointer;
  position: relative;
  z-index: 1;
  overflow: hidden;
  transition: color 0.2s ease-in-out;
}

/*************** tool background transition *****************/
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

/*************** tool text transition*****************/
.container .box .tool li:hover {
  background-color: transparent;
  color: white;
}
</style>
