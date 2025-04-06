<template>
  <div class="wrapper">
    <div class="container">
      <div class="box">
        <p class="title">我的评价</p>
        <ul class="comment">
          <li v-for="comment in commentArr">
            <div class="header">
              <div class="info">
                <p @click="toInfo(comment.categoryName, comment.targetId)">
                  {{ comment.targetName }}<i class="fa fa-chevron-right"></i>
                </p>
                <div class="star">
                  <i
                    v-for="index in 5"
                    :key="index"
                    class="fa"
                    :class="getStarClass(index, comment.rating)"
                  ></i>
                </div>
              </div>
              <p class="comment-date">发布于 {{ comment.createT }}</p>
            </div>
            <div class="body">
              <p class="comment-text">{{ comment.text }}</p>
              <div class="comment-img">
                <img :src="comment.commentImg" v-if="comment.commentImg" />
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "UserCommentList",
  data() {
    return {
      isLogin: false,
      user: {},
      commentArr: [],
    };
  },
  created() {
    this.user = this.$getSessionStorage("user");
    // check if login
    if (this.user != null) {
      this.isLogin = true;
    }

    // get comment list with user id
    this.$axios
      .get(`CommentController/listCommentByUserId/${this.user.userId}`)
      .then((response) => {
        this.commentArr = response.data.result;

        // sort comment in descending order
        this.commentArr.sort(
          (a, b) => new Date(b.createT) - new Date(a.createT)
        );

        // fetch target name for each comment
        this.commentArr.forEach((comment, index) => {
          comment.createT = comment.createT.split("T")[0];

          let categoryName;
          switch (comment.category) {
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
              `${categoryName}Controller/get${categoryName}ById/${comment.targetId}`
            )
            .then((response) => {
              comment.targetName = response.data.result.name;
              comment.categoryName = categoryName;
            })
            .catch((error) => console.error(error));
        });
      })
      .catch((error) => {
        console.error(error);
      });
  },
  methods: {
    openComment() {
      this.showComment = true;
    },
    closeComment() {
      this.showComment = false;
    },

    getStarClass(index, rating) {
      const temp = rating || this.hoverRating;
      if (index <= temp) {
        return "fa-star";
      } else if (index - 0.5 === temp) {
        return "fa-star-half-o";
      } else {
        return "fa-star-o";
      }
    },

    toInfo(name, id) {
      this.$router.push({ path: `/${name}Info`, query: { id: id } });
    },
  },
};
</script>

<style scoped>
/*************** comment box *****************/
.wrapper .container {
  display: flex;
  justify-content: center;
  margin: 3vw;
  padding-bottom: 3vw;
}
.wrapper .container .box {
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

/*************** comment *****************/
.wrapper .container .box .comment {
  margin-top: 1vw;
}
.wrapper .container .box .comment > li {
  padding: 2vw;
  border-radius: 1vw;
  border: 1px solid #c3c3c3;
  margin-bottom: 1vw;
  height: 12vw;
}
.wrapper .container .box .comment li .header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 1vw;
}
.wrapper .container .box .comment li .header .info {
  display: flex;
  flex-direction: column;
}
.wrapper .container .box .comment li .header .info img {
  width: 5vw;
  height: 5vw;
  border-radius: 0.5vw;
  object-fit: cover;
}
.wrapper .container .box .comment li .header .info p {
  font-size: 1.5vw;
  cursor: pointer;
  justify-content: center;
}
.wrapper .container .box .comment li .header .info p i {
  margin-left: 1vw;
}
.wrapper .container .box .comment li .header .info .star {
  color: var(--color-text2);
  font-size: 1.4vw;
}
.wrapper .container .box .comment li .header .comment-date {
  font-size: 1vw;
  color: var(--color-text2);
  font-weight: normal;
}
.wrapper .container .box .comment li .body {
  display: flex;
  justify-content: space-between;
}
.wrapper .container .box .comment li .body .comment-text {
  font-size: 1.25vw;
  font-weight: normal;
  margin-bottom: 1vw;
}
.wrapper .container .box .comment li .body .comment-img {
  display: flex;
}
.wrapper .container .box .comment li .body .comment-img img {
  height: 8vw;
  width: 20vw;
  border-radius: 0.5vw;
  object-fit: cover;
  cursor: pointer;
}
</style>
