<template>
  <div class="wrapper">
    <div class="nav">
      <div class="left">
        <p v-if="isLogin" @click="toBack">
          <i class="fa fa-chevron-left"></i>返回
        </p>
      </div>
    </div>
    <div class="container">
      <div class="date">
        <label
          ><p>入住日期</p>
          <input
            type="date"
            v-model="startDate"
            :min="minStartDate"
            @change="calNight"
          />
        </label>
        <p>共 {{ night }} 晚</p>
        <label>
          <p>退房日期</p>
          <input
            type="date"
            v-model="endDate"
            :min="minEndDate"
            @change="calNight"
          />
        </label>
      </div>
      <div class="room">
        <div class="img">
          <p class="title">{{ hotel.name }}</p>
          <img :src="hotel.hotelImg" />
          <div class="map">this is map</div>
        </div>
        <div class="location">
          <i class="fa fa-map-marker"></i>
          <p>{{ hotel.location }}</p>
        </div>
        <p class="description">{{ hotel.desc }}</p>
        <ul class="card">
          <li v-for="room in roomArr" @click="">
            <img :src="room.roomImg" />
            <div class="info">
              <div class="header">
                <p class="title">{{ room.name }}</p>
              </div>
              <div class="cost">
                <p class="price">{{ `¥${room.price}` }}</p>
                <p class="total">总额：{{ `¥${room.price * night}` }}</p>
              </div>
              <div class="reserve">
                <button @click="">预 定</button>
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
  name: "HotelInfo",
  data() {
    return {
      isLogin: false,
      hotelId: this.$route.query.id,
      hotel: "",
      minStartDate: this.getCurDate(),
      startDate: this.getCurDate(),
      endDate: this.getNextDate(),
      minEndDate: this.getNextDate(),
      night: 1,
      roomArr: [],
    };
  },
  created() {
    this.user = this.$getSessionStorage("user");
    // check if login
    if (this.user != null) {
      this.isLogin = true;
    }

    //get hotel info with hotelId
    this.$axios
      .get(`HotelController/getHotelById/${this.hotelId}`)
      .then((response) => {
        this.hotel = response.data.result;
      })
      .catch((error) => {
        console.error(error);
      });

    // get room list with hotelId
    this.$axios
      .get(`HotelController/listRoomById/${this.hotelId}`)
      .then((response) => {
        this.roomArr = response.data.result;
      })
      .catch((error) => {
        console.error(error);
      });
  },
  components: {
    Nav,
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

    toBack() {
      this.$router.go(-1);
    },
  },
};
</script>

<style scoped>
.wrapper {
  position: relative;
}

/*************** nav bar *****************/
.wrapper .nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 4vw;
  background-color: var(--color-blue1);
  height: 6vw;
}

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

/*************** container *****************/
.wrapper .container {
  display: flex;
  flex-direction: column;
  margin: 1.5vw 4vw 0 4vw;
}

/*************** selection tab *****************/
.wrapper .container .date {
  padding: 1vw 3vw;
  margin: 0 20vw 1vw;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 4vw;
  border-radius: 1vw;
  box-shadow: 3px 3px 3px #c3c3c3;
  outline: none;
}

.wrapper .container .date label {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.wrapper .container .date p {
  font-size: 1.2vw;
  margin-bottom: 0.5vw;
}

.wrapper .container .date select,
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

/*************** title, location and desc *****************/
.wrapper .container .room .location {
  display: flex;
  align-items: center;
  margin-bottom: 1vw;
}
.wrapper .container .room .location i {
  font-size: 1.5vw;
  margin-right: 0.8vw;
}
.wrapper .container .room .location p {
  font-size: 1vw;
  font-weight: normal;
}
.wrapper .container .room .description {
  font-size: 1vw;
  margin-bottom: 1vw;
  font-weight: normal;
}
.wrapper .container .room .img {
  margin-bottom: 2vw;
  position: relative;
  display: flex;
  height: 25vw;
  border-radius: 1vw;
  overflow: hidden;
}
.wrapper .container .room .img p {
  position: absolute;
  font-size: 2vw;
  color: white;
  z-index: 4;
  bottom: 0;
  margin: 1vw 2vw;
}
.wrapper .container .room .img img {
  flex: 7;
  object-fit: cover;
}

.wrapper .container .room .img .map {
  flex: 3;
  background-color: red;
}

/*************** card container *****************/
.wrapper .container .room .card {
  display: flex;
  justify-content: flex-start;
  flex-wrap: wrap;
}

.wrapper .container .room .card:last-child {
  margin-bottom: 2vw;
}

/*************** each individual card *****************/
.wrapper .container .room .card li {
  flex: 0 0 auto; /*grow/shrink/basis */
  margin: 1vw 2vw 1vw 0;
  padding-bottom: 1vw;
  height: 25vw;
  width: 21vw;
  border-radius: 1vw;
  box-shadow: 3px 3px 3px #c3c3c3;
  position: relative;
  z-index: 1;
  overflow: hidden;
}
.wrapper .container .room .card li .info .title {
  padding: 1vw 1.5vw 0;
  margin-bottom: 0;
  font-size: 1.5vw;
  color: var(--color-text);
}
.wrapper .container .room .card li .info .cost {
  justify-self: flex-end;
  padding: 0.5vw 1.5vw;
}
.wrapper .container .room .card li .info .cost .price {
  font-size: 1.5vw;
  font-weight: 700;
  text-align: end;
  color: var(--color-text3);
}
.wrapper .container .room .card li .info .cost .total {
  font-size: 1vw;
  font-weight: normal;
  color: var(--color-text2);
}
.wrapper .container .room .card li .info .reserve {
  padding: 0 1.5vw 1vw;
  position: absolute;
  top: auto;
  right: 0;
  bottom: 0;
}
.wrapper .container .room .card li .info .reserve button {
  width: 6vw;
  height: 2vw;
  background-color: var(--color-orange);
  border: none;
  outline: none;
  border-radius: 0.5vw;

  font-size: 1vw;
  font-weight: bold;
  color: white;

  cursor: pointer;
  position: relative;
  overflow: hidden;
  z-index: 1;
}

/*************** card background transition*****************/
.wrapper .container .room .card li img {
  height: 60%;
  width: 100%;
  object-fit: cover;
  border-radius: 1vw 1vw 0 0;
  transition: transform 0.3s ease-in-out;
}

.wrapper .container .room .card li:hover img {
  transform: scale(1.1);
}

/*************** button transition *****************/
.wrapper .container .room .card li .info .reserve button::before {
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

.wrapper .container .room .card li .info .reserve button:hover::before {
  transform: translateX(0);
}
</style>
