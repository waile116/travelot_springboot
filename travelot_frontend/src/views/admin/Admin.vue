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
      <p class="insert" @click="toInsert">
        添加数据<i class="fa fa-chevron-right"></i>
      </p>
      <div class="category">
        <!-- :class is vue's class binding, when category matches, it will render  class "active"-->
        <p
          :class="{ active: category === 'attraction' }"
          @click="fetchData('attraction')"
        >
          景点
        </p>
        <p
          :class="{ active: category === 'hotel' }"
          @click="fetchData('hotel')"
        >
          酒店
        </p>
        <p
          :class="{ active: category === 'restaurant' }"
          @click="fetchData('restaurant')"
        >
          餐厅
        </p>
      </div>

      <div class="state">
        <label
          ><p>州属</p>
          <select v-model="state" @change="fetchData(category)">
            <option
              v-for="state in stateArr"
              :key="state.stateId"
              :value="state"
            >
              {{ state.name }}
            </option>
          </select>
        </label>
      </div>

      <div class="data">
        <ul class="card">
          <li
            v-for="item in dataArr"
            @click="toUpdate(category, item[category + 'Id'])"
          >
            <img :src="item[category + 'Img']" />
            <table class="info">
              <tr>
                <td style="width: 10%">id</td>
                <td>
                  {{ item[category + "Id"] }}
                </td>
              </tr>
              <tr>
                <td>stateid</td>
                <td>{{ item.stateId }}</td>
              </tr>
              <tr>
                <td>name</td>
                <td>{{ item.name }}</td>
              </tr>
              <tr>
                <td>desc</td>
                <td>{{ item.desc }}</td>
              </tr>
              <tr>
                <td>location</td>
                <td>{{ item.location }}</td>
              </tr>
              <tr>
                <td>price</td>
                <td>{{ item.price }}</td>
              </tr>
              <tr>
                <td>rating</td>
                <td>{{ item.rating }}</td>
              </tr>
            </table>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Admin",

  data() {
    return {
      user: {},
      isLogin: false,
      category: "attraction",
      stateArr: [],
      state: "",
      dataArr: [],
    };
  },
  created() {
    this.user = this.$getSessionStorage("user");
    if (this.user != null) {
      this.isLogin = true;
    }

    //get state with stateId
    this.$axios
      .get("StateController/listState")
      .then((response) => {
        this.stateArr = response.data.result;
        this.state = this.stateArr[0]; //default value
        this.fetchData(this.category); // list data right after getting state info
      })
      .catch((error) => {
        console.error(error);
      });
  },
  methods: {
    // fetch data based on selected category
    fetchData(category) {
      // changing state and category will update this.state and this.category, and will list different category
      this.category = category;

      const url = `${
        this.category.charAt(0).toUpperCase() + this.category.slice(1)
      }Controller/list${
        this.category.charAt(0).toUpperCase() + this.category.slice(1)
      }ById/${this.state.stateId}`;

      this.$axios
        .get(url)
        .then((response) => {
          this.dataArr = response.data.result;
        })
        .catch((error) => {
          console.error(error);
        });
    },

    toLogout() {
      this.$removeSessionStorage("user");
      this.$router.push({
        path: "/login",
      });
    },

    toInsert() {
      this.$router.push({
        path: "/admin/insert",
      });
    },

    toUpdate(category, id) {
      this.$router.push({ path: "/admin/update", query: { category, id } });
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

/*************** nav menu *****************/
.wrapper .nav .menu {
  align-items: center;
  color: white;
  font-family: var(--font-family);
  font-size: 1.5vw;
  line-height: 25px;
  cursor: pointer;
  margin: 0 2vw;
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

/*************** content *****************/
.wrapper .content {
  display: flex;
  flex-direction: column;
  margin: 1.5vw 4vw 0 4vw;
}

.wrapper .content .insert {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  cursor: pointer;
  font-size: 1.5vw;
}

.wrapper .content .insert i {
  align-items: center;
  margin-left: 1vw;
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

/*************** state selection *****************/
.wrapper .content .state {
  display: flex;
  margin-bottom: 1vw;
  justify-content: space-between;
  border: solid 1px black;
  padding: 1vw 1.5vw;
  height: 100%;
  width: 12vw;
  border-radius: 0.5vw;
  border: 1px solid #c3c3c3;
  outline: none;
}

.wrapper .content .state label {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.wrapper .content .state p {
  font-size: 1.2vw;
  margin-right: 1vw;
}

.wrapper .content .state select,
input {
  font-size: 1.2vw;
  border: none;
  outline: none;
  color: var(--color-text);
  cursor: pointer;
  margin-left: 2vw;
}

select {
  appearance: none;
  -webkit-appearance: none;
  width: 5vw;
}

/*************** card container *****************/
.wrapper .content .data .card {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

/*************** each individual card *****************/
.wrapper .content .data .card li {
  display: flex;
  margin: 1vw 2vw 1vw 0;
  height: 20vw;
  width: 100%;
  border-radius: 1vw;
  box-shadow: 3px 3px 3px #c3c3c3;
  overflow: hidden;
  cursor: pointer;
}

.wrapper .content .data .card li img {
  height: 100%;
  width: 25vw;
  object-fit: cover;
  border-radius: 1vw 0 0 1vw;
}

.wrapper .content .data .card li table.info {
  padding: 1vw;
  font-weight: normal;
  font-size: 1vw;
  height: 1vw;
  border-spacing: 0;
}

.wrapper .content .data .card li table.info tr {
  height: 2vw;
}
</style>
