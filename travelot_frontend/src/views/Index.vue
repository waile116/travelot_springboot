<template>
  <div class="wrapper">
    <Nav></Nav>
    <ul class="carousel">
      <li class="state">
        <p>马来西亚州属</p>
        <ul class="card" ref="scrollContainer">
          <i class="fa fa-chevron-left disabled" ref="leftArrow"></i>
          <i class="fa fa-chevron-right" ref="rightArrow"></i>
          <li v-for="state in stateArr" @click="toStateInfo(state.stateId)">
            <img :src="state.stateImg" />
            <p class="title">{{ state.name }}</p>
            <p class="desc">{{ state.desc }}</p>
            <i class="fa fa-chevron-right"></i>
          </li>
        </ul>
      </li>
      <li class="attraction">
        <p>热门景点</p>
        <ul class="card">
          <li v-for="attraction in attractionArr" @click="">
            <img :src="attraction.attractionImg" />
            <p class="title">{{ attraction.name }}</p>
            <div class="rating">
              评分
              <p class="score">{{ attraction.rating }}</p>
              /5
            </div>
            <div class="ticket">
              <span v-if="attraction.price !== 0">门票</span>
              <p class="price">
                {{ attraction.price === 0 ? "免费" : `¥${attraction.price}` }}
              </p>
              <span v-if="attraction.price !== 0">起</span>
            </div>
          </li>
        </ul>
      </li>
    </ul>
  </div>
</template>

<script>
import Nav from "../components/Nav.vue";

export default {
  name: "Index",
  data() {
    return {
      stateArr: [],
      attractionArr: [],
    };
  },
  created() {
    this.$axios
      .get("StateController/listState")
      .then((response) => {
        this.stateArr = response.data;
      })
      .catch((error) => {
        console.error(error);
      });
    this.$axios
      .get("AttractionController/listAttractionRandom")
      .then((response) => {
        this.attractionArr = response.data;
      })
      .catch((error) => {
        console.error(error);
      });
  },
  mounted() {
    //horizontal scroll
    const container = this.$refs.scrollContainer;
    const rightArrow = this.$refs.rightArrow;
    const leftArrow = this.$refs.leftArrow;

    const updateArrows = () => {
      // if reach threshold, add or remove the label
      if (container.scrollLeft <= container.scrollWidth * 0.004) {
        leftArrow.classList.add("disabled");
      } else {
        leftArrow.classList.remove("disabled");
      }
      if (
        container.scrollLeft + container.clientWidth * 1.45 >=
        container.scrollWidth
      ) {
        rightArrow.classList.add("disabled");
      } else {
        rightArrow.classList.remove("disabled");
      }
    };

    // update arrow when clicked
    leftArrow.addEventListener("click", () => {
      container.addEventListener("scroll", updateArrows);
      container.scrollBy({
        left: -container.clientWidth * 0.4,
        behavior: "smooth",
      });
    });
    rightArrow.addEventListener("click", () => {
      container.addEventListener("scroll", updateArrows);
      container.scrollBy({
        left: container.clientWidth * 0.4,
        behavior: "smooth",
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
  margin: 1.5vw 4vw 0;
}

/* carousel arrow */
.wrapper .carousel .fa-chevron-right,
.wrapper .carousel .fa-chevron-left {
  position: absolute;
  top: 32%;
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
.wrapper .carousel .fa-chevron-left {
  left: 0;
  margin-left: 2vw;
}
.wrapper .carousel .fa-chevron-right {
  right: 0;
  margin-right: 2vw;
}
.wrapper .carousel .fa-chevron-right:hover:not(.disabled), /* not(.disabled) means only apply when no disabled*/
.wrapper .carousel .fa-chevron-left:hover:not(.disabled) {
  color: white;
  background-color: var(--color-blue1);
}
.wrapper .carousel .fa-chevron-right.disabled,
.wrapper .carousel .fa-chevron-left.disabled {
  cursor: not-allowed;
}

/* title */
.wrapper .carousel p {
  font-size: 2vw;
}

.wrapper .carousel li:last-child {
  margin-bottom: 2vw;
}

/* CARD COMMON STYLE*/
.wrapper .carousel .card {
  display: flex;
  justify-content: flex-start;
  overflow-x: scroll; /*make overflow scrollable*/
  scroll-behavior: smooth;
  scroll-snap-type: x mandatory; /*automatically move to snap point*/
}

/* each individual card */
.wrapper .carousel .card li {
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
.wrapper .carousel .card li p {
  padding: 1vw 1.5vw 0;
}
.wrapper .carousel .card li .title {
  font-size: 1.5vw;
}
.wrapper .carousel .card li .desc {
  font-size: 1vw;
  font-weight: 500;
}

/* ATTRACTION CARD */
.wrapper .carousel .attraction .card div {
  display: flex;
  align-items: flex-end;
  padding: 0.5vw 1.5vw 0;
  font-size: 1vw;
  line-height: 0.8;
  font-weight: 500;
}

.wrapper .carousel .attraction .card li {
  height: 20vw;
}

.wrapper .carousel .attraction .card li .ticket {
  position: absolute;
  top: auto;
  right: 0;
  bottom: 0;
  padding: 1vw 1.5vw;
}

.wrapper .carousel .attraction .card li .rating .score {
  font-size: 1.25vw;
}

.wrapper .carousel .attraction .card li .ticket .price {
  font-size: 1.5vw;
  font-weight: 700;
}

.wrapper .carousel .attraction .card li .rating .score,
.wrapper .carousel .attraction .card li .ticket .price {
  padding: 0 0.25vw;
}

/* hide scrollbar */
.wrapper .carousel .card::-webkit-scrollbar {
  background-color: transparent;
  height: 10px;
}

/* card background transition */
.wrapper .carousel .card li img {
  height: 60%;
  width: 100%;
  object-fit: cover;
  border-radius: 1vw 1vw 0 0;
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

/* card text and arrow transition*/
.wrapper .carousel .attraction .card li div {
  position: relative;
  color: var(--color-text2);
  transition: color 0.2s ease-in-out;
}
.wrapper .carousel .card li .title,
.wrapper .carousel .card li .desc,
.wrapper .carousel .card li .rating .score {
  position: relative;
  color: var(--color-text);
  transition: color 0.2s ease-in-out;
}
.wrapper .carousel .attraction .card li .ticket .price {
  position: relative;
  color: var(--color-text3);
  transition: color 0.2s ease-in-out;
}
.wrapper .carousel .card li .fa-chevron-right {
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
.wrapper .carousel .card li:hover .fa-chevron-right,
.wrapper .carousel .card li:hover div,
.wrapper .carousel .card li:hover .rating .score,
.wrapper .carousel .card li:hover .ticket .price {
  background-color: transparent;
  color: white;
}
</style>
