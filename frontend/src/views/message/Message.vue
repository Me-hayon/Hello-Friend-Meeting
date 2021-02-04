<template>
  <div>
    <ul>
      <li v-for="message in messages" :key="message.mno">
        {{ message.mtitle }},{{ message.mcontent }}
        <button @click="replyMessage(message.msender)">reply</button>
        <button @click="delMessage(message.mno)">delete</button>
      </li>
    </ul>
    <!-- 답장은 모달로 하던가 -->
  </div>
</template>

<script>
import axios from 'axios';
export default {
  props: ['uno'],
  data() {
    return {
      messages: Array,
    };
  },
  created() {
    this.getMessages();
  },
  methods: {
    replyMessage(mno) {
      var params = new URLSearchParams();
      params.append('mno', mno);
      axios
        .post('http://localhost:8080/sendMessage', params)
        .then((response) => {
          console.log(response);
          this.getMessages();
          alert('쪽지를 삭제했했습니다.');
        })
        .catch((error) => {
          console.log(error);
        });
    },
    delMessage(mno) {
      var params = new URLSearchParams();
      params.append('mno', mno);
      axios
        .post('http://localhost:8080/delMessage', params)
        .then((response) => {
          console.log(response);
          this.getMessages();
          alert('쪽지를 삭제했했습니다.');
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getMessages() {
      var params = new URLSearchParams();
      var storage = window.sessionStorage;
      params.append('email', storage.getItem('user-email'));
      axios
        .post('http://localhost:8080/getMessages', params)
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
