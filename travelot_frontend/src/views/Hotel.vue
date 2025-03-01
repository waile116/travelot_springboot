<template>
  <div class="wrapper">
    <Nav></Nav>
    <div class="carousel">
      <div class="select">
        <select v-model="state" @change="listHotel">
          <option v-for="state in stateArr" :key="state.stateId" :value="state">
            {{ state.name }}
          </option>
        </select>
        <input type="date" v-model="date" />
        <div>{{ date }}</div>
      </div>
      <div class="hotel">
        <p>{{ state.name }}热门酒店</p>
        <ul class="card" ref="scrollContainer">
          <!-- <i class="fa fa-chevron-left"></i>
          <i class="fa fa-chevron-right"></i> -->
          <li v-for="hotel in hotelArr" @click="">
            <img :src="hotel.hotelImg" />
            <div class="info">
              <p class="title">{{ hotel.name }}</p>
              <div class="rating">
                评分
                <p class="score">{{ hotel.rating }}</p>
                /5
              </div>
              <div class="ticket">
                <span v-if="hotel.price !== 0">门票</span>
                <p class="price">
                  {{ hotel.price === 0 ? "免费" : `¥${hotel.price}` }}
                </p>
                <span v-if="hotel.price !== 0">起</span>
              </div>
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
  name: "Hotel",
  data() {
    return {
      date: "",
      stateArr: [],
      state: "",
      hotelArr: [],
    };
  },
  created() {
    //get state with stateId
    this.$axios
      .post("StateController/listState")
      .then((response) => {
        this.stateArr = response.data;
        this.state = this.stateArr[0]; //default value
        this.listHotel(); // list hotel right after getting state info
      })
      .catch((error) => {
        console.error(error);
      });
  },
  components: {
    Nav,
  },
  methods: {
    listHotel() {
      //get hotel list with stateId
      this.$axios
        .get(`HotelController/listHotelById/${this.state.stateId}`)
        .then((response) => {
          this.hotelArr = response.data;
        })
        .catch((error) => {
          console.error(error);
        });
    },
  },
};
</script>

<style scoped>
.wrapper {
  position: relative;
}
.wrapper .carousel {
  display: flex;
  flex-direction: column;
  margin: 1.5vw 4vw 0 4vw;
}

/*************** selection tab *****************/
.wrapper .carousel .select {
  display: flex;
}

/*************** title *****************/
.wrapper .carousel p {
  font-size: 2vw;
}

.wrapper .carousel li:last-child {
  margin-bottom: 2vw;
}

/*************** card container *****************/
.wrapper .carousel .card {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

/*************** each individual card *****************/
.wrapper .carousel .card li {
  display: flex;
  margin: 1vw 2vw 1vw 0;
  height: 18vw;
  width: 100%;
  border-radius: 1vw;
  box-shadow: 3px 3px 3px #c3c3c3;
  cursor: pointer;
  position: relative;
  z-index: 1;
  overflow: hidden;
}

.wrapper .carousel .card li .info {
  flex: 3;
  padding: 1vw 1.5vw;
  display: flex;
  flex-direction: column;
  font-size: 1vw;
  font-weight: 500;
}

.wrapper .carousel .card li .info .title {
  font-size: 1.5vw;
}

.wrapper .carousel .card li .info .rating,
.ticket {
  display: flex;
  align-items: flex-end;
}

.wrapper .carousel .card li .info .rating .score {
  font-size: 1.25vw;
}

.wrapper .carousel .card li .info .ticket .price {
  font-size: 1.5vw;
  font-weight: 700;
}

.wrapper .carousel .card li .rating .score,
.wrapper .carousel .card li .ticket .price {
  padding: 0 0.25vw;
}

/*************** hide scrollbar *****************/
.wrapper .carousel .card::-webkit-scrollbar {
  background-color: transparent;
  height: 10px;
}

/*************** card background transition*****************/
.wrapper .carousel .card li img {
  flex: 1;
  height: 100%;
  width: 12vw;
  object-fit: cover;
  border-radius: 1vw 0 0 1vw;
  transition: transform 0.3s ease-in-out;
}
.wrapper .carousel .card li::before {
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
.wrapper .carousel .card li:hover::before {
  transform: translateX(0);
}
.wrapper .carousel .card li:hover img {
  transform: scale(1.1);
}

/*************** card text and arrow transition*****************/
.wrapper .carousel .card li div {
  position: relative;
  color: var(--color-text2);
  transition: color 0.2s ease-in-out;
}
.wrapper .carousel .card li .title,
.wrapper .carousel .card li .rating .score {
  position: relative;
  color: var(--color-text);
  transition: color 0.2s ease-in-out;
}
.wrapper .carousel .card li .ticket .price {
  position: relative;
  color: var(--color-text3);
  transition: color 0.2s ease-in-out;
}
.wrapper .carousel .card li:hover .title,
.wrapper .carousel .card li:hover div,
.wrapper .carousel .card li:hover .rating .score,
.wrapper .carousel .card li:hover .ticket .price {
  background-color: transparent;
  color: white;
}
</style>
