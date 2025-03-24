<template>
  <div class="wrapper">
    <Nav></Nav>
    <ul class="container">
      <li class="state">
        <p>马来西亚州属</p>
        <ul class="card" ref="c1">
          <i class="fa fa-chevron-left disabled" id="l1" ref="l1"></i>
          <i class="fa fa-chevron-right" id="r1" ref="r1"></i>
          <li v-for="state in stateArr" @click="toStateInfo(state.stateId)">
            <img :src="state.stateImg" />
            <p class="title">{{ state.name }}</p>
            <p class="desc">{{ state.desc }}</p>
            <i class="fa fa-chevron-right"></i>
          </li>
        </ul>
      </li>
      <li class="content">
        <p>热门景点</p>
        <ul class="card" ref="c2">
          <i class="fa fa-chevron-left disabled" id="l2" ref="l2"></i>
          <i class="fa fa-chevron-right" id="r2" ref="r2"></i>
          <li v-for="attraction in attractionArr" @click="">
            <img :src="attraction.attractionImg" />
            <p class="title">{{ attraction.name }}</p>
            <p class="statename">{{ stateArr[attraction.stateId - 1].name }}</p>
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
        <ul class="card" ref="c3">
          <i class="fa fa-chevron-left disabled" id="l3" ref="l3"></i>
          <i class="fa fa-chevron-right" id="r3" ref="r3"></i>
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
        <ul class="card" ref="c4">
          <i class="fa fa-chevron-left disabled" id="l4" ref="l4"></i>
          <i class="fa fa-chevron-right" id="r4" ref="r4"></i>
          <li v-for="restaurant in restaurantArr" @click="">
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
  name: "Index",
  data() {
    return {
      container: [],
      leftArrow: [],
      rightArrow: [],
      stateArr: [],
      attractionArr: [],
      hotelArr: [],
      restaurantArr: [],
    };
  },
  created() {
    this.$axios
      .get("StateController/listState")
      .then((response) => {
        this.stateArr = response.data.result;
      })
      .catch((error) => {
        console.error(error);
      });
    this.$axios
      .get("AttractionController/listAttractionRandom")
      .then((response) => {
        this.attractionArr = response.data.result;
      })
      .catch((error) => {
        console.error(error);
      });
    this.$axios
      .get("HotelController/listHotelRandom")
      .then((response) => {
        this.hotelArr = response.data.result;
      })
      .catch((error) => {
        console.error(error);
      });
    this.$axios
      .get("RestaurantController/listRestaurantRandom")
      .then((response) => {
        this.restaurantArr = response.data.result;
      })
      .catch((error) => {
        console.error(error);
      });
  },
  mounted() {
    //assign container and arrows into array
    this.container = [
      this.$refs.c1,
      this.$refs.c2,
      this.$refs.c3,
      this.$refs.c4,
    ];

    this.leftArrow = [
      this.$refs.l1,
      this.$refs.l2,
      this.$refs.l3,
      this.$refs.l4,
    ];

    this.rightArrow = [
      this.$refs.r1,
      this.$refs.r2,
      this.$refs.r3,
      this.$refs.r4,
    ];

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
    toStateInfo(id) {
      this.$router.push({ path: "/stateInfo", query: { id: id } });
    },
    toHotelInfo(id) {
      this.$router.push({ path: "/hotelInfo", query: { id: id } });
    },
  },
};
</script>

<style scoped>
.wrapper {
  position: relative;
}

/*************** carousel container *****************/
.wrapper .container {
  display: flex;
  flex-direction: column;
  margin: 1.5vw 4vw 0;
}

/*************** carousel arrow *****************/
.wrapper .container .fa-chevron-right,
.wrapper .container .fa-chevron-left {
  position: absolute;
  padding: 1vw;
  font-size: 2vw;
  width: 2vw;
  height: 2vw;
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
.wrapper .container #l1,
#r1 {
  top: 18%;
}
.wrapper .container #l2,
#r2 {
  top: 42%;
}
.wrapper .container #l3,
#r3 {
  top: 64%;
}
.wrapper .container #l4,
#r4 {
  top: 86%;
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

/*************** title *****************/
.wrapper .container p {
  font-size: 2vw;
}

.wrapper .container li:last-child {
  margin-bottom: 2vw;
}

/*************** STATE CARD *****************/
.wrapper .container .card {
  display: flex;
  justify-content: flex-start;
  overflow-x: scroll; /*make overflow scrollable*/
  scroll-behavior: smooth;
  scroll-snap-type: x mandatory; /*automatically move to snap point*/
}

.wrapper .container .card li {
  scroll-snap-align: start; /*snap at start of elements*/
  flex: 0 0 auto; /*grow/shrink/basis */
  margin: 1vw;
  padding-bottom: 1vw;
  height: 25vw;
  width: 21vw;
  border-radius: 1vw;
  box-shadow: 3px 3px 3px #c3c3c3;
  cursor: pointer;
  position: relative;
  z-index: 1;
  overflow: hidden;
}
.wrapper .container .card li p {
  padding: 1vw 1.5vw 0;
}
.wrapper .container .card li .title {
  font-size: 1.5vw;
}
.wrapper .container .card li .desc {
  font-size: 1vw;
  font-weight: 500;
}

/*************** CONTENT CARD *****************/
.wrapper .container .content .card div {
  display: flex;
  align-items: flex-end;
  padding: 0.5vw 1.5vw 0;
  font-size: 1vw;
  line-height: 0.8;
  font-weight: 500;
}
.wrapper .container .content .card li {
  height: 20vw;
}
.wrapper .container .content .card .statename {
  font-size: 1vw;
  padding: 0.5vw 1.5vw 0;
}
.wrapper .container .content .card li .ticket {
  position: absolute;
  top: auto;
  right: 0;
  bottom: 0;
  padding: 1vw 1.5vw;
}

.wrapper .container .content .card li .rating .score {
  font-size: 1.25vw;
}

.wrapper .container .content .card li .ticket .price {
  font-size: 1.5vw;
  font-weight: 700;
}

.wrapper .container .content .card li .rating .score,
.wrapper .container .content .card li .ticket .price {
  padding: 0 0.25vw;
}

/*************** hide scrollbar *****************/
.wrapper .container .card::-webkit-scrollbar {
  background-color: transparent;
  height: 10px;
}

/*************** card background transition *****************/
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
.wrapper .container .content .card li div {
  position: relative;
  color: var(--color-text2);
  transition: color 0.2s ease-in-out;
}
.wrapper .container .card li .title,
.wrapper .container .card li .desc,
.wrapper .container .card li .statename,
.wrapper .container .card li .rating .score {
  position: relative;
  color: var(--color-text);
  transition: color 0.2s ease-in-out;
}
.wrapper .container .content .card li .ticket .price {
  position: relative;
  color: var(--color-text3);
  transition: color 0.2s ease-in-out;
}
.wrapper .container .card li .fa-chevron-right {
  position: absolute;
  top: auto;
  bottom: 0;
  right: 0;
  font-size: 1.25vw;
  padding: 1vw 1.5vw;
  color: var(--color-text);
  background-color: transparent;
  box-shadow: 0 0 0;
  margin-right: 0;
  transition: color 0.2s ease-in-out;
}
.wrapper .container .card li:hover .title,
.wrapper .container .card li:hover .desc,
.wrapper .container .card li:hover .statename,
.wrapper .container .card li:hover .fa-chevron-right,
.wrapper .container .card li:hover div,
.wrapper .container .card li:hover .rating .score,
.wrapper .container .card li:hover .ticket .price {
  background-color: transparent;
  color: white;
}
</style>
