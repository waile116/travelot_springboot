<template>
  <div class="wrapper">
    <Nav></Nav>
    <div class="container">
      <ul class="select">
        <li class="state">
          <label
            ><p>州属</p>
            <select v-model="state" @change="listRestaurant">
              <option
                v-for="state in stateArr"
                :key="state.stateId"
                :value="state"
              >
                {{ state.name }}
              </option>
            </select>
          </label>
        </li>
        <li class="search">
          <input type="text" v-model="query" placeholder="搜索餐厅..." />
        </li>
        <button class="button" @click="search">
          <i class="fa fa-search"></i>搜索
        </button>
      </ul>
      <div class="hotel">
        <p>{{ state.name }}热门餐厅</p>
        <ul class="card">
          <li
            v-for="restaurant in filter"
            @click="toRestaurantInfo(restaurant.restaurantId)"
          >
            <img :src="restaurant.restaurantImg" />
            <div class="info">
              <div class="header">
                <p class="title">{{ restaurant.name }}</p>
                <div class="rating">
                  <p>评分</p>
                  <p class="score">{{ restaurant.rating }}</p>
                  <p>/5</p>
                </div>
                <div class="location">
                  <i class="fa fa-map-marker"></i>
                  <p>{{ restaurant.location }}</p>
                </div>
              </div>
              <div class="cost">
                <p>人均</p>
                <p class="price">{{ `¥${restaurant.price}` }}</p>
              </div>
            </div>
            <div class="arrow">
              <i class="fa fa-chevron-right"></i>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import Nav from "../components/Nav.vue";

export default {
  name: "RestaurantList",
  data() {
    return {
      stateArr: [],
      state: "",
      filter: [],
      restaurantArr: [],
      query: "",
    };
  },
  created() {
    //get state with stateId
    this.$axios
      .get("StateController/listState")
      .then((response) => {
        this.stateArr = response.data.result;
        this.state = this.stateArr[0]; //default value
        this.listRestaurant(); // list restaurant right after getting state info
      })
      .catch((error) => {
        console.error(error);
      });
  },
  components: {
    Nav,
  },
  methods: {
    //get restaurant list with stateId
    listRestaurant() {
      this.$axios
        .get(`RestaurantController/listRestaurantById/${this.state.stateId}`)
        .then((response) => {
          this.restaurantArr = response.data.result;
          this.filter = this.restaurantArr;
        })
        .catch((error) => {
          console.error(error);
        });
    },

    search() {
      // if search is empty, show all restaurants
      if (this.query === "") {
        this.filter = this.restaurantArr;
      } else {
        this.filter = this.restaurantArr.filter((restaurant) =>
          restaurant.name.includes(this.query)
        );
      }
    },

    toRestaurantInfo(id) {
      this.$router.push({ path: "/restaurantInfo", query: { id: id } });
    },
  },
};
</script>

<style scoped>
.wrapper {
  position: relative;
}
.wrapper .container {
  display: flex;
  flex-direction: column;
  margin: 1.5vw 4vw 0 4vw;
}

/*************** selection tab *****************/
.wrapper .container .select {
  display: flex;
  margin: 0 20vw 1vw;
  border-radius: 1vw;
  box-shadow: 3px 3px 3px #c3c3c3;
}
.wrapper .container .select li,
button {
  margin: 1vw;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 4vw;
  border-radius: 1vw;
  border: 1px solid #c3c3c3;
  outline: none;
}
.wrapper .container .select li label {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.wrapper .container .select p {
  font-size: 1.2vw;
  margin-bottom: 0.5vw;
}
.wrapper .container .select select,
input {
  font-size: 1.2vw;
  border: none;
  outline: none;
  color: var(--color-text);
  cursor: pointer;
}
.wrapper .container .select .state {
  flex: 4;
  padding: 1vw 1.5vw;
}
.wrapper .container .select .search {
  flex: 4;
  padding: 1vw 1.5vw;
}
.wrapper .container .select .button {
  flex: 2;
  padding: 3vw 0;
  background-color: var(--color-orange);
  border: none;
  outline: none;
  font-size: 1.2vw;
  font-weight: bold;
  color: white;
  cursor: pointer;
  justify-content: center;
  position: relative;
  overflow: hidden;
  z-index: 1;
}
.wrapper .container .select .button i {
  margin-right: 1vw;
}
::placeholder {
  color: #c3c3c3;
}
select {
  appearance: none;
  -webkit-appearance: none;
  width: 8vw;
}
.wrapper .container .select .button::before {
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
.wrapper .container .select .button:hover::before {
  transform: translateX(0);
}

/*************** title *****************/
.wrapper .container p {
  font-size: 2vw;
}

/*************** card container *****************/
.wrapper .container .card {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

/*************** each individual card *****************/
.wrapper .container .card li {
  display: flex;
  margin: 1vw 2vw 1vw 0;
  height: 16vw;
  width: 100%;
  border-radius: 1vw;
  box-shadow: 3px 3px 3px #c3c3c3;
  cursor: pointer;
  position: relative;
  z-index: 1;
  overflow: hidden;
}
.wrapper .container .card li:last-child {
  margin-bottom: 2vw;
}
.wrapper .container .card li .info {
  flex: 5;
  padding: 1vw 2vw;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  font-weight: 500;
}
.wrapper .container .card li .info p {
  font-size: 1vw;
}
.wrapper .container .card li .info .header .title {
  font-size: 1.5vw;
}
.wrapper .container .card li .info .header .rating,
.wrapper .container .card li .info .header .location {
  display: flex;
  align-items: center;
  margin-top: 0.5vw;
}
.wrapper .container .card li .info .header .rating {
  align-items: flex-end;
}
.wrapper .container .card li .info .header .rating .score {
  font-size: 1.25vw;
}
.wrapper .container .card li .info .header .location {
  width: 30vw;
}
.wrapper .container .card li .info .header .location i {
  font-size: 1.5vw;
  margin-right: 0.8vw;
}
.wrapper .container .card li .info .cost .price {
  font-size: 1.5vw;
  font-weight: 700;
  text-align: end;
}
.wrapper .container .card li .info .header .rating .score,
.wrapper .container .card li .info .cost .price {
  padding: 0 0.25vw;
}
.wrapper .container .card li .info .cost {
  display: flex;
  align-items: flex-end;
  line-height: 0.8;
  align-self: flex-end;
}
.wrapper .container .card li .arrow {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
}
.wrapper .container .card li .arrow i {
  font-size: 2vw;
}

/*************** hide scrollbar *****************/
.wrapper .container .card::-webkit-scrollbar {
  background-color: transparent;
  height: 10px;
}

/*************** card background transition*****************/
.wrapper .container .card li img {
  flex: 2;
  height: 100%;
  width: 12vw;
  object-fit: cover;
  border-radius: 1vw 0 0 1vw;
  transition: transform 0.3s ease-in-out;
}
.wrapper .container .card li::before {
  content: "";
  position: absolute;
  bottom: 0;
  left: 0;
  transform: translateX(-100%);
  transition: transform 0.2s ease-in-out;
  height: 100%;
  width: 100%;
  background-color: var(--color-blue1);
  z-index: -1;
}
.wrapper .container .card li:hover::before {
  transform: translateX(0);
}
.wrapper .container .card li:hover img {
  transform: scale(1.1);
}

/*************** card text and arrow transition*****************/
.wrapper .container .card li div,
.wrapper .container .card li .info .cost .total {
  position: relative;
  color: var(--color-text2);
  transition: color 0.2s ease-in-out;
}
.wrapper .container .card li .header .title,
.wrapper .container .card li .header .rating .score {
  position: relative;
  color: var(--color-text);
  transition: color 0.2s ease-in-out;
}
.wrapper .container .card li .info .cost .price {
  position: relative;
  color: var(--color-text3);
  transition: color 0.2s ease-in-out;
}
.wrapper .container .card li:hover .title,
.wrapper .container .card li:hover div,
.wrapper .container .card li:hover .info .rating .score,
.wrapper .container .card li:hover .info .cost .price,
.wrapper .container .card li:hover .info .cost .total {
  background-color: transparent;
  color: white;
}
</style>
