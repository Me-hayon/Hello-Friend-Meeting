<template>
  <div>
    <v-simple-table>
      <template v-slot:default>
        <thead>
          <tr>
            <th class="text-left">
              보낸사람
            </th>
            <th class="text-center">
              내용
            </th>
          </tr>
        </thead>
        <tbody>
          <tr
            @click="modalShow = !modalShow"
            v-for="message in messages"
            :key="message.name"
          >
            <td>{{ message.mtitle }}</td>
            <td>
              {{ message.mcontent }}
              <div style="float:right"></div>
            </td>
          </tr>
        </tbody>
        <b-modal
          id="reply"
          centered
          title="쪽지쓰기"
          @ok="replyMessage(message.msender)"
          ><b-form-textarea id="textarea-rows" rows="8"></b-form-textarea
        ></b-modal>
        <b-modal
          id="delete"
          centered
          title="쪽지를 삭제하시겠습니까?"
          @ok="delMessage(message.mno)"
        ></b-modal>
        <b-modal v-model="modalShow" centered hide-footer>
          <p>
            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Nisi
            laborum sint voluptate, hic corrupti quasi voluptates vero, maiores
            tempora fuga debitis quis molestiae ab laudantium esse cum deleniti
            quidem explicabo.
          </p>
          <b-button style="margin-right:15px" v-b-modal.reply>
            답장
          </b-button>

          <b-button v-b-modal.delete>
            삭제
          </b-button>
        </b-modal>
      </template>
    </v-simple-table>

    <!-- 답장은 모달로 하던가 -->
  </div>
</template>

<script>
import axios from "axios";
export default {
  props: ["uno"],
  data() {
    return {
      messages: Array,
      modalShow: false,
    };
  },
  created() {
    this.getMessages();
  },
  methods: {
    replyMessage(mno) {
      var params = new URLSearchParams();
      params.append("mno", mno);
      axios
        .post("http://localhost:8080/sendMessage", params)
        .then((response) => {
          console.log(response);
          this.getMessages();
          alert("쪽지를 삭제했했습니다.");
        })
        .catch((error) => {
          console.log(error);
        });
    },
    delMessage(mno) {
      var params = new URLSearchParams();
      params.append("mno", mno);
      axios
        .post("http://localhost:8080/delMessage", params)
        .then((response) => {
          console.log(response);
          this.getMessages();
          alert("쪽지를 삭제했했습니다.");
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getMessages() {
      var params = new URLSearchParams();
      var storage = window.sessionStorage;
      params.append("email", storage.getItem("user-email"));
      axios
        .post("http://localhost:8080/getMessages", params)
        .then((response) => {
          this.messages = response.data;
          console.log(response);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style></style>
