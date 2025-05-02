<template>
  <div class="wrapper">
    <Nav2></Nav2>
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
      <div class="hotel">
        <div class="img">
          <p class="title">{{ hotel.name }}</p>
          <img class="background" :src="hotel.hotelImg" />
          <a class="map" :href="hotel.mapLink" target="_blank">
            <img :src="hotel.mapImg" />
          </a>
        </div>
        <div class="favourite">
          <!--array of v-bind class-->
          <i
            class="fa"
            :class="[getHeartClass, animateHeart ? 'animate-heart' : '']"
            @click="setFav"
          ></i>
        </div>
        <p class="description">{{ hotel.desc }}</p>
        <div class="location">
          <i class="fa fa-map-marker"></i>
          <p>{{ hotel.location }}</p>
        </div>
        <ul class="card">
          <li v-for="room in roomArr">
            <img :src="room.roomImg" />
            <div class="detail">
              <div class="header">
                <p class="title">{{ room.name }}</p>
                <p class="type">{{ room.type }}</p>
              </div>
              <div class="cost">
                <p class="price">{{ `¥${room.price}` }}</p>
                <p class="total">总额：{{ `¥${room.price * night}` }}</p>
              </div>
              <div class="reserve">
                <button @click="openOrder(room)">预 订</button>
              </div>
            </div>
          </li>
        </ul>
      </div>
      <!--parsing category to comment component-->
      <Comment category="3" :target_id="hotelId"></Comment>
    </div>

    <div v-if="showOrder" class="order-box">
      <div class="order-content">
        <div class="header">
          <div></div>
          <p>{{ hotel.name }}</p>
          <i class="fa fa-close" @click="closeOrder"></i>
        </div>
        <div class="order-date">
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
        <div class="order-info">
          <div class="order-info-header">
            <p>{{ orderRoom.name }}</p>
            <div class="right">
              <p class="price">
                ¥{{ orderRoom.price * night * orderQuantity }}
              </p>
              <div class="order-info-quantity">
                <i
                  v-if="orderQuantity > 1"
                  class="fa fa-minus"
                  @click="orderQuantity--"
                ></i>
                <p>{{ orderQuantity }}</p>
                <i class="fa fa-plus" @click="orderQuantity++"></i>
              </div>
            </div>
          </div>
          <div class="order-desc">
            <i class="fa fa-hotel"></i>
            <p>{{ orderRoom.type }}</p>
          </div>
          <div class="order-reminder">
            <i class="fa fa-info"></i>
            <p class="t1">不可退款</p>
            <p>立即出票</p>
          </div>
        </div>
        <div class="order" @click="toPayment"><button>预 订</button></div>
      </div>
    </div>
  </div>
</template>

<script>
import Nav2 from "../components/Nav2.vue";
import Comment from "../components/Comment.vue";

export default {
  name: "HotelInfo",
  data() {
    return {
      isLogin: false,
      user: "",

      hotelId: this.$route.query.id,
      hotel: "",
      minStartDate: this.getCurDate(),
      startDate: this.$route.query.startDate ?? this.getCurDate(),
      endDate: this.$route.query.endDate ?? this.getNextDate(),
      minEndDate: this.getNextDate(),
      night: 1,
      roomArr: [],

      userFav: false,
      animateHeart: false,

      showOrder: false,
      orderRoom: "", // selected room object
      orderQuantity: 1,
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

    // get favourite with user, category and target id
    this.$axios
      .get(
        `FavouriteController/getFavouriteById/${this.user.userId}/3/${this.hotelId}`
      )
      .then((response) => {
        if (response.data.result) {
          this.userFav = true;
        }
      })
      .catch((error) => {
        console.error(error);
      });

    this.calNight(); // calculate night when page is loaded because of this.$route.query
  },
  components: {
    Nav2,
    Comment,
  },
  computed: {
    getHeartClass() {
      if (this.userFav) {
        return "fa-heart";
      } else {
        return "fa-heart-o";
      }
    },
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

    setFav() {
      this.userFav = !this.userFav; // save favourite when clicked
      this.animateHeart = true;

      // remove the animation class after it ends
      setTimeout(() => {
        this.animateHeart = false;
      }, 1000); // set animation duration

      if (this.userFav) {
        this.$axios
          .post(
            `FavouriteController/saveFavourite/${this.user.userId}/3/${this.hotelId}`
          )
          .then((response) => {
            console.log(response.data.message);
            alert("收藏成功");
          })
          .catch((error) => {
            console.error(error);
          });
      } else {
        this.$axios
          .post(
            `FavouriteController/removeFavourite/${this.user.userId}/3/${this.hotelId}`
          )
          .then((response) => {
            console.log(response.data.message);
          })
          .catch((error) => {
            console.error(error);
          });
      }
    },

    openOrder(room) {
      this.showOrder = true;
      this.orderRoom = room; // parse room to order
    },
    closeOrder() {
      this.showOrder = false;
      this.orderQuantity = 1; // reset room quantity
    },

    toPayment() {
      // create and save order
      this.$axios
        .post(
          `OrdersController/saveOrders/${this.user.userId}/3/${this.hotelId}/${
            this.orderRoom.roomId
          }/${this.orderRoom.price * this.night}/${this.orderQuantity}/${
            this.startDate
          }/${this.endDate}`
        )
        .then((response) => {
          const orderId = response.data.result;
          // go to payment page after creating order
          if (orderId > 0) {
            console.log(response.data.message);
            this.$router.push({ path: "/payment", query: { id: orderId } });
          } else {
            alert("订单创建失败");
          }
        })
        .catch((error) => {
          console.error(error);
        });
    },
  },
  watch: {
    // auto update end date when start date change
    startDate(newVal) {
      const tmr = new Date(newVal);
      tmr.setDate(tmr.getDate() + 1);
      this.endDate = tmr.toISOString().split("T")[0];
      this.minEndDate = tmr.toISOString().split("T")[0];

      console.log("endDate changed");
    },
  },
};
</script>

<style scoped>
.wrapper {
  position: relative;
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
  height: 3vw;
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
.wrapper .container .date input {
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
.wrapper .container .hotel .location {
  display: flex;
  align-items: center;
  margin-bottom: 1vw;
}
.wrapper .container .hotel .location i {
  font-size: 1.5vw;
  margin-right: 0.8vw;
}
.wrapper .container .hotel .location p {
  font-size: 1vw;
  font-weight: normal;
}
.wrapper .container .hotel .favourite {
  display: flex;
  justify-content: flex-end;
  margin: 0 3vw 1vw;
}
.wrapper .container .hotel .favourite i {
  font-size: 2vw;
  cursor: pointer;
  color: #e74c3c;
  transition: transform 0.2s ease;
}
.animate-heart {
  animation: pop 0.5s ease;
}
@keyframes pop {
  0% {
    transform: scale(1);
  }
  30% {
    transform: scale(1.4);
  }
  60% {
    transform: scale(0.9);
  }
  100% {
    transform: scale(1);
  }
}
.wrapper .container .hotel .description {
  font-size: 1vw;
  margin-bottom: 1vw;
  font-weight: normal;
}
.wrapper .container .hotel .img {
  margin-bottom: 2vw;
  position: relative;
  display: flex;
  height: 25vw;
  border-radius: 1vw;
  overflow: hidden;
}
.wrapper .container .hotel .img p {
  position: absolute;
  font-size: 2vw;
  color: white;
  text-shadow: 3px 0px 3px #000000;
  z-index: 4;
  bottom: 0;
  margin: 1vw 2vw;
}
.wrapper .container .hotel .img .background {
  flex: 7;
  object-fit: cover;
  z-index: 3;
}
.wrapper .container .hotel .img .map {
  flex: 3;
  cursor: pointer;
}
.wrapper .container .hotel .img .map img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease-in-out;
}
.wrapper .container .hotel .img .map img:hover {
  transform: scale(1.1);
}

/*************** card container *****************/
.wrapper .container .hotel .card {
  display: flex;
  justify-content: flex-start;
  flex-wrap: wrap;
  margin-bottom: 1vw;
}

.wrapper .container .hotel .card:last-child {
  margin-bottom: 2vw;
}

/*************** each individual card *****************/
.wrapper .container .hotel .card li {
  flex: 0 0 auto; /*grow/shrink/basis */
  margin: 1vw 2vw 1vw 0;
  padding-bottom: 1vw;
  height: 26vw;
  width: 21vw;
  border-radius: 1vw;
  box-shadow: 3px 3px 3px #c3c3c3;
  position: relative;
  z-index: 1;
  overflow: hidden;
}
.wrapper .container .hotel .card li .detail {
  padding: 0.25vw 1.5vw 1vw;
  box-sizing: border-box;
}
.wrapper .container .hotel .card li .detail .title {
  margin-bottom: 0;
  font-size: 1.5vw;
  color: var(--color-text);
}
.wrapper .container .hotel .card li .detail .type {
  text-align: center;
  color: white;
  background-color: var(--color-text2);
  padding: 0.5vw;
  width: 4vw;
  border-radius: 0.5vw;
  font-size: 1vw;
}
.wrapper .container .hotel .card li .detail .cost {
  justify-self: flex-end;
}
.wrapper .container .hotel .card li .detail .cost .price {
  font-size: 1.35vw;
  font-weight: 700;
  text-align: end;
  color: var(--color-text3);
}
.wrapper .container .hotel .card li .detail .cost .total {
  font-size: 1vw;
  font-weight: normal;
  color: var(--color-text2);
}
.wrapper .container .hotel .card li .detail .reserve {
  padding: 0 1.5vw 0.7vw;
  position: absolute;
  top: auto;
  right: 0;
  bottom: 0;
}
.wrapper .container .hotel .card li .detail .reserve button {
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
.wrapper .container .hotel .card li img {
  height: 60%;
  width: 100%;
  object-fit: cover;
  border-radius: 1vw 1vw 0 0;
  transition: transform 0.3s ease-in-out;
}
.wrapper .container .hotel .card li:hover img {
  transform: scale(1.1);
}

/*************** button transition *****************/
.wrapper .container .hotel .card li .detail .reserve button::before,
.wrapper .order-box .order-content .order button::before {
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
.wrapper .container .hotel .card li .detail .reserve button:hover::before,
.wrapper .order-box .order-content .order button:hover::before {
  transform: translateX(0);
}

/*************** order box *****************/
.wrapper .order-box {
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
.wrapper .order-box .order-content {
  background: white;
  width: 40vw;
  border-radius: 1vw;
  padding: 2vw;
}
.wrapper .order-box .order-content .header {
  display: flex;
  justify-content: space-between;
}
.wrapper .order-box .order-content .header p {
  font-size: 1.5vw;
}
.wrapper .order-box .order-content .header i {
  cursor: pointer;
  font-size: 2vw;
}
.wrapper .order-box .order-content .order-date {
  padding: 0.5vw 2vw;
  margin: 1vw 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 3vw;
  border-radius: 0.5vw;
  border: 1px solid var(--color-text);
  outline: none;
}
.wrapper .order-box .order-content .order-date label {
  display: flex;
  justify-content: center;
}
.wrapper .order-box .order-content .order-date p {
  font-size: 1.2vw;
  margin-right: 1vw;
}
.wrapper .order-box .order-content .order-date select,
.wrapper .order-box .order-content .order-date input {
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
.wrapper .order-box .order-content .order-info .order-info-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 1vw;
}
.wrapper .order-box .order-content .order-info .order-info-header p {
  font-size: 1.5vw;
  font-weight: 700;
}
.wrapper .order-box .order-content .order-info .order-info-header .right {
  display: flex;
  align-items: center;
}
.wrapper
  .order-box
  .order-content
  .order-info
  .order-info-header
  .right
  .price {
  color: var(--color-text3);
  margin-right: 2vw;
  font-weight: normal;
}
.wrapper
  .order-box
  .order-content
  .order-info
  .order-info-header
  .right
  .order-info-quantity {
  display: flex;
  align-items: center;
}
.wrapper
  .order-box
  .order-content
  .order-info
  .order-info-header
  .right
  .order-info-quantity
  i {
  cursor: pointer;
  background-color: var(--color-text2);
  color: white;
  padding: 0.5vw 0.6vw;
  border-radius: 10vw;
  font-size: 1vw;
}
.wrapper
  .order-box
  .order-content
  .order-info
  .order-info-header
  .right
  .order-info-quantity
  p {
  margin: 0 1vw;
  font-weight: normal;
}
.wrapper .order-box .order-content .order-info p {
  font-size: 1.25vw;
  font-weight: normal;
}
.wrapper .order-box .order-content .order-info .order-reminder,
.wrapper .order-box .order-content .order-info .order-desc {
  display: flex;
  align-items: center;
  margin-bottom: 1vw;
}
.wrapper .order-box .order-content .order-info .order-reminder p,
.wrapper .order-box .order-content .order-info .order-desc p {
  margin-left: 1vw;
}
.wrapper .order-box .order-content .order-info .order-desc i {
  font-size: 1.5vw;
}
.wrapper .order-box .order-content .order-info .order-reminder .t1 {
  color: var(--color-text3);
}
.wrapper .order-box .order-content .order-info .order-reminder i {
  border-radius: 10vw;
  border: solid 0.1vw var(--color-text);
  padding: 0.2vw 0.5vw;
  font-size: 1vw;
}
.wrapper .order-box .order-content .order {
  display: flex;
  justify-content: center;
}
.wrapper .order-box .order-content .order button {
  width: 14vw;
  height: 3vw;
  background-color: var(--color-orange);
  border: none;
  outline: none;
  border-radius: 0.5vw;
  font-size: 1.25vw;
  font-weight: bold;
  color: white;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  z-index: 1;
}
</style>
