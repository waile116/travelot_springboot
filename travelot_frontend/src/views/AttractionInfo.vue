<template>
  <div class="wrapper">
    <Nav2></Nav2>
    <div class="img">
      <p class="title">{{ attraction.name }}</p>
      <img class="background" :src="attraction.attractionImg" />
      <a class="map" :href="attraction.mapLink" target="_blank">
        <img :src="attraction.mapImg" />
      </a>
    </div>
    <div class="container">
      <div class="attraction">
        <div class="favourite">
          <!--array of v-bind class-->
          <i
            class="fa"
            :class="[getHeartClass, animateHeart ? 'animate-heart' : '']"
            @click="setFav"
          ></i>
        </div>
        <p class="description">{{ attraction.desc }}</p>
        <div class="info">
          <i class="fa fa-map-marker"></i>
          <p>{{ attraction.location }}</p>
        </div>
        <div class="info">
          <i class="fa fa-clock-o"></i>
          <p>{{ attraction.openTime }}</p>
        </div>
        <ul class="card">
          <li v-for="ticket in ticketArr">
            <div class="detail">
              <div class="header">
                <p class="title">{{ ticket.name }}</p>
                <p class="desc">{{ ticket.desc }}</p>
                <p class="sold">已售出 {{ ticket.quantity }} 份</p>
              </div>
              <div class="cost">
                <p class="price">{{ `¥${ticket.price}` }}</p>
              </div>
              <div class="reserve">
                <button @click="openOrder(ticket)">预 订</button>
              </div>
            </div>
          </li>
        </ul>
      </div>
      <Comment category="1" :target_id="attractionId"></Comment>
    </div>

    <div v-if="showOrder" class="order-box">
      <div class="order-content">
        <div class="header">
          <div></div>
          <p>{{ attraction.name }}门票</p>
          <i class="fa fa-close" @click="closeOrder"></i>
        </div>
        <div class="order-date">
          <label
            ><p>选择日期</p>
            <input
              type="date"
              v-model="orderDate"
              :min="minOrderDate"
              :max="maxOrderDate"
            />
          </label>
        </div>
        <div class="order-info">
          <div class="order-info-header">
            <p>{{ orderTicket.name }}</p>
            <div class="right">
              <p class="price">¥{{ orderTicket.price * orderQuantity }}</p>
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
            <i class="fa fa-user"></i>
            <p>{{ orderTicket.desc }}</p>
          </div>
          <div class="order-reminder">
            <i class="fa fa-info"></i>
            <p class="t1">不可退款</p>
            <p>无需取票</p>
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
  name: "AttractionInfo",
  data() {
    return {
      user: "",
      isLogin: false,
      attractionId: this.$route.query.id,
      attraction: "",
      ticketArr: [],

      userFav: false,
      animateHeart: false,

      minOrderDate: this.getNextDate(),
      maxOrderDate: this.getWeekDate(),
      showOrder: false,
      orderDate: this.getNextDate(),
      orderTicket: "", // selected ticket object
      orderQuantity: 1,
    };
  },
  created() {
    this.user = this.$getSessionStorage("user");
    // check if login
    if (this.user != null) {
      this.isLogin = true;
    }

    //get attraction info with attractionId
    this.$axios
      .get(`AttractionController/getAttractionById/${this.attractionId}`)
      .then((response) => {
        this.attraction = response.data.result;
      })
      .catch((error) => {
        console.error(error);
      });

    // get ticket list with attractionId
    this.$axios
      .get(`AttractionController/listTicketById/${this.attractionId}`)
      .then((response) => {
        this.ticketArr = response.data.result;
      })
      .catch((error) => {
        console.error(error);
      });

    // get favourite with user, category and target id
    this.$axios
      .get(
        `FavouriteController/getFavouriteById/${this.user.userId}/1/${this.attractionId}`
      )
      .then((response) => {
        if (response.data.result) {
          this.userFav = true;
        }
      })
      .catch((error) => {
        console.error(error);
      });
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
    getNextDate() {
      const date = new Date();
      date.setDate(date.getDate() + 1);
      return date.toISOString().split("T")[0]; // Format YYYY-MM-DD
    },

    getWeekDate() {
      const date = new Date();
      date.setDate(date.getDate() + 7);
      return date.toISOString().split("T")[0]; // Format YYYY-MM-DD
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
            `FavouriteController/saveFavourite/${this.user.userId}/1/${this.attractionId}`
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
            `FavouriteController/removeFavourite/${this.user.userId}/1/${this.attractionId}`
          )
          .then((response) => {
            console.log(response.data.message);
          })
          .catch((error) => {
            console.error(error);
          });
      }
    },

    openOrder(ticket) {
      this.showOrder = true;
      this.orderTicket = ticket; // parse ticket to order
    },
    closeOrder() {
      this.showOrder = false;
      this.orderQuantity = 1; // reset ticket quantity
    },

    toPayment() {
      // create and save order
      this.$axios
        .post(
          `OrdersController/saveOrders/${this.user.userId}/1/${this.attractionId}/${this.orderTicket.ticketId}/${this.orderTicket.price}/${this.orderQuantity}/${this.orderDate}/null`
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
};
</script>

<style scoped>
.wrapper {
  position: relative;
}

/*************** header container *****************/
.wrapper .img {
  margin: 0 4vw;
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
.wrapper .container .info {
  display: flex;
  align-items: center;
  margin-bottom: 1vw;
}
.wrapper .container .attraction .info i {
  font-size: 1.5vw;
  margin-right: 0.8vw;
}
.wrapper .container .attraction .info p {
  font-size: 1vw;
  font-weight: normal;
}
.wrapper .container .attraction .favourite {
  display: flex;
  justify-content: flex-end;
  margin: 0 3vw 1vw;
}
.wrapper .container .attraction .favourite i {
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
.wrapper .container .attraction .description {
  font-size: 1vw;
  margin-bottom: 1vw;
  font-weight: normal;
}

/*************** card container *****************/
.wrapper .container .attraction .card {
  display: flex;
  justify-content: flex-start;
  flex-wrap: wrap;
}

.wrapper .container .attraction .card:last-child {
  margin-bottom: 2vw;
}

/*************** each individual card *****************/
.wrapper .container .attraction .card li {
  flex: 0 0 auto; /*grow/shrink/basis */
  margin: 1attractionvw 2vw 1vw 0;
  padding: 1vw 1.5vw;
  box-sizing: border-box;
  width: 21vw;
  border-radius: 1vw;
  box-shadow: 3px 3px 3px #c3c3c3;
  position: relative;
  z-index: 1;
  overflow: hidden;
}
.wrapper .container .attraction .card li .detail .header .title {
  margin-bottom: 0;
  font-size: 1.5vw;
  color: var(--color-text);
}
.wrapper .container .attraction .card li .detail .header .desc {
  font-size: 1vw;
  margin-top: 0.5vw;
  font-weight: normal;
}
.wrapper .container .attraction .card li .detail .header .sold {
  text-align: center;
  color: white;
  background-color: var(--color-text2);
  padding: 0.5vw;
  width: 7vw;
  border-radius: 0.5vw;
  font-size: 1vw;
  margin-top: 0.5vw;
}
.wrapper .container .attraction .card li .detail .cost {
  justify-self: flex-end;
  padding: 0.5vw 0;
}
.wrapper .container .attraction .card li .detail .cost .price {
  font-size: 1.5vw;
  font-weight: 700;
  text-align: end;
  color: var(--color-text3);
}
.wrapper .container .attraction .card li .detail .cost .total {
  font-size: 1vw;
  font-weight: normal;
  color: var(--color-text2);
}
.wrapper .container .attraction .card li .detail .reserve {
  justify-self: flex-end;
}
.wrapper .container .attraction .card li .detail .reserve button {
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
.wrapper .container .attraction .card li img {
  height: 60%;
  width: 100%;
  object-fit: cover;
  border-radius: 1vw 1vw 0 0;
  transition: transform 0.3s ease-in-out;
}

.wrapper .container .attraction .card li:hover img {
  transform: scale(1.1);
}

/*************** button transition *****************/
.wrapper .container .attraction .card li .detail .reserve button::before,
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

.wrapper .container .attraction .card li .detail .reserve button:hover::before,
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
  width: 14vw;
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
