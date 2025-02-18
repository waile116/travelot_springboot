<template>
  <div class="wrapper">
    <Nav></Nav>
    <ul class="carousel">
      <li>
        <p>马来西亚热门州属</p>
        <ul class="card" ref="scrollContainer">
          <i class="fa fa-chevron-left"></i>
          <i class="fa fa-chevron-right"></i>
          <li v-for="state in stateArr" @click="toStateInfo(state.stateId)">
            <img :src="state.stateImg" />
            <p class="title">{{ state.name }}</p>
            <p class="desc">{{ state.desc }}</p>
            <i class="fa fa-chevron-right"></i>
          </li>
        </ul>
      </li>
      <li>
        <p>热门景点</p>
        <ul class="card">
          <li>
            <img src="" alt="kualalumpur" />
            <p>吉隆坡</p>
          </li>
          <li>
            <img src="" alt="penang" />
            <p>槟城</p>
          </li>
          <li>
            <img src="" alt="penang" />
            <p>槟城</p>
          </li>
          <li>
            <img src="" alt="penang" />
            <p>槟城</p>
          </li>
        </ul>
      </li>
      <li>
        <p>热门美食</p>
        <ul class="card">
          <li>
            <img src="" alt="kualalumpur" />
            <p>吉隆坡</p>
          </li>
          <li>
            <img src="" alt="penang" />
            <p>槟城</p>
          </li>
          <li>
            <img src="" alt="penang" />
            <p>槟城</p>
          </li>
          <li>
            <img src="" alt="penang" />
            <p>槟城</p>
          </li>
        </ul>
      </li>
      <li>
        <p>推荐住宿</p>
        <ul class="card">
          <li>
            <img src="" alt="kualalumpur" />
            <p>吉隆坡</p>
          </li>
          <li>
            <img src="" alt="penang" />
            <p>槟城</p>
          </li>
          <li>
            <img src="" alt="penang" />
            <p>槟城</p>
          </li>
          <li>
            <img src="" alt="penang" />
            <p>槟城</p>
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
  },
  components: {
    Nav,
  },
  mounted() {
    //horizontal scroll
    const container = this.$refs.scrollContainer;
    const rightArrow = document.querySelector(
      ".wrapper .carousel .fa-chevron-right"
    );
    const leftArrow = document.querySelector(
      ".wrapper .carousel .fa-chevron-left"
    );
    if (rightArrow && leftArrow) {
      rightArrow.addEventListener("click", () => {
        container.scrollBy({ left: 600, behavior: "smooth" });
      });

      leftArrow.addEventListener("click", () => {
        container.scrollBy({ left: -600, behavior: "smooth" });
      });
    }
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
  top: 16%;
  padding: 0.8vw 1vw;
  font-size: 2vw;
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
.wrapper .carousel .fa-chevron-right:hover,
.wrapper .carousel .fa-chevron-left:hover {
  color: white;
  background-color: var(--color-blue1);
}

/* title */
.wrapper .carousel p {
  font-size: 2vw;
}

.wrapper .carousel li:last-child {
  margin-bottom: 2vw;
}

/* card container */
.wrapper .carousel .card {
  display: flex;
  justify-content: flex-start;
  overflow-x: scroll; /*make overflow scrollable*/
  scroll-behavior: smooth;
  scroll-snap-type: x mandatory; /*automatically move to snap point*/
}

/* hide scrollbar */
.wrapper .carousel .card::-webkit-scrollbar {
  background-color: transparent;
  height: 10px;
}

/* each individual card */
.wrapper .carousel .card li {
  scroll-snap-align: start; /*snap at start of elements*/
  flex: 0 0 auto; /*grow/shrink/basis */
  margin: 1vw;
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
.wrapper .carousel .card li:hover .fa-chevron-right {
  background-color: transparent;
  color: white;
}
</style>
