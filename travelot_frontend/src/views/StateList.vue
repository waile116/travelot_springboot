<template>
  <div class="wrapper">
    <Nav></Nav>
    <div class="carousel">
      <p>西海岸州属</p>
      <ul class="card">
        <li v-for="item in isWestCoast" @click="">
          <img :src="item.stateImg" />
          <p class="title">{{ item.name }}</p>
          <p class="desc">{{ item.desc }}</p>
          <i class="fa fa-chevron-right"></i>
        </li>
      </ul>
      <p>东海岸州属</p>
      <ul class="card">
        <li v-for="item in isEastCoast" @click="">
          <img :src="item.stateImg" />
          <p class="title">{{ item.name }}</p>
          <p class="desc">{{ item.desc }}</p>
          <i class="fa fa-chevron-right"></i>
        </li>
      </ul>
      <p>东马州属</p>
      <ul class="card">
        <li v-for="item in isEast" @click="">
          <img :src="item.stateImg" />
          <p class="title">{{ item.name }}</p>
          <p class="desc">{{ item.desc }}</p>
          <i class="fa fa-chevron-right"></i>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import Nav from "../components/Nav.vue";

export default {
  name: "Index",
  data() {
    return {
      stateId: this.$route.query.stateId,
      state: {},
      stateArr: [],
    };
  },
  created() {
    //根据orderTypeId查询商家信息
    this.$axios
      .post("StateController/listState")
      .then((response) => {
        this.stateArr = response.data;
      })
      .catch((error) => {
        console.error(error);
      });
  },
  components: {
    Nav,
  },
  computed: {
    isWestCoast() {
      return this.stateArr.filter((item) => item.region === "west coast");
    },
    isEastCoast() {
      return this.stateArr.filter((item) => item.region === "east coast");
    },
    isEast() {
      return this.stateArr.filter((item) => item.region === "east");
    },
  },
};
</script>

<style scoped>
.wrapper {
  position: relative;
}

/*carousel container*/
.wrapper .carousel {
  display: flex;
  flex-direction: column;
  padding: 0 2vw 2vw 4vw;
}

/* title */
.wrapper .carousel > p {
  padding-top: 1.5vw;
  font-size: 2vw;
}

/* card container */
.wrapper .carousel .card {
  display: flex;
  justify-content: flex-start;
  padding-bottom: 0.5vw;
  flex-wrap: wrap;
}

/* hide scrollbar */
.wrapper .carousel .card::-webkit-scrollbar {
  background-color: transparent;
  height: 10px;
}

/* each individual card */
.wrapper .carousel .card li {
  flex: 0 0 auto; /*grow/shrink/basis */
  margin: 2vw 2vw 0 0;
  padding-bottom: 1vw;
  height: 25vw;
  width: 21vw;
  border-radius: 15px;
  box-shadow: 3px 3px 3px #c3c3c3;
  cursor: pointer;
  position: relative;
  z-index: 1;
  overflow: hidden;
}

/* background transition*/
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

.wrapper .carousel .card li img {
  height: 60%;
  width: 100%;
  object-fit: cover;
  border-radius: 15px 15px 0 0;
  transition: transform 0.3s ease-in-out;
}

.wrapper .carousel .card li:hover img {
  transform: scale(1.1);
}

.wrapper .carousel .card li p {
  padding: 1vw 1.5vw 0;
}

/*text and arrow transition*/
.wrapper .carousel .card li .title {
  font-size: 1.5vw;
}
.wrapper .carousel .card li .desc {
  font-size: 1vw;
}
.wrapper .carousel .card li .title,
.wrapper .carousel .card li .desc {
  position: relative;
  color: var(--color-text);
  transition: color 0.2s ease-in-out;
}
.wrapper .carousel .card li i {
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
.wrapper .carousel .card li:hover .title,
.wrapper .carousel .card li:hover .desc,
.wrapper .carousel .card li:hover i {
  color: white;
}
</style>
