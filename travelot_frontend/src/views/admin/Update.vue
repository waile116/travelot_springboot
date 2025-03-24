<template>
  <div class="wrapper">
    <div class="nav">
      <div class="left">
        <p v-if="isLogin" @click="toAdmin">
          <i class="fa fa-chevron-left"></i>返回首页
        </p>
      </div>
    </div>
    <div class="content">
      <div class="photo">
        <img :src="data.img" />
        <label class="change">
          <input type="file" @change="uploadImg" accept="image/*" />
          <i class="fa fa-pencil-square-o"></i>
        </label>
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
      <button @click="updateData">保存</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "Update",

  data() {
    return {
      user: {},
      isLogin: false,
      category: this.$route.query["category"],
      id: this.$route.query["id"],
      data: {
        stateId: "",
        name: "",
        desc: "",
        location: "",
        price: "",
        openTime: "",
        rating: "",
        img: "",
      },
    };
  },
  created() {
    this.user = this.$getSessionStorage("user");
    if (this.user != null) {
      this.isLogin = true;
    }

    const url = `${
      this.category.charAt(0).toUpperCase() + this.category.slice(1)
    }Controller/get${
      this.category.charAt(0).toUpperCase() + this.category.slice(1)
    }ById/${this.id}`;

    this.$axios
      .get(url)
      .then((response) => {
        this.data = response.data.result;
        this.data.img = response.data.result[this.category + "Img"]; // give categoryImg to img to make it general
      })
      .catch((error) => console.error(error));
  },
  methods: {
    uploadImg(event) {
      const file = event.target.files[0]; //get first selected file
      if (!file) return;

      const reader = new FileReader();
      reader.readAsDataURL(file); //converts file into base64 string
      reader.onload = () => {
        this.data.img = reader.result;
      };
    },

    updateData() {
      const url = `${
        this.category.charAt(0).toUpperCase() + this.category.slice(1)
      }Controller/save${
        this.category.charAt(0).toUpperCase() + this.category.slice(1)
      }/${this.id}/${this.data.stateId}/${this.data.name}/${this.data.desc}/${
        this.data.location
      }/${this.data.price}/${
        this.category !== "hotel" ? this.data.openTime + "/" : ""
      }${this.data.rating}`;

      this.$axios
        .post(url, { img: this.data.img })
        .then((response) => {
          console.log(response.data.message);
          alert("保存成功");
          this.$router.go(-1);
        })
        .catch((error) => {
          console.error(error);
        });
    },

    toAdmin() {
      this.$router.push({
        path: "/admin",
      });
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

/*************** content *****************/
.wrapper .content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin: 1.5vw 4vw 0 4vw;
}

/*************** photo box *****************/
.wrapper .content .photo {
  display: flex;
  justify-content: center;
  padding: 1vw 1vw 1vw 0;
  position: relative;
  display: flex;
}

.wrapper .content .photo img {
  width: 16vw;
  height: 12vw;
  border: 0.1vw solid #c3c3c3;
  object-fit: cover;
}

.wrapper .content .photo .change {
  cursor: pointer;
  font-size: 1.5vw;
  position: absolute;
  bottom: 0;
  right: 39%;
  padding-bottom: 1vw;
}

.wrapper .content .photo .change input[type="file"] {
  display: none;
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

.wrapper .content button {
  margin: 2vw 20vw 4vw 20vw;
  color: white;
  font-weight: 700;
  font-size: 1.5vw;
  background-color: var(--color-orange);
  padding: 1vw 2.5vw;
  outline: none;
  border: none;
  border-radius: 1vw;
  cursor: pointer;
}
</style>
