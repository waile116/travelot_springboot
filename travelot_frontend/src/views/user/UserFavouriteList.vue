<template>
  <div class="wrapper">
    <div class="container">
      <div class="box">
        <p class="title">我的收藏</p>
        <ul class="favourite">
          <li
            v-for="favourite in favouriteArr"
            @click="toInfo(favourite.categoryName, favourite.targetId)"
          >
            <img :src="favourite.categoryImg" />
            <div class="info">
              <div class="header">
                <p class="title">{{ favourite.targetName }}</p>
                <div class="rating">
                  <p>评分</p>
                  <p class="score">{{ favourite.rating }}</p>
                  <p>/5</p>
                </div>
                <div class="location">
                  <i class="fa fa-map-marker"></i>
                  <p>{{ favourite.location }}</p>
                </div>
              </div>
              <div class="cost">
                <!-- <p v-if="getMinPrice(attraction.attractionId) !== 0">门票</p>
                <p class="price">
                  {{
                    getMinPrice(attraction.attractionId) === 0
                      ? "免费"
                      : `¥${getMinPrice(attraction.attractionId)}`
                  }}
                </p>
                <p v-if="getMinPrice(attraction.attractionId) !== 0">起</p> -->
              </div>
            </div>
            <div class="arrow">
              <i class="fa fa-chevron-right"></i>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "UserFavouriteList",
  data() {
    return {
      isLogin: false,
      user: {},
      favouriteArr: [],
    };
  },
  created() {
    this.user = this.$getSessionStorage("user");
    // check if login
    if (this.user != null) {
      this.isLogin = true;
    }

    // get favourite list with user id
    this.$axios
      .get(`FavouriteController/listFavouriteByUserId/${this.user.userId}`)
      .then((response) => {
        this.favouriteArr = response.data.result;

        // sort favourite in descending order
        this.favouriteArr.sort(
          (a, b) => new Date(b.createT) - new Date(a.createT)
        );

        // fetch target name for each favourite
        this.favouriteArr.forEach((favourite) => {
          favourite.createT = favourite.createT.split("T")[0];

          let categoryName;
          switch (favourite.category) {
            case 1:
              categoryName = "Attraction";
              break;
            case 2:
              categoryName = "Restaurant";
              break;
            case 3:
              categoryName = "Hotel";
          }
          this.$axios
            .get(
              `${categoryName}Controller/get${categoryName}ById/${favourite.targetId}`
            )
            .then((response) => {
              let categoryNameL =
                categoryName.charAt(0).toLowerCase() + categoryName.slice(1);
              favourite.categoryImg =
                response.data.result[`${categoryNameL}Img`];
              favourite.targetName = response.data.result.name;
              favourite.categoryName = categoryName;
              favourite.rating = response.data.result.rating;
              favourite.location = response.data.result.location;
            })
            .catch((error) => console.error(error));
        });
      })
      .catch((error) => {
        console.error(error);
      });
  },
  methods: {
    toInfo(name, id) {
      this.$router.push({ path: `/${name}Info`, query: { id: id } });
    },
  },
};
</script>

<style scoped>
/*************** favourite box *****************/
.wrapper .container {
  display: flex;
  justify-content: center;
  margin: 3vw;
  padding-bottom: 3vw;
}
.wrapper .container .box {
  overflow: hidden;
  height: 100%;
  width: 75vw;
  border-radius: 1vw;
  box-shadow: 3px 3px 3px #c3c3c3;
  border: 0.1vw solid #c3c3c3;
  box-sizing: border-box;
  padding: 2vw;
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.wrapper .container .box .title {
  font-size: 1.5vw;
}

/*************** each individual card *****************/
.wrapper .container .box .favourite {
  margin-top: 1vw;
}
.wrapper .container .box .favourite li {
  display: flex;
  height: 16vw;
  cursor: pointer;
  border-radius: 1vw;
  border: 1px solid #c3c3c3;
  margin-bottom: 1vw;
  overflow: hidden;
  z-index: 1;
}
.wrapper .container .box .favourite li .info {
  flex: 6;
  padding: 1vw 2vw;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  font-weight: 500;
}
.wrapper .container .box .favourite li .info p {
  font-size: 1vw;
}
.wrapper .container .box .favourite li .info .header .title {
  font-size: 1.5vw;
}
.wrapper .container .box .favourite li .info .header .rating,
.wrapper .container .box .favourite li .info .header .location {
  display: flex;
  align-items: center;
  margin-top: 0.5vw;
}
.wrapper .container .box .favourite li .info .header .rating {
  align-items: flex-end;
}
.wrapper .container .box .favourite li .info .header .rating .score {
  font-size: 1.25vw;
}
.wrapper .container .box .favourite li .info .header .location {
  width: 30vw;
}
.wrapper .container .box .favourite li .info .header .location i {
  font-size: 1.5vw;
  margin-right: 0.8vw;
}
.wrapper .container .box .favourite li .info .header .rating .score {
  padding: 0 0.25vw;
}
.wrapper .container .box .favourite li .arrow {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
}
.wrapper .container .box .favourite li .arrow i {
  font-size: 2vw;
}
.wrapper .container .box .favourite li img {
  flex: 3;
  height: 100%;
  width: 12vw;
  object-fit: cover;
  border-radius: 1vw 0 0 1vw;
  transition: transform 0.3s ease-in-out;
}
.wrapper .container .box .favourite li:hover img {
  transform: scale(1.1);
}
.wrapper .container .box .favourite li div {
  color: var(--color-text2);
}
.wrapper .container .box .favourite li .header .title,
.wrapper .container .box .favourite li .header .rating .score,
.wrapper .container .box .favourite li .arrow {
  color: var(--color-text);
}
</style>
