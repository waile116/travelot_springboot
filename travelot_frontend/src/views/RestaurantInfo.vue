<template>
  <div class="wrapper">
    <Nav2></Nav2>
    <div class="img">
      <p class="title">{{ restaurant.name }}</p>
      <img class="background" :src="restaurant.restaurantImg" />
      <a class="map" :href="restaurant.mapLink" target="_blank">
        <img :src="restaurant.mapImg" />
      </a>
    </div>
    <div class="container">
      <div class="restaurant">
        <p class="description">{{ restaurant.desc }}</p>
        <div class="info">
          <i class="fa fa-map-marker"></i>
          <p>{{ restaurant.location }}</p>
        </div>
        <div class="info">
          <i class="fa fa-clock-o"></i>
          <p>{{ restaurant.openTime }}</p>
        </div>
        <ul class="card">
          <li v-for="food in foodArr">
            <img :src="food.foodImg" />
            <div class="detail">
              <div class="header">
                <p class="title">{{ food.name }}</p>
              </div>
              <div class="cost">
                <p class="price">{{ `¥${food.price}` }}</p>
              </div>
            </div>
          </li>
        </ul>
      </div>
      <Comment category="2" :target_id="restaurantId"></Comment>
    </div>
  </div>
</template>

<script>
import Nav2 from "../components/Nav2.vue";
import Comment from "../components/Comment.vue";

export default {
  name: "RestaurantInfo",
  data() {
    return {
      isLogin: false,
      restaurantId: this.$route.query.id,
      restaurant: "",
      foodArr: [],
    };
  },
  created() {
    this.user = this.$getSessionStorage("user");
    // check if login
    if (this.user != null) {
      this.isLogin = true;
    }

    //get restaurant info with restaurantId
    this.$axios
      .get(`RestaurantController/getRestaurantById/${this.restaurantId}`)
      .then((response) => {
        this.restaurant = response.data.result;
      })
      .catch((error) => {
        console.error(error);
      });

    // get food list with restaurantId
    this.$axios
      .get(`RestaurantController/listFoodById/${this.restaurantId}`)
      .then((response) => {
        this.foodArr = response.data.result;
      })
      .catch((error) => {
        console.error(error);
      });
  },
  components: {
    Nav2,
    Comment,
  },
  methods: {
    getCurDate() {
      const tdy = new Date();
      return tdy.toISOString().split("T")[0]; // Format YYYY-MM-DD
    },

    getNextDate() {
      const tmr = new Date();
      tmr.setDate(tmr.getDate() + 1);
      return tmr.toISOString().split("T")[0]; // Format YYYY-MM-DD
    },

    calNight() {
      if (this.startDate && this.endDate) {
        const start = new Date(this.startDate);
        const end = new Date(this.endDate);
        const difference = (end - start) / (1000 * 60 * 60 * 24); // convert milliseconds to days
        this.night = difference > 0 ? difference : 0;
      }
    },
  },
};
</script>

<style scoped>
.wrapper {
  position: relative;
}

/*************** header container *****************/
.wrapper .img {
  margin: 0 4vw 2vw;
  position: relative;
  display: flex;
  height: 25vw;
  border-radius: 0 0 1vw 1vw;
  overflow: hidden;
}
.wrapper .img p {
  position: absolute;
  font-size: 2vw;
  color: white;
  text-shadow: 3px 0px 3px #000000;
  z-index: 4;
  bottom: 0;
  margin: 1vw 2vw;
}
.wrapper .img .background {
  flex: 7;
  object-fit: cover;
  z-index: 3;
}
.wrapper .img .map {
  flex: 3;
  cursor: pointer;
}
.wrapper .img .map img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease-in-out;
}
.wrapper .img .map img:hover {
  transform: scale(1.1);
}

/*************** container *****************/
.wrapper .container {
  display: flex;
  flex-direction: column;
  margin: 1.5vw 4vw 0 4vw;
}

/*************** title, info and desc *****************/
.wrapper .container .restaurant .info {
  display: flex;
  align-items: center;
  margin-bottom: 1vw;
}
.wrapper .container .restaurant .info i {
  font-size: 1.5vw;
  margin-right: 0.8vw;
}
.wrapper .container .restaurant .info p {
  font-size: 1vw;
  font-weight: normal;
}
.wrapper .container .restaurant .description {
  font-size: 1vw;
  margin-bottom: 1vw;
  font-weight: normal;
}

/*************** card container *****************/
.wrapper .container .restaurant .card {
  display: flex;
  justify-content: flex-start;
}

.wrapper .container .restaurant .card:last-child {
  margin-bottom: 2vw;
}

/*************** each individual card *****************/
.wrapper .container .restaurant .card li {
  flex: 0 0 auto; /*grow/shrink/basis */
  margin: 1vw 2vw 1vw 0;
  box-sizing: border-box;
  width: 21vw;
  height: 20vw;
  border-radius: 1vw;
  box-shadow: 3px 3px 3px #c3c3c3;
  position: relative;
  z-index: 1;
  overflow: hidden;
}
.wrapper .container .restaurant .card li .detail {
  padding: 1vw 1.5vw;
  box-sizing: border-box;
}
.wrapper .container .restaurant .card li .detail .header .title {
  margin-bottom: 0;
  font-size: 1.5vw;
  color: var(--color-text);
}
.wrapper .container .restaurant .card li .detail .header .desc {
  font-size: 1vw;
  margin-top: 0.5vw;
  font-weight: normal;
}
.wrapper .container .restaurant .card li .detail .cost {
  justify-self: flex-end;
  padding: 0.5vw 0;
}
.wrapper .container .restaurant .card li .detail .cost .price {
  font-size: 1.5vw;
  font-weight: 700;
  text-align: end;
  color: var(--color-text3);
}
.wrapper .container .restaurant .card li .detail .cost .total {
  font-size: 1vw;
  font-weight: normal;
  color: var(--color-text2);
}

/*************** card background transition*****************/
.wrapper .container .restaurant .card li img {
  height: 65%;
  width: 100%;
  object-fit: cover;
  border-radius: 1vw 1vw 0 0;
  transition: transform 0.3s ease-in-out;
}

.wrapper .container .restaurant .card li:hover img {
  transform: scale(1.1);
}
</style>
