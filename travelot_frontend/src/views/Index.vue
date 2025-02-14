<template>
  <div class="wrapper">
    <Nav></Nav>
    <ul class="carousel">
      <li>
        <p>马来西亚热门州属</p>
        <ul class="card">
          <li>
            <img :src="stateArr[0].stateImg" alt="kualalumpur" />
            <p>吉隆坡</p>
          </li>
          <li>
            <img src="" alt="penang" />
            <p>槟城</p>
          </li>
          <li>
            <img src="" alt="johor" />
            <p>柔佛</p>
          </li>
          <li>
            <img src="" alt="pahang" />
            <p>彭亨</p>
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
  flex-wrap: wrap;
}

.wrapper .carousel .card li {
  margin: 2vw 2vw 0 0;
  height: 25vw;
  width: 20vw;
  border-radius: 15px;
  box-shadow: 1px 3px 3px #c3c3c3;
  border: solid 1px #f7f7f7;
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

.wrapper .carousel .card li p {
  padding: 1vw 2vw;
}

.wrapper .carousel .card li img {
}
</style>
