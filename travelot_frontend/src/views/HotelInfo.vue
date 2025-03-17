<template>
  <div class="wrapper">
    <Nav></Nav>
    <div class="carousel">
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
        <label>
          <p>退房日期</p>
          <input
            type="date"
            v-model="endDate"
            :min="minEndDate"
            @change="calNight"
          />
        </label>
        <p>共 {{ night }} 晚</p>
      </div>
      <div class="room">
        <p>{{ hotel.name }}热门酒店</p>
        <ul class="card">
          <li v-for="room in roomArr" @click="">
            <img :src="room.roomImg" />
            <div class="info">
              <div class="header">
                <p class="title">{{ room.name }}</p>
                <div class="rating">
                  <p>评分</p>
                  <p class="score">{{ room.rating }}</p>
                  <p>/5</p>
                </div>
                <div class="location">
                  <i class="fa fa-map-marker"></i>
                  <p>{{ room.location }}</p>
                </div>
              </div>
              <div class="cost">
                <p class="price">{{ `¥${room.price}` }}</p>
                <p class="total">总额：{{ `¥${room.price * night}` }}</p>
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
  },
};
</script>

<style scoped></style>
