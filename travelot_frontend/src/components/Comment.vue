<template>
  <div class="wrapper">
    <div class="container">
      <div class="room">
        <div class="comment-header">
          <div class="header">
            评价
            <p>({{ commentArr.length }})</p>
          </div>
          <button class="button" @click="openComment">
            去评价 <i class="fa fa-chevron-right"></i>
          </button>
        </div>
        <ul class="comment">
          <li v-for="comment in commentArr">
            <div class="header">
              <div class="user">
                <img :src="comment.userImg" alt="avatar" />
                <div class="name">
                  <p>{{ comment.username }}</p>
                  <div class="star">
                    <i
                      v-for="index in 5"
                      :key="index"
                      class="fa"
                      :class="getStarClass(index, comment.rating)"
                    ></i>
                  </div>
                </div>
              </div>
              <p class="comment-date">{{ comment.createT }}</p>
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

      <div v-if="showComment" class="comment-box">
        <div class="comment-content">
          <div class="header">
            <div></div>
            <p>{{ name }}</p>
            <i class="fa fa-close" @click="closeComment"></i>
          </div>
          <div class="rating">
            <p>评分</p>
            <p v-if="userRating > 0">{{ userRating }}</p>
            <div class="star">
              <i
                v-for="index in 5"
                :key="index"
                class="fa"
                :class="getStarClass(index)"
                @mousemove="hoverStarEvent(index, $event)"
                @mouseleave="resetHover"
                @click="setRating(index)"
              ></i>
            </div>
          </div>
          <div class="text">
            <textarea
              placeholder="分享真实体验可以帮到更多人哦~"
              v-model="commentText"
            ></textarea>
          </div>
          <div class="img">
            <img :src="commentImg" v-if="commentImg" />
            <label class="img-box">
              <input type="file" @change="uploadImg" accept="image/*" /><i
                class="fa fa-camera"
              ></i>
            </label>
          </div>
          <div class="publish" @click="saveComment"><button>发布</button></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Comment",
  props: {
    category: String,
    target_id: String,
  }, // props is like parameter but for component
  data() {
    return {
      isLogin: false,
      name: "",

      commentArr: [],
      showComment: false,
      userRating: 0,
      hoverRating: 0, // temporary rating
      isHalf: false,

      commentText: "",
      commentImg: "",
    };
  },
  created() {
    this.user = this.$getSessionStorage("user");
    // check if login
    if (this.user != null) {
      this.isLogin = true;
    }

    let categoryName;
    switch (this.category) {
      case "1":
        categoryName = "Attraction";
        break;
      case "2":
        categoryName = "Restaurant";
        break;
      case "3":
        categoryName = "Hotel";
    }

    this.$axios
      .get(`${categoryName}Controller/get${categoryName}ById/${this.target_id}`)
      .then((response) => {
        this.name = response.data.result.name;
      })
      .catch((error) => console.error(error));

    // get comment list with category and target id
    this.$axios
      .get(
        `CommentController/listCommentByTargetId/${this.category}/${this.target_id}`
      )
      .then((response) => {
        this.commentArr = response.data.result;

        // fetch user detail for each comment
        this.commentArr.forEach((comment, index) => {
          this.$axios
            .get(`UserController/getUserById/${comment.userId}`)
            .then((response) => {
              // direct assignment
              comment.username = response.data.result.username;
              comment.userImg = response.data.result.userImg;
              comment.createT = comment.createT.split("T")[0];
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
    hoverStarEvent(index, event) {
      const starWidth = event.target.offsetWidth; // star width
      const mouseX = event.offsetX; // mouse position inside star
      this.isHalf = mouseX < starWidth / 2; // if mouse is in left half, make half-star
      this.hoverRating = this.isHalf ? index - 0.5 : index;
    },
    resetHover() {
      this.hoverRating = this.userRating; // Reset hover effect when mouse leaves
    },
    setRating(index) {
      this.userRating = this.hoverRating; // save rating when clicked
    },

    uploadImg(event) {
      const file = event.target.files[0]; //get first selected file
      if (!file) return;

      const reader = new FileReader();
      reader.readAsDataURL(file); //converts file into base64 string
      reader.onload = () => {
        this.commentImg = reader.result;
      };
    },

    saveComment() {
      if (this.userRating == 0) {
        alert("评分不能为空");
        return;
      }
      if (this.commentText == "") {
        alert("评论内容不能为空");
        return;
      }
      this.$axios
        .post(
          `CommentController/saveComment/${this.user.userId}/${this.category}/${this.target_id}/${this.userRating}/${this.commentText}`,
          { commentImg: this.commentImg }
        ) //send image as JSON body because too big
        .then((response) => {
          console.log(response.data.message);

          alert("发布成功");
          this.$router.go();
        })
        .catch((error) => {
          console.error(error);
        });
    },
  },
};
</script>

<style scoped>
/*************** comment *****************/
.wrapper .container .room .comment-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.wrapper .container .room .comment-header .header {
  display: flex;
  align-items: flex-end;
  font-size: 2vw;
}
.wrapper .container .room .comment-header .header p {
  font-size: 1.5vw;
  margin-left: 0.5vw;
}
.wrapper .container .room .comment-header .button {
  width: 8vw;
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
.wrapper .container .room .comment {
  margin: 1vw 0 2vw;
}
.wrapper .container .room .comment > li {
  padding: 2vw;
  border-radius: 1vw;
  box-shadow: 2px 2px 3px #c3c3c3;
  margin-bottom: 1vw;
}
.wrapper .container .room .comment li .header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 1vw;
}
.wrapper .container .room .comment li .header .user {
  display: flex;
}
.wrapper .container .room .comment li .header .user img {
  width: 5vw;
  height: 5vw;
  border-radius: 0.5vw;
  object-fit: cover;
}
.wrapper .container .room .comment li .header .user .name {
  margin-left: 1vw;
  display: flex;
  flex-direction: column;
}
.wrapper .container .room .comment li .header .user .name p {
  font-size: 1.5vw;
}
.wrapper .container .room .comment li .header .user .name .star {
  color: var(--color-text2);
  font-size: 1.4vw;
}
.wrapper .container .room .comment li .header .comment-date {
  font-size: 1vw;
  color: var(--color-text2);
  font-weight: normal;
}
.wrapper .container .room .comment li .body {
  display: flex;
  justify-content: space-between;
}
.wrapper .container .room .comment li .body .comment-text {
  font-size: 1.25vw;
  font-weight: normal;
  margin-bottom: 1vw;
}
.wrapper .container .room .comment li .body .comment-img {
  display: flex;
  margin-right: 8vw;
}
.wrapper .container .room .comment li .body .comment-img img {
  height: 8vw;
  width: 8vw;
  border-radius: 0.5vw;
  object-fit: cover;
  cursor: pointer;
}

/*************** comment box *****************/
.wrapper .container .comment-box {
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
.wrapper .container .comment-box .comment-content {
  background: white;
  width: 40vw;
  border-radius: 1vw;
  padding: 2vw;
}
.wrapper .container .comment-box .comment-content .header {
  display: flex;
  justify-content: space-between;
}
.wrapper .container .comment-box .comment-content .header p {
  font-size: 1.5vw;
}
.wrapper .container .comment-box .comment-content .header i {
  cursor: pointer;
  font-size: 2vw;
}
.wrapper .container .comment-box .comment-content .rating {
  margin: 1vw 0;
  display: flex;
  justify-content: center;
  align-items: center;
}
.wrapper .container .comment-box .comment-content .rating p {
  font-size: 1.2vw;
  margin-right: 1vw;
  font-weight: normal;
}
.wrapper .container .comment-box .comment-content .rating .star {
  align-content: center;
  color: var(--color-text2);
}
.wrapper .container .comment-box .comment-content .rating .star i {
  margin-right: 0.5vw;
  font-size: 1.4vw;
  cursor: pointer;
}
.wrapper .container .comment-box .comment-content .text textarea {
  padding: 1vw;
  box-sizing: border-box;
  font-size: 1.2vw;
  width: 100%;
  height: 10vw;
  border-radius: 1vw;
  border: 1px solid #c3c3c3;
  outline: none;
  resize: none;
}
.wrapper .container .comment-box .comment-content .img {
  margin: 1vw 0;
  display: flex;
  justify-content: center;
}
.wrapper .container .comment-box .comment-content .img img {
  width: 5vw;
  height: 5vw;
  border-radius: 1vw;
  object-fit: cover;
  margin-right: 1vw;
}
.wrapper .container .comment-box .comment-content .img .img-box {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 5vw;
  height: 5vw;
  border-radius: 1vw;
  border: 1px solid #c3c3c3;
  cursor: pointer;
}
.wrapper
  .container
  .comment-box
  .comment-content
  .img
  .img-box
  input[type="file"] {
  display: none;
}
.wrapper .container .comment-box .comment-content .img .img-box i {
  font-size: 1.5vw;
  color: #c3c3c3;
}
.wrapper .container .comment-box .comment-content .publish {
  display: flex;
  justify-content: center;
}
.wrapper .container .comment-box .comment-content .publish button {
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

/*************** button transition *****************/
.wrapper .container .room .comment-header .button::before,
.wrapper .container .comment-box .comment-content .publish button::before {
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

.wrapper .container .room .comment-header .button:hover::before,
.wrapper
  .container
  .comment-box
  .comment-content
  .publish
  button:hover::before {
  transform: translateX(0);
}
</style>
