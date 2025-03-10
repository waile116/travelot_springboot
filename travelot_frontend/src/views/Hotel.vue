<template>
  <div class="wrapper">
    <Nav></Nav>
    <div class="carousel">
      <ul class="select">
        <li class="state">
          <label
            ><p>州属</p>
            <select v-model="state" @change="listHotel">
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
        <li class="date">
          <label
            ><p>入住日期</p>
            <input
              type="date"
              v-model="startDate"
              value="startDate"
              @change="calNight"
            />
          </label>
          <p>共 {{ night }} 晚</p>
          <label>
            <p>退房日期</p>
            <input
              type="date"
              v-model="endDate"
              :min="startDate"
              @change="calNight"
            />
          </label>
        </li>
      </ul>
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
      startDate: this.getCurDate(),
      endDate: this.getNextDate(),
      night: 1,
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
    //get hotel list with stateId
    listHotel() {
      this.$axios
        .get(`HotelController/listHotelById/${this.state.stateId}`)
        .then((response) => {
          this.hotelArr = response.data;
        })
        .catch((error) => {
          console.error(error);
        });
    },

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
  watch: {
    // auto update end date when start date change
    startDate(newVal) {
      const tmr = new Date(newVal);
      tmr.setDate(tmr.getDate() + 1);
      console.log("End date updated");
      this.endDate = tmr.toISOString().split("T")[0];
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
  margin-bottom: 1vw;
}

.wrapper .carousel .select li {
  display: flex;
  justify-content: space-between;
  border: solid 1px black;
  padding: 1vw 1.5vw;
  height: 100%;
  border-radius: 0.5vw;
  border: 1px solid #c3c3c3;
  outline: none;
}

.wrapper .carousel .select li label {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.wrapper .carousel .select p {
  font-size: 1.2vw;
  margin-right: 1vw;
}

.wrapper .carousel .select .state {
  margin-right: 5vw;
}

.wrapper .carousel .select select,
input {
  font-size: 1.2vw;
  border: none;
  outline: none;
  color: var(--color-text);
  cursor: pointer;
}

select {
  appearance: none;
  -webkit-appearance: none;
  width: 8vw;
}

input[type="date"]::-webkit-calendar-picker-indicator {
  opacity: 0;
  position: absolute;
  cursor: pointer;
  width: 8vw;
}

/*************** title *****************/
.wrapper .carousel p {
  font-size: 2vw;
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
  height: 16vw;
  width: 100%;
  border-radius: 1vw;
  box-shadow: 3px 3px 3px #c3c3c3;
  cursor: pointer;
  position: relative;
  z-index: 1;
  overflow: hidden;
}

.wrapper .carousel .card li:last-child {
  margin-bottom: 2vw;
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
