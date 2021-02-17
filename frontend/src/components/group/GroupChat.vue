<template>
  <v-container style="margin-bottom:40px;">
    <hr />
    <div v-for="(chat, idx) in chats" :key="idx">
      <br />
      <v-row
        id="wow"
        :class="chat.style"
        :justify="chat.uno == gcuno ? 'end' : 'start'"
        no-gutters
        ><div class="hr-date" v-show="chat.IsDateChange">
          {{ chat.gcdate.substring(0, 4) }}년
          {{ chat.gcdate.substring(5, 7) }}월
          {{ chat.gcdate.substring(8, 10) }}일
        </div>
        <v-col cols="8">
          <!-- <v-row :justify="chat.uno == gcuno ? 'end' : 'start'" no-gutters> -->
          <div style="float:right" v-if="chat.style == 'myStyle'">
            <!-- <h5>{{ chat.uname }}</h5> -->

            <span
              style="
              font-size:13px; 
              position:absolute; 
              display:inline-block; 
              margin-right:15px; 
              bottom:0; 
              color:black"
            >
              {{ chat.parsedDate }}
            </span>
            <div class="speech-bubble-right">
              <h5>{{ chat.gccontent }}</h5>
            </div>
          </div>
          <div v-else>
            <h5 style="color:black">{{ chat.uname }}</h5>
            <div class="speech-bubble-left">
              <h5>{{ chat.gccontent }}</h5>
            </div>
            <span
              style="
              display:inline-block;
              color:black;
              position:absolute;
              bottom:0;
              font-size:13px;
              margin-left:5px"
            >
              {{ chat.parsedDate }}
            </span>
          </div>

          <!-- <h5>{{ chat.uname }}</h5>

          <div
            :class="[
              { 'speech-bubble-left': !LorR },
              { 'speech-bubble-right': LorR },
            ]"
          >
            <h5>{{ chat.gccontent }}</h5>
          </div>
          <span
            style="display:inline-block; font-size:13px;margin-bottom:0px; margin-left:5px"
          >
            {{ chat.parsedDate }}
          </span> -->

          <!-- <v-row
            :justify="chat.uno == gcuno ? 'end' : 'start'"
            no-gutters
            style="font-size: 1rem; letter-spacing: -1px; margin: 0;"
          >
            {{ chat.uname }}
          </v-row>
          <v-row
            :justify="chat.uno == gcuno ? 'end' : 'start'"
            no-gutters
            style="font-size: 1.3rem; letter-spacing: -1px; margin: 0;"
          >
            {{ chat.gccontent }}
          </v-row>
          <v-row
            :justify="chat.uno == gcuno ? 'end' : 'start'"
            no-gutters
            style="font-size: 0.8rem; letter-spacing: -1px; margin: 0;"
            >{{ chat.parsedDate }}</v-row
          > -->
          <!-- </v-row> -->
        </v-col>
      </v-row>
    </div>

    <v-row style="position:fixed;width:100%; bottom:68px">
      <input
        style="border:1px solid #d5d1d6; background-color:#d5d1d6; width:80%"
        type="text"
        v-model="gccontent"
        placeholder="보낼 메세지"
        @keyup.enter="sendMessage"
      />
      <v-btn style="width:20%" @click="sendMessage">SEND</v-btn>
    </v-row>
  </v-container>
</template>

<script>
import axios from "axios";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

const storage = window.sessionStorage;

export default {
  data() {
    return {
      memberStatus: this.$store.getters.getMemberStatus,
      gcgno: this.$store.getters.getGno,
      gcno: "",
      gcuno: "",
      gcdate: "",
      parsedDate: "",
      gccontent: "",
      uname: "",
      chats: [],
      stompClient: null,
      IsDateChange: false,
      isLoadingUser: true,
      isLoadingChatList: true,
    };
  },
  updated() {
    this.$nextTick(function() {
      document.documentElement.scrollTop = document.body.scrollHeight;
    });
  },

  created() {
    console.log("처음화면높이" + document.body.scrollHeight);
    this.$nextTick(function() {
      document.documentElement.scrollTop = document.body.scrollHeight;
    });
    axios
      .post("/findUserByEmail", { email: storage.getItem("user-email") })
      .then((response) => {
        if (response.data["is-success"]) {
          this.gcuno = response.data["user-number"];
          this.uname = response.data["user-name"];
          this.isLoadingUser = false;
        } else {
          alert("너 누구야");
        }
      });

    axios({
      method: "get",
      url: "/getChat/" + this.gcgno,
      baseURL: "http://localhost:8080/",
    }).then(
      (response) => {
        this.chats = [];
        console.log(response);
        let chatList = response.data["chat-list"];
        let unameList = response.data["uname-list"];

        for (let i = 0; i < chatList.length; i++) {
          let chat = {
            uno: chatList[i].gcuno,
            uname: unameList[i],
            gccontent: chatList[i].gccontent,
            gcdate: chatList[i].gcdate,
            parsedDate: chatList[i].gcdate.substring(11, 16),
            style: chatList[i].gcuno == this.gcuno ? "myStyle" : "yourStyle",
          };

          if (this.gcdate != chatList[i].gcdate.substring(5, 10)) {
            this.gcdate = chatList[i].gcdate.substring(5, 10);
            chat.IsDateChange = true;
          } else {
            chat.IsDateChange = false;
          }

          this.chats.push(chat);
          this.isLoadingChatList = false;
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
        alert("error : 새로고침하세요");
      }
    );

    this.connect();
  },
  methods: {
    aa() {
      alert(this.memberStatus + " " + this.gno);
    },
    sendMessage(e) {
      if (this.gccontent !== "") {
        this.send();
        this.gccontent = "";
      }
      this.$nextTick(function() {
        document.documentElement.scrollTop = document.body.scrollHeight + 100;
      });
    },
    send() {
      console.log("Send message:" + this.gccontent);
      console.log("group ID: " + this.gcgno);

      if (this.stompClient && this.stompClient.connected) {
        const chat = {
          gcgno: this.gcgno,
          gcuno: this.gcuno,
          gcuname: this.uname,
          gccontent: this.gccontent,
        };
        this.stompClient.send("/pub/chat", JSON.stringify(chat), {});
      }
    },
    connect() {
      const serverURL = "http://localhost:8080/ws";
      let socket = new SockJS(serverURL);
      let tmp = {};

      this.stompClient = Stomp.over(socket);
      this.stompClient.connect(
        {},
        (frame) => {
          // 소켓 연결 성공
          this.connected = true;
          console.log("소켓 연결 성공", frame);
          this.stompClient.subscribe("/sub/" + this.gcgno, (response) => {
            console.log("구독으로 받은 메시지 입니다???.", response.body);
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
            console.log("★★★★★★★★★★★★★", parseTmp);
            tmp = {
              uno: parseTmp.gcuno,
              uname: parseTmp.gcuname,
              gccontent: parseTmp.gccontent,
              gcdate: parseTmp.gcdate,
              parsedDate: parseTmp.gcdate.substring(11, 16),
              style: parseTmp.gcuno == this.gcuno ? "myStyle" : "yourStyle",
            };

            this.chats.push(tmp);
          });
        },
        (error) => {
          // 소켓 연결 실패
          console.log("소켓 연결 실패", error);
          this.connected = false;
        }
      );
    },
    handleScroll() {
      this.btnShow = window.scrollY > 400;
    },
  },
  beforeMount() {
    window.addEventListener("scroll", this.handleScroll);
  },
  beforeDestroy() {
    window.removeEventListener("scroll", this.handleScroll);
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
      console.log("처음화면높이" + document.body.scrollHeight);
      this.$nextTick(function() {
        document.documentElement.scrollTop = document.body.scrollHeight;
      });
      axios
        .post("/findUserByEmail", { email: storage.getItem("user-email") })
        .then((response) => {
          if (response.data["is-success"]) {
            this.gcuno = response.data["user-number"];
            this.uname = response.data["user-name"];
            this.isLoadingUser = false;
          } else {
            alert("너 누구야");
          }
        });

      axios({
        method: "get",
        url: "/getChat/" + this.gcgno,
        baseURL: "http://localhost:8080/",
      }).then(
        (response) => {
          this.chats = [];
          console.log(response);
          let chatList = response.data["chat-list"];
          let unameList = response.data["uname-list"];

          for (let i = 0; i < chatList.length; i++) {
            let chat = {
              uno: chatList[i].gcuno,
              uname: unameList[i],
              gccontent: chatList[i].gccontent,
              gcdate: chatList[i].gcdate,
              parsedDate: chatList[i].gcdate.substring(11, 16),
              style: chatList[i].gcuno == this.gcuno ? "myStyle" : "yourStyle",
            };

            if (this.gcdate != chatList[i].gcdate.substring(5, 10)) {
              this.gcdate = chatList[i].gcdate.substring(5, 10);
              chat.IsDateChange = true;
            } else {
              chat.IsDateChange = false;
            }

            this.chats.push(chat);
            this.isLoadingChatList = false;
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
          alert("error : 새로고침하세요");
        }
      );
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
    isLoadingUser(isLoadingUser) {
      console.log("watch에서 바뀌는지" + isLoadingUser);
      console.log("watch에서 바뀌는지" + this.isLoadingChatList);
      console.log(document.body.scrollHeight);
      if (!isLoadingUser && !this.isLoadingChatList) {
        this.$nextTick(function() {
          document.body.scrollTop = document.body.scrollHeight;
          console.log(document.body.scrollHeight);
        });
      }
    },
    isLoadingChatList(isLoadingChatList) {
      console.log("watch에서 바뀌는지" + isLoadingChatList);
      console.log("watch에서 바뀌는지" + this.isLoadingUser);
      console.log("전" + document.getElementById("scrollBody").scrollHeight);
      if (!isLoadingChatList && !this.isLoadingUser) {
        this.$nextTick(function() {
          document.documentElement.scrollTop = document.body.scrollHeight;
          console.log(
            "후" + document.getElementById("scrollBody").scrollHeight
          );
        });
      }
    },
  },
};
</script>

<style scoped>
.myStyle {
  color: black;
}
.yourStyle {
  color: black;
}
.speech-bubble-left {
  margin-left: 15px;
  padding: 5px;
  position: relative;
  background: #c7dcf0;
  border-radius: 0.4em;
  display: inline-block;
  max-width: 200px;
}

.speech-bubble-left:after {
  content: "";
  position: absolute;
  left: 0;
  top: 50%;
  width: 10;
  height: 0;
  border: 15px solid transparent;
  border-right-color: #c7dcf0;
  border-left: 0;
  border-top: 0;
  margin-top: -13.5px;
  margin-left: -15px;
}
.speech-bubble-right {
  margin-left: 40px;
  padding: 5px;
  position: relative;
  background: #ebccf3;
  border-radius: 0.4em;
  display: inline-block;
  max-width: 200px;
}

.speech-bubble-right:after {
  content: "";
  position: absolute;
  right: 0;
  top: 50%;
  width: 10;
  height: 0;
  border: 15px solid transparent;
  border-left-color: #ebccf3;
  border-right: 0;
  border-top: 0;
  margin-top: -13.5px;
  margin-right: -15px;
}
.hr-date {
  display: flex;
  flex-basis: 100%;
  align-items: center;
  color: rgba(0, 0, 0, 0.35);
  font-family: Helvetica;
  font-size: 15px;
  margin: 8px 0px;
}
.hr-date::before,
.hr-date::after {
  content: "";
  flex-grow: 1;
  background: rgba(0, 0, 0, 0.35);
  height: 1px;
  font-size: 0px;
  line-height: 0px;
  margin: 0px 16px;
}
</style>
