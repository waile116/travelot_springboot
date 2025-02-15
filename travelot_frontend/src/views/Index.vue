<template>
  <div class="wrapper">
    <Nav></Nav>
    <ul class="carousel">
      <li>
        <p>马来西亚热门州属</p>
        <ul class="card" ref="scrollContainer">
          <li v-for="item in stateArr" @click="">
            <img :src="item.stateImg" alt="kualalumpur" />
            <p class="title">{{ item.name }}</p>
            <p class="desc">{{ item.desc }}</p>
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
  mounted() {
    //horizontal scroll
    const container = this.$refs.scrollContainer;
    if (container) {
      const handleWheel = (e) => {
        e.preventDefault();
        container.scrollLeft += e.deltaY;
      };
      container.addEventListener("wheel", handleWheel); //listen for mouse wheel event and execute handleWheel when detected
    }
  },
};
</script>

<style scoped>
.wrapper .carousel {
  display: flex;
  flex-direction: column;
}

.wrapper .carousel > li {
  padding: 2vw 2vw 0 4vw;
}

.wrapper .carousel > li > p {
  font-size: 2vw;
}

.wrapper .carousel li:last-child {
  margin-bottom: 2vw;
}

.wrapper .carousel .card {
  display: flex;
  justify-content: flex-start;
  overflow-x: scroll; /*make overflow scrollable*/
  scroll-behavior: smooth;
  scroll-snap-type: x mandatory; /*automatically move to snap point*/
  padding-bottom: 1.5vw;
}

/* hide scrollbar */
.wrapper .carousel .card::-webkit-scrollbar {
  background-color: transparent;
  height: 10px;
}
.wrapper .carousel .card::-webkit-scrollbar-thumb {
  border-radius: 10px;
  background-color: var(--color-text);
}

.wrapper .carousel .card li {
  scroll-snap-align: start; /*snap at start of elements*/
  flex: 0 0 auto; /*grow/shrink/basis */
  margin: 2vw 2vw 0 0;
  padding-bottom: 1vw;
  height: 25vw;
  width: 20vw;
  border-radius: 15px;
  box-shadow: 1px 3px 3px #c3c3c3;
  border: solid 1px #d6d6d6;
  cursor: pointer;
  position: relative;
  z-index: 1;
  overflow: hidden;
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
  background-color: #f7f7f7;
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
  padding: 1vw 2vw 0;
}

.wrapper .carousel .card li .title {
  font-size: 1.5vw;
}

.wrapper .carousel .card li .desc {
  font-size: 1vw;
}
</style>
