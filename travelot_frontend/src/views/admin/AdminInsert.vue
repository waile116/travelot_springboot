<template>
  <div class="wrapper">
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
      <div class="photo">
        <label class="change">
          <img :src="data.img" />
          <input
            type="file"
            @change="uploadImg($event, 'img')"
            accept="image/*"
          />
        </label>
        <label class="change">
          <img :src="data.mapImg" />
          <input
            type="file"
            @change="uploadImg($event, 'mapImg')"
            accept="image/*"
          />
        </label>
      </div>
      <table class="form">
        <tbody>
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
          <tr>
            <td>Map Link:</td>
            <td><input v-model="data.mapLink" type="text" /></td>
          </tr>
        </tbody>
      </table>
      <button @click="insertData">保存</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "AdminInsert",

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
        img: "",
        mapImg: "",
        mapLink: "",
      },
    };
  },
  created() {
    this.user = this.$getSessionStorage("user");
    //check if login
    if (this.user != null) {
      this.isLogin = true;
    }
  },
  methods: {
    uploadImg(event, key) {
      const file = event.target.files[0]; //get first selected file
      if (!file) return;

      const reader = new FileReader();
      reader.readAsDataURL(file); //converts file into base64 string
      reader.onload = () => {
        this.data[key] = reader.result;
      };
    },

    insertData() {
      const url = `${
        this.category.charAt(0).toUpperCase() + this.category.slice(1)
      }Controller/save${
        this.category.charAt(0).toUpperCase() + this.category.slice(1)
      }/-1/${this.data.stateId}/${this.data.name}/${this.data.desc}/${
        this.data.location
      }/${this.data.price}/${
        this.category !== "hotel" ? this.data.openTime + "/" : ""
      }${this.data.rating}`;

      this.$axios
        .post(url, {
          img: this.data.img,
          mapImg: this.data.mapImg,
          mapLink: this.data.mapLink,
        })
        .then((response) => {
          console.log(response.data.message);
          alert("保存成功");
          this.$router.go(-1);
        })
        .catch((error) => {
          console.error(error);
        });
    },
  },
};
</script>

<style scoped>
/*************** content *****************/
.wrapper .content {
  display: flex;
  flex-direction: column;
  justify-content: center;
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
