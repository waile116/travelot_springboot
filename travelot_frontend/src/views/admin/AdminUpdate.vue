<template>
  <div class="wrapper">
    <div class="content">
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

      <!------ data form ------>
      <table class="form">
        <tbody>
          <tr>
            <td style="width: 9vw">State Id:</td>
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
          <tr v-if="category !== 'Hotel'">
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

      <button @click="updateData">保存</button>

      <div class="insert">
        <p @click="openForm()">添加数据<i class="fa fa-chevron-right"></i></p>
      </div>

      <!------ sub data box ------>
      <div class="sub-data">
        <ul class="card">
          <li v-for="item in subDataArr" @click="openForm(item)">
            <img
              v-if="category !== 'Attraction'"
              :src="item[`${subCategoryL}Img`]"
            />
            <table class="data">
              <tbody>
                <tr>
                  <td style="width: 7vw">{{ this.category }} id</td>
                  <td>
                    {{ item[`${categoryL}Id`] }}
                  </td>
                </tr>
                <tr>
                  <td>name</td>
                  <td>{{ item.name }}</td>
                </tr>
                <tr v-if="category === 'Attraction'">
                  <td>desc</td>
                  <td>{{ item.desc }}</td>
                </tr>
                <tr v-if="category === 'Attraction'">
                  <td>quantity</td>
                  <td>{{ item.quantity }}</td>
                </tr>
                <tr v-if="category === 'Hotel'">
                  <td>type</td>
                  <td>{{ item.type }}</td>
                </tr>
                <tr v-if="category === 'Hotel'">
                  <td>capacity</td>
                  <td>{{ item.capacity }}</td>
                </tr>
                <tr>
                  <td>price</td>
                  <td>{{ item.price }}</td>
                </tr>
              </tbody>
            </table>
          </li>
        </ul>
      </div>

      <!------ sub data form ------>
      <div v-if="showForm" class="form-box">
        <div class="form-container">
          <div class="header">
            <i class="fa fa-close" @click="closeForm"></i>
          </div>
          <div v-if="category !== 'Attraction'" class="photo">
            <label class="change">
              <img :src="subData.img" />
              <input
                type="file"
                @change="uploadImg($event, 'subImg')"
                accept="image/*"
              />
            </label>
          </div>
          <table class="sub-form">
            <tbody>
              <tr>
                <td style="width: 7vw">{{ this.category }} id</td>
                <td>
                  <input v-model="subData[`${categoryL}Id`]" type="text" />
                </td>
              </tr>
              <tr>
                <td>name</td>
                <td><input v-model="subData.name" type="text" /></td>
              </tr>
              <tr v-if="category === 'Attraction'">
                <td>desc</td>
                <td><input v-model="subData.desc" type="text" /></td>
              </tr>
              <tr v-if="category === 'Attraction'">
                <td>quantity</td>
                <td><input v-model="subData.quantity" type="text" /></td>
              </tr>
              <tr v-if="category === 'Hotel'">
                <td>type</td>
                <td><input v-model="subData.type" type="text" /></td>
              </tr>
              <tr v-if="category === 'Hotel'">
                <td>capacity</td>
                <td><input v-model="subData.capacity" type="text" /></td>
              </tr>
              <tr>
                <td>price</td>
                <td><input v-model="subData.price" type="text" /></td>
              </tr>
            </tbody>
          </table>
          <button @click="updateSubData">保存</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "AdminUpdate",

  data() {
    return {
      user: {},
      isLogin: false,

      category:
        this.$route.query["category"].charAt(0).toUpperCase() +
        this.$route.query["category"].slice(1),
      categoryL: this.$route.query["category"],
      id: this.$route.query["id"],
      data: "",

      subCategory: "",
      subCategoryL: "",
      subDataArr: [],
      subData: {
        ticketId: -1,
        foodId: -1,
        roomId: -1,
        attractionId: "",
        restaurantId: "",
        hotelId: "",
        name: "",
        desc: "",
        type: "",
        capacity: "",
        price: "",
        quantity: "",
        img: "",
      },
      showForm: false,
    };
  },
  created() {
    this.user = this.$getSessionStorage("user");
    if (this.user != null) {
      this.isLogin = true;
    }

    const url = `${this.category}Controller/get${this.category}ById/${this.id}`;

    // get DATA
    this.$axios
      .get(url)
      .then((response) => {
        this.data = response.data.result;
        this.data.img = response.data.result[`${this.categoryL}Img`]; // give categoryImg to img to make it general
      })
      .catch((error) => console.error(error));

    switch (this.category) {
      case "Attraction":
        this.subCategory = "Ticket";
        break;
      case "Restaurant":
        this.subCategory = "Food";
        break;
      case "Hotel":
        this.subCategory = "Room";
        break;
    }
    this.subCategoryL =
      this.subCategory.charAt(0).toLowerCase() + this.subCategory.slice(1);

    const url2 = `${this.category}Controller/list${this.subCategory}ById/${this.id}`;

    // get sub DATA
    this.$axios
      .get(url2)
      .then((response) => {
        this.subDataArr = response.data.result;
      })
      .catch((error) => {
        console.error(error);
      });
  },
  methods: {
    resetSubData() {
      return {
        ticketId: -1,
        foodId: -1,
        roomId: -1,
        attractionId: "",
        restaurantId: "",
        hotelId: "",
        name: "",
        desc: "",
        type: "",
        capacity: "",
        price: "",
        quantity: "",
        img: "",
      };
    },

    openForm(item) {
      this.showForm = true;
      if (item) {
        this.subData = item;
        this.subData.img = item[`${this.subCategoryL}Img`];
      } else {
        this.subData[`${this.categoryL}Id`] = this.id;
      }
      console.log(this.subData);
    },
    closeForm() {
      this.showForm = false;
      this.subData = this.resetSubData(); //reset subData structure when closed
    },

    uploadImg(event, key) {
      const file = event.target.files[0]; //get first selected file
      if (!file) return;

      const reader = new FileReader();
      reader.readAsDataURL(file); //converts file into base64 string
      reader.onload = () => {
        if (key === "subImg") this.subData.img = reader.result;
        else this.data[key] = reader.result;
      };
    },

    updateData() {
      const url = `${this.category}Controller/save${this.category}/${this.id}/${
        this.data.stateId
      }/${this.data.name}/${this.data.desc}/${this.data.location}/${
        this.data.price
      }/${this.category !== "Hotel" ? this.data.openTime + "/" : ""}${
        this.data.rating
      }`;

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

    updateSubData() {
      const url = `${this.category}Controller/save${this.subCategory}/${
        this.subData[`${this.subCategoryL}Id`]
      }/${this.subData[`${this.categoryL}Id`]}/${this.subData.name}/${
        this.subCategory === "Ticket" ? this.subData.desc + "/" : ""
      }${
        this.subCategory === "Room"
          ? this.subData.type + "/" + this.subData.capacity + "/"
          : ""
      }${this.subData.price}${
        this.subCategory === "Ticket" ? "/" + this.subData.quantity : ""
      }`;

      console.log(url);
      this.$axios
        .post(url, { img: this.subData.img })
        .then((response) => {
          console.log(response.data.message);
          alert("保存成功");
          this.$router.go();
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

/*************** photo box *****************/
.wrapper .content .photo {
  display: flex;
  justify-content: center;
  padding: 1vw 1vw 1vw 0;
  position: relative;
  display: flex;
}

.wrapper .content .photo .change img {
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

/*************** form container *****************/
.wrapper .content table.form {
  justify-content: center;
  padding: 1vw;
  width: 100%;
  border-radius: 1vw;
  box-shadow: 3px 3px 3px #c3c3c3;
}

/*************** each individual form *****************/
.wrapper .content table.form tr,
.wrapper .content .form-box .form-container table.sub-form tr {
  align-items: center;
  height: 2vw;
  font-size: 1vw;
  overflow: hidden;
}

.wrapper .content table.form tr input,
.wrapper .content .form-box .form-container table.sub-form tr input {
  height: 2vw;
  width: 100%;
  border: 0.1vw solid var(--color-text);
  color: var(--color-text);
  font-family: "Manrope";
  font-size: 1vw;
}

.wrapper .content table.form tr input:focus,
.wrapper .content .form-box .form-container table.sub-form tr input::focus {
  border: 0.2vw solid var(--color-text);
  outline: none;
}

/*************** save button *****************/
.wrapper .content button {
  margin: 2vw 20vw;
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

/*************** insert button *****************/
.wrapper .content .insert {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  font-size: 1.5vw;
  margin-bottom: 2vw;
}
.wrapper .content .insert p {
  cursor: pointer;
}
.wrapper .content .insert p i {
  align-items: center;
  margin-left: 1vw;
}

/*************** card container *****************/
.wrapper .content .sub-data .card {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
}

/*************** each individual card *****************/
.wrapper .content .sub-data .card li {
  display: flex;
  margin: 1vw 2vw 1vw 0;
  height: 14vw;
  width: 30%;
  border-radius: 1vw;
  box-shadow: 3px 3px 3px #c3c3c3;
  overflow: hidden;
  cursor: pointer;
}

.wrapper .content .sub-data .card li img {
  height: 100%;
  width: 10vw;
  object-fit: cover;
  border-radius: 1vw 0 0 1vw;
}

.wrapper .content .sub-data .card li table.data {
  padding: 1vw;
  font-weight: normal;
  font-size: 1vw;
  height: 1vw;
  border-spacing: 0;
}

.wrapper .content .sub-data .card li table.data tr {
  height: 2vw;
}

/*************** form box *****************/
.wrapper .content .form-box {
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
.wrapper .content .form-box .form-container {
  background: white;
  width: 40vw;
  border-radius: 1vw;
  padding: 2vw;
}
.wrapper .content .form-box .form-container .header {
  display: flex;
  justify-content: flex-end;
}
.wrapper .content .form-box .form-container .header p {
  font-size: 1.5vw;
}
.wrapper .content .form-box .form-container .header i {
  cursor: pointer;
  font-size: 2vw;
}
.wrapper .content .form-box .form-container table.sub-form {
  justify-content: center;
  padding: 1vw;
  width: 100%;
}

/*************** save button *****************/
.wrapper .content .form-box .form-container button {
  margin: 2vw 3vw;
}
</style>
