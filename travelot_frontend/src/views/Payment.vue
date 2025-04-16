<template>
  <div class="wrapper">
    <Nav2></Nav2>
    <div class="container">
      <div class="box">
        <div class="header">
          <div></div>
          <p>{{ orderDetail.name }}</p>
        </div>
        <div class="order">
          <div class="order-info">
            <div class="order-info-name">
              <h3>{{ orderDetail.subName }}</h3>
            </div>
            <div class="order-info-price">
              <div class="quantity">x {{ orderDetail.quantity }}</div>
              <div class="price">¥{{ orderDetail.price }}</div>
            </div>
          </div>
          <div class="order-desc">
            <i v-if="orderDetail.category == 1" class="fa fa-user"></i>
            <i v-if="orderDetail.category == 3" class="fa fa-hotel"></i>
            <p>{{ orderDetail.desc }}</p>
          </div>
          <div class="order-date">
            <div class="date">
              <i class="fa fa-calendar"></i>
              <!-- show one date if atttraction, show two date if hotel -->
              <p v-if="orderDetail.category == 1">
                预订日期：{{ orderDetail.startT }}
              </p>
              <p v-if="orderDetail.category == 3">
                入住日期：{{ orderDetail.startT }}
              </p>
              <p v-if="orderDetail.category == 3">
                退房日期：{{ orderDetail.endT }}
              </p>
            </div>
            <div v-if="orderDetail.category == 3" class="night">
              <p>共 {{ orderDetail.night }} 晚</p>
            </div>
          </div>
          <div class="order-reminder">
            <i class="fa fa-info"></i>
            <p class="t1">不可退款</p>
            <p v-if="orderDetail.category == 1">无需取票</p>
            <p v-if="orderDetail.category == 3">立即出票</p>
          </div>
          <div class="total">
            <p>订单总额：</p>
            <p class="total-price">¥{{ order.total }}</p>
          </div>
        </div>
        <ul class="payment-method">
          <h3>支付方式</h3>
          <li>
            <div>
              <img src="../assets/alipay.jpg" />
              <p>支付宝</p>
            </div>
            <input type="radio" name="method" checked />
          </li>
          <li>
            <div>
              <img src="../assets/wechat.jpg" />
              <p>微信支付</p>
            </div>
            <input type="radio" name="method" />
          </li>
          <li>
            <div>
              <img src="../assets/szrmb.jpg" />
              <p>数字人民币</p>
            </div>
            <input type="radio" name="method" />
          </li>
        </ul>
        <div class="pay" @click="toPay"><button>支 付</button></div>
        <div class="delete" @click="openDelete">
          <i class="fa fa-trash"></i>
          <p>取消订单</p>
        </div>
      </div>
    </div>

    <div v-if="showDelete" class="delete-box">
      <div class="delete-content">
        <p>确定是否取消订单？</p>
        <div class="buttons">
          <button class="confirm" @click="toDeleteOrder">确认</button>
          <button class="back" @click="closeDelete">返回</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Nav2 from "../components/Nav2.vue";
export default {
  name: "Payment",
  data() {
    return {
      orderId: this.$route.query.id,
      order: "",
      orderDetail: "",
      showDelete: false,
    };
  },
  created() {
    // get order
    this.$axios
      .get(`OrdersController/getOrdersById/${this.orderId}`)
      .then((response) => {
        this.order = response.data.result;
      })
      .catch((error) => {
        console.error(error);
      });

    // get order detail
    this.$axios
      .get(`OrdersController/getOrderDetailById/${this.orderId}`)
      .then((response) => {
        this.orderDetail = response.data.result;
        this.orderDetail.startT = this.orderDetail.startT.split("T")[0];
        if (this.orderDetail.endT) {
          this.orderDetail.endT = this.orderDetail.endT.split("T")[0];
          // calculate night
          this.orderDetail.night = this.calNight(
            this.orderDetail.startT,
            this.orderDetail.endT
          );
        }

        // get order name
        let categoryName, subCategoryName;
        switch (this.orderDetail.category) {
          case 1:
            categoryName = "Attraction";
            subCategoryName = "Ticket";
            break;
          case 3:
            categoryName = "Hotel";
            subCategoryName = "Room";
        }

        this.$axios
          .get(
            `${categoryName}Controller/get${categoryName}ById/${this.orderDetail.targetId}`
          )
          .then((response) => {
            this.orderDetail.name = response.data.result.name;
          })
          .catch((error) => console.error(error));

        this.$axios
          .get(
            `${categoryName}Controller/get${subCategoryName}ById/${this.orderDetail.targetSubId}`
          )
          .then((response) => {
            // get sub name, desc and price
            this.orderDetail.subName = response.data.result.name;
            this.orderDetail.price = response.data.result.price;
            if (this.orderDetail.category == 1) {
              this.orderDetail.desc = response.data.result.desc;
            } else {
              this.orderDetail.desc = response.data.result.type;
            }
          })
          .catch((error) => console.error(error));
      })
      .catch((error) => {
        console.error(error);
      });
  },
  components: {
    Nav2,
  },
  methods: {
    calNight(startDate, endDate) {
      const start = new Date(startDate);
      const end = new Date(endDate);
      const difference = (end - start) / (1000 * 60 * 60 * 24); // convert milliseconds to days
      return difference > 0 ? difference : 0;
    },

    toPay() {
      // pay order
      this.$axios
        .post(`OrdersController/updateOrdersById/${this.orderId}/1`)
        .then((response) => {
          // payment successful
          if (response.data.result > 0) {
            alert("支付成功");
            console.log(response.data.message);
            this.$router.push({ path: "/user/orderList" });
          } else {
            alert("支付失败");
          }
        })
        .catch((error) => {
          console.error(error);
        });
    },

    openDelete() {
      this.showDelete = true;
    },
    closeDelete() {
      this.showDelete = false;
    },

    toDeleteOrder() {
      // delete order
      this.$axios
        .post(`OrdersController/updateOrdersById/${this.orderId}/-1`)
        .then((response) => {
          // delete successful
          if (response.data.result > 0) {
            alert("取消订单成功");
            console.log(response.data.message);
            this.$router.push({ path: "/user/orderList" });
          } else {
            alert("取消订单失败");
          }
        })
        .catch((error) => {
          console.error(error);
        });
    },
  },
};
</script>

<style scoped>
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
  padding: 2vw 0;
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}
.wrapper .container .box .header {
  display: flex;
  justify-content: center;
}
.wrapper .container .box .header p {
  font-size: 1.5vw;
}
.wrapper .container .box .header i {
  cursor: pointer;
  font-size: 2vw;
}
.wrapper .container .box .order {
  margin: 2vw 0 1vw;
  border: 0.1vw #c3c3c3;
  border-style: solid none;
  padding: 1vw 2vw;
}

.wrapper .container .box .order p {
  font-size: 1.2vw;
  font-weight: normal;
}
.wrapper .container .box .order .order-info {
  display: flex;
  justify-content: space-between;
  margin-bottom: 1vw;
  align-items: center;
}
.wrapper .container .box .order .order-info h3 {
  font-size: 1.5vw;
  font-weight: 700;
  margin-bottom: 0.5vw;
}
.wrapper .container .box .order .order-info .order-info-price {
  display: flex;
  font-size: 1.5vw;
}
.wrapper .container .box .order .order-info .order-info-price .price {
  margin-left: 1vw;
  font-weight: normal;
  color: var(--color-text3);
}
.wrapper .container .box .order .order-info .order-info-price .quantity {
  font-weight: normal;
}
.wrapper .container .box .order .order-reminder,
.wrapper .container .box .order .order-desc,
.wrapper .container .box .order .order-date {
  display: flex;
  align-items: center;
  margin-bottom: 1vw;
}
.wrapper .container .box .order .order-date {
  justify-content: space-between;
}
.wrapper .container .box .order .order-date .date {
  display: flex;
  align-items: center;
}
.wrapper .container .box .order .order-reminder p,
.wrapper .container .box .order .order-desc p,
.wrapper .container .box .order .order-date p {
  margin-left: 1vw;
}
.wrapper .container .box .order .order-desc i,
.wrapper .container .box .order .order-date i {
  font-size: 1.5vw;
  width: 1.5vw;
}
.wrapper .container .box .order .order-reminder .t1 {
  color: var(--color-text3);
}
.wrapper .container .box .order .order-reminder i {
  border-radius: 10vw;
  border: solid 0.1vw var(--color-text);
  padding: 0.2vw 0.5vw;
  font-size: 1vw;
}
.wrapper .container .box .order .total {
  display: flex;
  align-items: center;
  justify-self: flex-end;
  font-size: 1.5vw;
}
.wrapper .container .box .order .total .total-price {
  color: var(--color-text3);
  font-weight: 700;
  font-size: 1.5vw;
}
.wrapper .container .box .payment-method {
  padding: 0 2vw;
  margin-bottom: 1vw;
}
.wrapper .container .box .payment-method h3 {
  font-size: 1.5vw;
  margin-bottom: 1vw;
}
.wrapper .container .box .payment-method li {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin: 0.5vw 0;
}
.wrapper .container .box .payment-method li div {
  display: flex;
  align-items: center;
}
.wrapper .container .box .payment-method li div img {
  width: 4vw;
  height: 4vw;
  border-radius: 1vw;
}
.wrapper .container .box .payment-method li div p {
  margin-left: 2vw;
  font-size: 1.25vw;
  font-weight: normal;
}
.wrapper .container .box .payment-method li input[type="radio"] {
  height: 1.5vw;
  width: 1.5vw;
  cursor: pointer;
  accent-color: var(--color-text);
}
.wrapper .container .box .pay {
  display: flex;
  justify-content: center;
}
.wrapper .container .box .pay button {
  width: 14vw;
  height: 4vw;
  background-color: var(--color-orange);
  border: none;
  outline: none;
  border-radius: 0.5vw;
  font-size: 1.5vw;
  font-weight: bold;
  color: white;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  z-index: 1;
}
.wrapper .container .box .delete {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 0 2vw;
  font-size: 1.25vw;
  color: #c3c3c3;
  cursor: pointer;
}
.wrapper .container .box .delete i {
  margin-right: 1vw;
}

/*************** order box *****************/
.wrapper .delete-box {
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
.wrapper .delete-box .delete-content {
  background: white;
  width: 40vw;
  border-radius: 1vw;
  padding: 4vw 2vw;
}
.wrapper .delete-box .delete-content p {
  text-align: center;
  font-size: 1.5vw;
  margin-bottom: 3vw;
}
.wrapper .delete-box .delete-content .buttons {
  padding: 0 6vw;
  display: flex;
  justify-content: space-between;
}
.wrapper .delete-box .delete-content .buttons button {
  width: 10vw;
  height: 4vw;
  border: none;
  outline: none;
  border-radius: 0.5vw;
  font-size: 1.5vw;
  font-weight: bold;
  color: white;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  z-index: 1;
}
.wrapper .delete-box .delete-content .buttons .confirm {
  background-color: var(--color-orange);
}
.wrapper .delete-box .delete-content .buttons .back {
  background-color: #c3c3c3;
}

/*************** button transition *****************/
.wrapper .container .box .pay button::before,
.wrapper .delete-box .delete-content .buttons .confirm::before,
.wrapper .delete-box .delete-content .buttons .back::before {
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
.wrapper .delete-box .delete-content .buttons .back::before {
  background-color: #a8a8a8;
}
.wrapper .container .box .pay button:hover::before,
.wrapper .delete-box .delete-content .buttons .confirm:hover::before,
.wrapper .delete-box .delete-content .buttons .back:hover::before {
  transform: translateX(0);
}
</style>
