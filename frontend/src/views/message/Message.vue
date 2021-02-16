<template>
  <div>
    <v-icon>mdi-magnify</v-icon>
    <input
      type="text"
      v-model="search"
      placeholder="보낸사람 검색"
      @input="handleSearchInput"
      @keydown.tab="KeydownTab"
    />

    <v-simple-table>
      <template v-slot:default>
        <thead style="background-color: #f9f5fe;">
          <tr>
            <th class="text-left">
              보낸사람
            </th>
            <th class="text-center ">
              내용
            </th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="message in searchList"
            :key="message.name"
            @click="
              modalShowMethod(
                message.mtitle,
                message.mcontent,
                message.msender,
                message.mno
              )
            "
          >
            <td :search="search">{{ message.msenderName }}</td>
            <td class="text-truncate" style="max-width: 150px;">
              {{ message.mcontent }}
              <div style="float:right"></div>
            </td>
          </tr>
        </tbody>
        <b-modal id="reply" centered title="쪽지쓰기" @ok="replyMessage()">
          제목
          <v-text-field
            full-width
            value="Re: Vacation Request"
            label="Subject"
            single-line
            v-model="newMtitle"
          ></v-text-field>

          <hr />
          <v-textarea
            id="textarea-rows"
            full-width
            single-line
            rows="8"
            label="Message"
            v-model="newMcontent"
          ></v-textarea
        ></b-modal>
        <b-modal
          id="delete"
          centered
          title="쪽지를 삭제하시겠습니까?"
          @ok="delMessage(mno)"
        ></b-modal>
        <b-modal v-model="modalShow" centered hide-footer :title="mtitle">
          <p><span style="font-weight: bold;">보낸사람</span> {{ msender }}</p>
          <hr />
          <p>{{ mcontent }}</p>
          <div style="float:right">
            <button class="btn-message" v-b-modal.reply>
              답장
            </button>

            <button class="btn-message" v-b-modal.delete>
              삭제
            </button>
          </div>
        </b-modal>
      </template>
    </v-simple-table>
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
      mtitle: "",
      mcontent: "",
      msender: "",
      msenderUno: "",
      mno: "",
      newMtitle: "",
      newMcontent: "",
      search: "",
      searchList: Array,
    };
  },
  created() {
    this.getMessages();
  },
  methods: {
    handleSearchInput(e) {
      this.search = e.target.value;
      if (this.search.length !== 0) {
        clearTimeout(this.debounce);
        this.debounce = setTimeout(() => {
          const filteredList = this.messages.filter((item) =>
            item.msenderName.includes(this.search)
          );
          this.searchList = filteredList;
          console.log("키키키", this.searchList);
        }, 100);
      } else {
        clearTimeout(this.debounce);
        this.debounce = setTimeout(() => {
          this.searchList = this.messages;
        }, 100);
      }
    },

    modalShowMethod(title, content, sender, mno) {
      this.modalShow = !this.modalShow;
      if (this.modalShow) {
        this.mtitle = title;
        this.mcontent = content;
        this.msenderUno = sender;
        this.mno = mno;
        var params = new URLSearchParams();
        params.append("uno", sender);
        axios
          .post("findUnameByUno", params)
          .then((response) => {
            this.msender = response.data.data;
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
    replyMessage() {
      var storage = window.sessionStorage;
      var params = new URLSearchParams();

      params.append("uno", this.msenderUno);
      if (this.newMtitle === "") {
        alert("제목을 입력해주세요.");
        return;
      }

      if (this.newMcontent === "") {
        alert("내용을 입력해주세요.");
        return;
      }
      axios
        .post("findEmailByUno", params)
        .then((response) => {
          params = new URLSearchParams();
          params.append("email", storage.getItem("user-email"));
          params.append("friendEmail", response.data.data);
          params.append("mtitle", this.newMtitle);
          params.append("mcontent", this.newMcontent);
          axios
            .post("sendMessage", params)
            .then((resp) => {
              console.log(resp);
              this.getMessages();
              alert("쪽지를 보냈습니다.");
              this.newMcontent = "";
              this.newMtitle = "";
            })
            .catch((err) => {
              console.log(err);
            });
        })
        .catch((error) => {
          console.log(error);
        });
    },
    delMessage(mno) {
      var params = new URLSearchParams();
      params.append("mno", mno);
      axios
        .post("delMessage", params)
        .then((response) => {
          console.log(response);
          this.getMessages();
          alert("쪽지를 삭제했습니다.");
          this.modalShow = !this.modalShow;
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
        .post("getMessages", params)
        .then((response) => {
          this.messages = response.data.messagesList;
          this.searchList = response.data.messagesList;

          var nameList = response.data.namesList;
          for (var i = 0; i < this.messages.length; i++) {
            this.messages[i].msenderName = nameList[i];
          }
          console.log(response);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style>
.btn-message {
  color: purple;
  border-radius: 20%;
  width: 80px;
  height: 40px;
  font-size: 18px;
  margin-right: 15px;
}
.btn-message:hover {
  background-color: #f9f5fe;
}
</style>
