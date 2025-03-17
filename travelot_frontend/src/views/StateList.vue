<template>
  <div class="wrapper">
    <Nav></Nav>
    <div class="carousel">
      <div class="region">
        <!-- :class is vue's class binding, when selected region matches, it will render  class "active"-->
        <p
          @click="selectedRegion = 'west coast'"
          :class="{ active: selectedRegion === 'west coast' }"
        >
          西海岸
        </p>
        <p
          @click="selectedRegion = 'east coast'"
          :class="{ active: selectedRegion === 'east coast' }"
        >
          东海岸
        </p>
        <p
          @click="selectedRegion = 'east'"
          :class="{ active: selectedRegion === 'east' }"
        >
          东马
        </p>
      </div>
      <ul class="card">
        <li v-for="state in filterState" @click="toStateInfo(state.stateId)">
          <img :src="state.stateImg" alt="state image" />
          <p class="title">{{ state.name }}</p>
          <p class="desc">{{ state.desc }}</p>
          <i class="fa fa-chevron-right"></i>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import Nav from "../components/Nav.vue";

export default {
  name: "StateList",
  data() {
    return {
      selectedRegion: "west coast", //default
      stateArr: [],
    };
  },
  created() {
    this.$axios
      .get("StateController/listState")
      .then((response) => {
        this.stateArr = response.data.result;
      })
      .catch((error) => {
        console.error(error);
      });
  },
  components: {
    Nav,
  },
  computed: {
    filterState() {
      return this.stateArr.filter(
        (state) => state.region === this.selectedRegion
      );
    },
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
.wrapper .carousel {
  display: flex;
  flex-direction: column;
  margin: 1.5vw 2vw 0 4vw;
}

/*************** title *****************/
.wrapper .carousel .region {
  display: flex;
  font-size: 1.5vw;
  color: var(--color-text2);
  align-items: center;
}

.wrapper .carousel .region .active {
  display: flex;
  font-size: 2vw;
  color: var(--color-text);
}

/*************** card container *****************/
.wrapper .carousel .card {
  display: flex;
  justify-content: flex-start;
  flex-wrap: wrap;
}

.wrapper .carousel .card:last-child {
  margin-bottom: 2vw;
}

/*************** each individual card *****************/
.wrapper .carousel .card li {
  flex: 0 0 auto; /*grow/shrink/basis */
  margin: 1vw 2vw 1vw 0;
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

/*************** hide scrollbar *****************/
.wrapper .carousel .card::-webkit-scrollbar {
  background-color: transparent;
  height: 10px;
}

/*************** title transition *****************/
.wrapper .carousel .region p {
  margin: 0 3vw 1vw 0.1vw;
  cursor: pointer;
  position: relative;
  transition: transform 0.3s ease-in-out;
}
.wrapper .carousel .region p:hover {
  transform: scale(1.1);
}

.wrapper .carousel .region p::before,
.wrapper .carousel .region .active::before {
  content: "";
  position: absolute;
  bottom: 0;
  left: 0%;
  transform: translateX(-100%) translateY(100%) scale(0);
  transition: transform 0.3s ease-in-out;
  height: 0.2vw;
  width: 110%;
  border-radius: 2px;
  background-color: var(--color-text2);
}

.wrapper .carousel .region .active::before {
  background-color: var(--color-text);
}

.wrapper .carousel .region p:hover::before {
  transform: translateX(-5%) translateY(100%);
}

/*************** card background transition*****************/
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
  border-radius: 1vw 1vw 0 0;
  transition: transform 0.3s ease-in-out;
}

.wrapper .carousel .card li:hover img {
  transform: scale(1.1);
}

/*************** card text and arrow transition *****************/
.wrapper .carousel .card li .title {
  font-size: 1.5vw;
}
.wrapper .carousel .card li .desc {
  font-size: 1vw;
  font-weight: 500;
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
