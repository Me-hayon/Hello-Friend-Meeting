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
    <div v-for="(chat, idx) in chats" :key="idx">
      <h3>유저이름: {{ chat.uname }}</h3>
      <h3>내용: {{ chat.gccontent }}</h3>
      <h5>날짜: {{ chat.gcdate }}</h5>
      <hr />
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import Stomp from 'webstomp-client';
import SockJS from 'sockjs-client';

const storage = window.sessionStorage;

export default {
  data() {
    return {
      memberStatus: this.$store.getters.getMemberStatus,
      gcgno: this.$store.getters.getGno,
      gcno: '',
      gcuno: '',
      gcdate: '',
      gccontent: '',
      uname: '',
      chats: [],
      stompClient: null,
    };
  },
  created() {
    axios
      .post('/findUserByEmail', { email: storage.getItem('user-email') })
      .then((response) => {
        if (response.data['is-success']) {
          this.gcuno = response.data['user-number'];
          this.uname = response.data['user-name'];
          // console.log('★★★★★★★★★★★★★★★★★:', this.gcuno, this.uname);
        } else {
          alert('너 누구야');
        }
      });

    axios({
      method: 'get',
      url: '/getChat/' + this.gcgno,
      baseURL: 'http://localhost:8080/',
    }).then(
      (response) => {
        this.chats = [];
        console.log(response);
        let chatList = response.data['chat-list'];
        let unameList = response.data['uname-list'];

        for (let i = 0; i < chatList.length; i++) {
          let chat = {
            uno: chatList[i].gcuno,
            uname: unameList[i],
            gccontent: chatList[i].gccontent,
            gcdate: chatList[i].gcdate,
          };

          this.chats.push(chat);
        }

        // for (let i = 0; i < response.data.length; i++) {
        //   let chat = {
        //     gcuno: response.data[i].gcuno,
        //     gccontent: response.data[i].gccontent,
        //     // 'senderNickname':res.data[i].senderNickname,
        //     // 'content':res.data[i].content,
        //     // 'style': res.data[i].senderId == this.id ? 'myMsg':'otherMsg'
        //   };
        //   this.chats.push(chat);
        // }
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
        const chat = {
          gcgno: this.gcgno,
          gcuno: this.gcuno,
          gccontent: this.gccontent,
        };
        this.stompClient.send('/pub/chat', JSON.stringify(chat), {});
      }
    },
    connect() {
      const serverURL = 'http://localhost:8080/ws';
      let socket = new SockJS(serverURL);
      let tmp = {};

      this.stompClient = Stomp.over(socket);
      this.stompClient.connect(
        {},
        (frame) => {
          // 소켓 연결 성공
          this.connected = true;
          console.log('소켓 연결 성공', frame);
          this.stompClient.subscribe('/sub/' + this.gcgno, (response) => {
            console.log('구독으로 받은 메시지 입니다???.', response.body);
            //   let jsonBody = JSON.parse(res.body)
            //    let m={
            //   'senderNickname':jsonBody.senderNickname,
            //   'content': jsonBody.content,
            //   'style': jsonBody.senderId == this.id ? 'myMsg':'otherMsg'
            // }
            // this.chats.push(m)
            // 받은 데이터를 json으로 파싱하고 리스트에 넣어줍니다.
            // console.log(this.chats);
            // this.chats.push(JSON.parse(response.body));

            let parseTmp = JSON.parse(response.body);
            console.log('★★★★★★★★★★★★★', parseTmp.gccontent, parseTmp.gcdate);
            tmp = {
              uno: this.gcuno,
              uname: this.uname,
              gccontent: parseTmp.gccontent,
              gcdate: parseTmp.gcdate,
            };

            this.chats.push(tmp);
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
