<template>
  <div class="room">
    <input
      type="text"
      v-model="gccontent"
      placeholder="보낼 메세지"
      size="100"
      @keyup.enter="sendMessage"
    />
    <v-btn @click="sendMessage">SEND</v-btn>
    <hr />
    <div v-for="(item, idx) in chats" :key="idx">
      <h3>유저이름: {{ item.gcuno }}</h3>
      <h3>내용: {{ item.gccontent }}</h3>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import Stomp from 'webstomp-client';
import SockJS from 'sockjs-client';

export default {
  data() {
    return {
      memberStatus: this.$store.getters.getMemberStatus,
      gcgno: this.$store.getters.getGno,
      gcno: '',
      gcuno: '',
      gcdate: '',
      gccontent: '',
      chats: [],
      stompClient: null,
    };
  },
  created() {
    this.gcuno = 5; // axios로 받아와야 함

    axios({
      method: 'get',
      url: '/getChat/' + this.gcgno,
      baseURL: 'http://localhost:8080/',
    }).then(
      (response) => {
        this.chats = [];
        console.log(response);
        for (let i = 0; i < response.data.length; i++) {
          let chat = {
            gcuno: response.data[i].gcuno,
            gccontent: response.data[i].gccontent,
            // 'senderNickname':res.data[i].senderNickname,
            // 'content':res.data[i].content,
            // 'style': res.data[i].senderId == this.id ? 'myMsg':'otherMsg'
          };
          this.chats.push(chat);
        }
      },
      (err) => {
        console.log(err);
        alert('error : 새로고침하세요');
      }
    );

    this.connect();
  },
  methods: {
    aa() {
      alert(this.memberStatus + ' ' + this.gno);
    },
    sendMessage(e) {
      if (this.gccontent !== '') {
        this.send();

        this.gccontent = '';
      }
    },
    send() {
      console.log('Send message:' + this.gccontent);
      console.log('group ID: ' + this.gcgno);
      if (this.stompClient && this.stompClient.connected) {
        const msg = {
          // gcno: 0, // ???
          gcgno: this.gcgno,
          gcuno: this.gcuno,
          gccontent: this.gccontent,
          // gcdate: new Date(),
        };
        console.log('Date...? ###################### ' + new Date());
        this.stompClient.send('/pub/chat', JSON.stringify(msg), {});
      }
    },
    connect() {
      const serverURL = 'http://localhost:8080/ws';
      let socket = new SockJS(serverURL);

      this.stompClient = Stomp.over(socket);
      // console.log('@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@2');
      // console.log(this.stompClient);
      // console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`);
      this.stompClient.connect(
        {},
        (frame) => {
          // 소켓 연결 성공
          this.connected = true;
          console.log('소켓 연결 성공', frame);
          this.stompClient.subscribe('/sub/' + this.gcgno, (res) => {
            console.log('구독으로 받은 메시지 입니다.', res.body);
            //   let jsonBody = JSON.parse(res.body)
            //    let m={
            //   'senderNickname':jsonBody.senderNickname,
            //   'content': jsonBody.content,
            //   'style': jsonBody.senderId == this.id ? 'myMsg':'otherMsg'
            // }
            // this.chats.push(m)
            // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
            this.chats.push(JSON.parse(res.body));
          });
        },
        (error) => {
          // 소켓 연결 실패
          console.log('소켓 연결 실패', error);
          this.connected = false;
        }
      );
    },
  },
  computed: {
    vuexGno() {
      return this.$store.getters.getGno;
    },
    vuexUno() {
      return this.$store.getters.getUno;
    },
    vuexBno() {
      return this.$store.getters.getBno;
    },
    vuexMemberStatus() {
      return this.$store.getters.getMemberStatus;
    },
  },
  watch: {
    vuexGno(val) {
      this.gno = val;
    },
    vuexUno(val) {
      this.uno = val;
    },
    vuexBno(val) {
      this.bno = val;
    },
    vuexMemberStatus(val) {
      this.memberStatus = val;
    },
  },
};
</script>

<style></style>
