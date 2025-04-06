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
          <li v-for="ticket in ticketArr" @click="">
            <div class="detail">
              <div class="header">
                <p class="title">{{ ticket.name }}</p>
                <p class="desc">{{ ticket.desc }}</p>
                <p class="sold">已售出 {{ ticket.amount }} 份</p>
              </div>
              <div class="cost">
                <p class="price">{{ `¥${ticket.price}` }}</p>
              </div>
              <div class="reserve">
                <button @click="">预 定</button>
              </div>
            </div>
          </li>
        </ul>
      </div>
      <Comment category="1" :target_id="attractionId"></Comment>
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
      isLogin: false,
      attractionId: this.$route.query.id,
      attraction: "",
      ticketArr: [],
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
.wrapper .container .attraction .info {
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
  margin: 1vw 2vw 1vw 0;
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
  width: 6vw;
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
.wrapper .container .attraction .card li .detail .reserve button::before {
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

.wrapper .container .attraction .card li .detail .reserve button:hover::before {
  transform: translateX(0);
}
</style>
