<template>
  <div id="app">
    gcuno:
    <input v-model="gcuno" type="text" />
    gcno:
    <input v-model="gcno" type="text" />
    gcgno:
    <input v-model="gcgno" type="text" />
    gcdate:
    <input v-model="gcdate" type="text" />
    내용: <input v-model="gccontent" type="text" @keyup="sendMessage" />
    <div v-for="(item, idx) in recvList" :key="idx">
      <h3>유저이름: {{ item.gcuno }}</h3>
      <h3>내용: {{ item.gccontent }}</h3>
    </div>
  </div>
</template>

<script>
import Stomp from 'webstomp-client';
import SockJS from 'sockjs-client';

export default {
  data() {
    return {
      memberStatus: this.$store.getters.getMemberStatus,
      gno: this.$store.getters.getGno,
      gcno: '',
      gcgno: '',
      gcuno: '',
      gcdate: '',
      gccontent: '',
      recvList: [],
    };
  },
  created() {
    this.connect();
  },
  methods: {
    aa() {
      alert(this.memberStatus + ' ' + this.gno);
    },
    sendMessage(e) {
      if (e.keyCode === 13 && this.gcuno !== '' && this.gccontent !== '') {
        this.send();
        this.gccontent = '';
      }
    },
    send() {
      console.log('Send message:' + this.gccontent);
      if (this.stompClient && this.stompClient.connected) {
        const msg = {
          gcuno: this.gcuno,
          gccontent: this.gccontent,
        };
        this.stompClient.send('/receive', JSON.stringify(msg), {});
      }
    },
    connect() {
      const serverURL = 'http://localhost:8080';
      let socket = new SockJS(serverURL);

      this.stompClient = Stomp.over(socket);
      console.log('@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@2');
      console.log(this.stompClient);
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`);
      this.stompClient.connect(
        {},
        (frame) => {
          // 소켓 연결 성공
          this.connected = true;
          console.log('소켓 연결 성공', frame);
          // 서버의 메시지 전송 endpoint를 구독합니다.
          // 이런형태를 pub sub 구조라고 합니다.
          this.stompClient.subscribe('/send', (res) => {
            console.log('구독으로 받은 메시지 입니다.', res.body);

            // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
            this.recvList.push(JSON.parse(res.body));
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
