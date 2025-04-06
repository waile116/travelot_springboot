<template>
  <div class="wrapper">
    <Nav></Nav>
    <div class="header">
      <p>{{ state.name }}</p>
      <img class="background" :src="state.stateImg" />
      <a class="map" :href="state.mapLink" target="_blank">
        <img :src="state.mapImg" />
      </a>
    </div>
    <ul class="container">
      <li class="content">
        <p>热门景点</p>
        <ul class="card" ref="c1">
          <i class="fa fa-chevron-left disabled" id="l1" ref="l1"></i>
          <i class="fa fa-chevron-right" id="r1" ref="r1"></i>
          <li
            v-for="attraction in attractionArr"
            @click="toAttractionInfo(attraction.attractionId)"
          >
            <img :src="attraction.attractionImg" />
            <p class="title">{{ attraction.name }}</p>
            <div class="rating">
              评分
              <p class="score">{{ attraction.rating }}</p>
              /5
            </div>
            <div class="ticket">
              <span v-if="attraction.price !== '0'">门票</span>
              <p class="price">
                {{ attraction.price === "0" ? "免费" : `¥${attraction.price}` }}
              </p>
              <span v-if="attraction.price !== '0'">起</span>
            </div>
          </li>
        </ul>
      </li>
      <li class="content">
        <p>热门酒店</p>
        <ul class="card" ref="c2">
          <i class="fa fa-chevron-left disabled" id="l2" ref="l2"></i>
          <i class="fa fa-chevron-right" id="r2" ref="r2"></i>
          <li v-for="hotel in hotelArr" @click="toHotelInfo(hotel.hotelId)">
            <img :src="hotel.hotelImg" />
            <p class="title">{{ hotel.name }}</p>
            <div class="rating">
              评分
              <p class="score">{{ hotel.rating }}</p>
              /5
            </div>
            <div class="ticket">
              每晚
              <p class="price">
                {{ `¥${hotel.price}` }}
              </p>
              起
            </div>
          </li>
        </ul>
      </li>
      <li class="content">
        <p>热门餐厅</p>
        <ul class="card" ref="c3">
          <i class="fa fa-chevron-left disabled" id="l3" ref="l3"></i>
          <i class="fa fa-chevron-right" id="r3" ref="r3"></i>
          <li
            v-for="restaurant in restaurantArr"
            @click="toRestaurantInfo(restaurant.restaurantId)"
          >
            <img :src="restaurant.restaurantImg" />
            <p class="title">{{ restaurant.name }}</p>
            <div class="rating">
              评分
              <p class="score">{{ restaurant.rating }}</p>
              /5
            </div>
            <div class="ticket">
              人均
              <p class="price">
                {{ `¥${restaurant.price}` }}
              </p>
            </div>
          </li>
        </ul>
      </li>
    </ul>
  </div>
</template>

<script>
import Nav from "../components/Nav.vue";
import { updateArrows } from "../common.js";

export default {
  name: "StateInfo",
  data() {
    return {
      stateId: this.$route.query.id,
      state: {},
      attractionArr: [],
      hotelArr: [],
      restaurantArr: [],
    };
  },
  created() {
    //get state with stateId
    this.$axios
      .get("StateController/getStateById/" + this.stateId)
      .then((response) => {
        this.state = response.data.result;
        console.log(this.state);
      })
      .catch((error) => {
        console.error(error);
      });

    //get attraction list with stateId
    this.$axios
      .get(`AttractionController/listAttractionById/${this.stateId}`)
      .then((response) => {
        this.attractionArr = response.data.result;
      })
      .catch((error) => {
        console.error(error);
      });

    //get hotel list with stateId
    this.$axios
      .get(`HotelController/listHotelById/${this.stateId}`)
      .then((response) => {
        this.hotelArr = response.data.result;
      })
      .catch((error) => {
        console.error(error);
      });

    //get restaurant list with stateId
    this.$axios
      .get(`RestaurantController/listRestaurantById/${this.stateId}`)
      .then((response) => {
        this.restaurantArr = response.data.result;
      })
      .catch((error) => {
        console.error(error);
      });
  },
  mounted() {
    //assign container and arrows into array
    this.container = [this.$refs.c1, this.$refs.c2, this.$refs.c3];

    this.leftArrow = [this.$refs.l1, this.$refs.l2, this.$refs.l3];

    this.rightArrow = [this.$refs.r1, this.$refs.r2, this.$refs.r3];

    // add event listener for each arrows, and update everytime
    this.leftArrow.forEach((l, index) => {
      l.addEventListener("click", () => {
        this.container[index].scrollBy({
          left: -this.container[index].clientWidth * 0.4,
          behavior: "smooth",
        });
        setTimeout(() => {
          this.container[index].addEventListener(
            "scroll",
            updateArrows(this.container[index], l, this.rightArrow[index])
          );
        }, 600);
      });
    });

    this.rightArrow.forEach((r, index) => {
      r.addEventListener("click", () => {
        this.container[index].scrollBy({
          left: this.container[index].clientWidth * 0.4,
          behavior: "smooth",
        });
        setTimeout(() => {
          this.container[index].addEventListener(
            "scroll",
            updateArrows(this.container[index], this.leftArrow[index], r)
          );
        }, 600);
      });
    });
  },
  components: {
    Nav,
  },
  methods: {
    toAttractionInfo(id) {
      this.$router.push({ path: "/attractionInfo", query: { id: id } });
    },
    toHotelInfo(id) {
      this.$router.push({ path: "/hotelInfo", query: { id: id } });
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

/***************header container*****************/
.wrapper .header {
  position: relative;
  display: flex;
  margin: 0 4vw 2vw;
  height: 25vw;
  border-radius: 0 0 1vw 1vw;
  overflow: hidden;
}
.wrapper .header p {
  position: absolute;
  font-size: 2vw;
  color: white;
  text-shadow: 3px 0px 3px #000000;
  z-index: 4;
  bottom: 0;
  margin: 1vw 2vw;
}
.wrapper .header .background {
  flex: 7;
  object-fit: cover;
  z-index: 3;
}
.wrapper .header .map {
  flex: 3;
  cursor: pointer;
}
.wrapper .header .map img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease-in-out;
}
.wrapper .header .map img:hover {
  transform: scale(1.1);
}

/***************carousel container*****************/
.wrapper .container {
  display: flex;
  flex-direction: column;
  margin: 1.5vw 4vw 0;
}

/*************** carousel arrow *****************/
.wrapper .container .fa-chevron-right,
.wrapper .container .fa-chevron-left {
  position: absolute;
  top: 16%;
  padding: 0.8vw 1vw;
  font-size: 2vw;
  color: var(--color-text);
  background-color: white;
  box-shadow: 1px 2px 4px #c3c3c3;
  z-index: 2;
  border-radius: 10vw;
  cursor: pointer;
  transition: color 0.2s ease-in-out;
}
.wrapper .container .fa-chevron-left {
  left: 0;
  margin-left: 2vw;
}
.wrapper .container .fa-chevron-right {
  right: 0;
  margin-right: 2vw;
}
.wrapper .container .fa-chevron-right:hover:not(.disabled), /* not(.disabled) means only apply when no disabled*/
.wrapper .container .fa-chevron-left:hover:not(.disabled) {
  color: white;
  background-color: var(--color-blue1);
}
.wrapper .container .fa-chevron-right.disabled,
.wrapper .container .fa-chevron-left.disabled {
  cursor: not-allowed;
}
.wrapper .container #l1,
#r1 {
  top: 39%;
}
.wrapper .container #l2,
#r2 {
  top: 62%;
}
.wrapper .container #l3,
#r3 {
  top: 85%;
}

/*************** title *****************/
.wrapper .container p {
  font-size: 2vw;
}

.wrapper .container li:last-child {
  margin-bottom: 2vw;
}

/*************** card container *****************/
.wrapper .container .card {
  display: flex;
  justify-content: flex-start;
  overflow-x: scroll; /*make overflow scrollable*/
  scroll-behavior: smooth;
  scroll-snap-type: x mandatory; /*automatically move to snap point*/
}

/*************** each individual card *****************/
.wrapper .container .card li {
  scroll-snap-align: start; /*snap at start of elements*/
  flex: 0 0 auto; /*grow/shrink/basis */
  margin: 1vw 2vw 1vw 0;
  padding-bottom: 1vw;
  height: 20vw;
  width: 21vw;
  border-radius: 1vw;
  box-shadow: 3px 3px 3px #c3c3c3;
  cursor: pointer;
  position: relative;
  z-index: 1;
  overflow: hidden;
}

.wrapper .container .card li div {
  display: flex;
  align-items: flex-end;
  padding: 0.5vw 1.5vw 0;
  font-size: 1vw;
  line-height: 0.8;
  font-weight: 500;
}

.wrapper .container .card li .title {
  padding: 0.5vw 1.5vw 0;
  font-size: 1.5vw;
}

.wrapper .container .card li .ticket {
  position: absolute;
  top: auto;
  right: 0;
  bottom: 0;
  padding: 1vw 1.5vw;
}

.wrapper .container .card li .rating .score {
  font-size: 1.25vw;
}

.wrapper .container .card li .ticket .price {
  font-size: 1.5vw;
  font-weight: 700;
}

.wrapper .container .card li .rating .score,
.wrapper .container .card li .ticket .price {
  padding: 0 0.25vw;
}

/*************** hide scrollbar *****************/
.wrapper .container .card::-webkit-scrollbar {
  background-color: transparent;
  height: 10px;
}

/*************** card background transition*****************/
.wrapper .container .card li img {
  height: 60%;
  width: 100%;
  object-fit: cover;
  border-radius: 1vw 1vw 0 0;
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
.wrapper .container .card li div {
  position: relative;
  color: var(--color-text2);
  transition: color 0.2s ease-in-out;
}
.wrapper .container .card li .title,
.wrapper .container .card li .rating .score {
  position: relative;
  color: var(--color-text);
  transition: color 0.2s ease-in-out;
}
.wrapper .container .card li .ticket .price {
  position: relative;
  color: var(--color-text3);
  transition: color 0.2s ease-in-out;
}
.wrapper .container .card li:hover .title,
.wrapper .container .card li:hover div,
.wrapper .container .card li:hover .rating .score,
.wrapper .container .card li:hover .ticket .price {
  background-color: transparent;
  color: white;
}
</style>
