<template>
  <div class="wrapper">
    <div class="container">
      <div class="box">
        <p class="title">我的订单</p>
        <table class="info">
          <p>景点订单</p>
          <tbody>
            <tr>
              <th style="width: 7vw">订单编号</th>
              <th style="width: 20vw">门票详情</th>
              <th style="width: 16.5vw">预订日期</th>
              <th>总金额</th>
              <th style="padding-left: 1.1vw">订单状态</th>
              <th style="width: 12vw">订单创建时间</th>
            </tr>
            <tr v-for="ticket in ticketArr">
              <td>{{ ticket.orderId }}</td>
              <td style="padding-right: 1vw">
                {{ ticket.name }} - {{ ticket.subName }} x {{ ticket.quantity }}
              </td>
              <td>{{ ticket.startT }}</td>
              <td
                style="
                  padding-left: 0.5vw;
                  font-weight: 700;
                  color: var(--color-text3);
                "
              >
                ¥{{ ticket.total }}
              </td>
              <td
                v-if="ticket.status > 0"
                style="padding-left: 1.75vw; color: #c3c3c3"
              >
                已完成
              </td>
              <td v-else-if="ticket.status == 0">
                <button @click="toPayment(ticket.orderId)">去支付</button>
              </td>
              <td v-else style="padding-left: 1.75vw; color: #c3c3c3">
                已取消
              </td>
              <td style="color: #c3c3c3">{{ ticket.createT }}</td>
            </tr>
          </tbody>
        </table>
        <table class="info">
          <p>酒店订单</p>
          <tbody>
            <tr>
              <th style="width: 7vw">订单编号</th>
              <th style="width: 20vw">入住详情</th>
              <th style="width: 8vw">入住日期</th>
              <th style="width: 8vw">退房日期</th>
              <th>总金额</th>
              <th style="padding-left: 1.1vw">订单状态</th>
              <th style="width: 12vw">订单创建时间</th>
            </tr>
            <tr v-for="room in roomArr">
              <td>{{ room.orderId }}</td>
              <td style="padding-right: 1vw">
                {{ room.name }} - {{ room.subName }} x {{ room.quantity }}
              </td>
              <td>{{ room.startT }}</td>
              <td>{{ room.endT }}</td>
              <td
                style="
                  padding-left: 0.5vw;
                  font-weight: 700;
                  color: var(--color-text3);
                "
              >
                ¥{{ room.total }}
              </td>
              <td
                v-if="room.status > 0"
                style="padding-left: 1.75vw; color: #c3c3c3"
              >
                已完成
              </td>
              <td v-else-if="room.status == 0">
                <button @click="toPayment(room.orderId)">去支付</button>
              </td>
              <td v-else style="padding-left: 1.75vw; color: #c3c3c3">
                已取消
              </td>
              <td style="color: #c3c3c3">{{ room.createT }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "UserOrderList",
  data() {
    return {
      isLogin: false,
      user: {},
      orderArr: [],
      ticketArr: [],
      roomArr: [],
    };
  },
  created() {
    this.user = this.$getSessionStorage("user");
    // check if login
    if (this.user != null) {
      this.isLogin = true;
    }

    // get order list with user id
    this.$axios
      .get(`OrdersController/listOrdersByUserId/${this.user.userId}`)
      .then((response) => {
        this.orderArr = response.data.result;
        // sort order in descending order
        this.orderArr.sort((a, b) => new Date(b.createT) - new Date(a.createT));

        // get order detail for each order
        const detailPromise = this.orderArr.map((order) => {
          order.createT = order.createT.replace("T", " ");

          return Promise.all([
            this.$axios
              .get(`OrdersController/getOrderDetailById/${order.orderId}`)
              .then((response) => {
                const orderDetail = response.data.result;
                // copy properties to order
                order.quantity = orderDetail.quantity;
                order.startT = orderDetail.startT.split("T")[0];
                if (orderDetail.endT) {
                  order.endT = orderDetail.endT.split("T")[0];
                }
                order.category = orderDetail.category;

                // get order name
                let categoryName, subCategoryName;
                switch (orderDetail.category) {
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
                    `${categoryName}Controller/get${categoryName}ById/${orderDetail.targetId}`
                  )
                  .then((response) => {
                    order.name = response.data.result.name;
                  });

                this.$axios
                  .get(
                    `${categoryName}Controller/get${subCategoryName}ById/${orderDetail.targetSubId}`
                  )
                  .then((response) => {
                    order.subName = response.data.result.name;
                  });
              }),
          ]);
        });

        //split order into ticket and room (use Promise because need to wait category to be filled)
        Promise.all(detailPromise).then(() => {
          this.ticketArr = this.orderArr.filter((o) => o.category === 1);
          this.roomArr = this.orderArr.filter((o) => o.category === 3);
        });
      })
      .catch((error) => {
        console.error(error);
      });
  },
  methods: {
    toPayment(orderId) {
      this.$router.push({ path: "/payment", query: { id: orderId } });
    },
  },
};
</script>

<style scoped>
/*************** order box *****************/
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
.wrapper .container .box .title {
  font-size: 1.5vw;
  padding: 0 2vw 1vw;
}
.wrapper .container .box .info {
  padding: 1vw 2vw;
  border: 0.1vw #c3c3c3;
  border-style: solid none none;
}
.wrapper .container .box .info p {
  font-size: 1.25vw;
  margin-bottom: 1vw;
}
.wrapper .container .box .info tr th {
  text-align: left;
  font-size: 1.25vw;
}
.wrapper .container .box .info tr {
  height: 4vw;
}
.wrapper .container .box .info tr td {
  font-size: 1.15vw;
  font-weight: normal;
}

/*************** button transition *****************/
.wrapper .container .box .info tr td button {
  background-color: var(--color-orange);
  border: none;
  outline: none;
  border-radius: 0.5vw;
  width: 7vw;
  height: 2.75vw;
  font-size: 1.15vw;
  color: white;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  z-index: 1;
}
.wrapper .container .box .info tr td button::before {
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
.wrapper .container .box .info tr td button:hover::before {
  transform: translateX(0);
}
</style>
