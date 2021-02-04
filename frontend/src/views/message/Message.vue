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
            @click="
              modalShowMethod(
                message.mtitle,
                message.mcontent,
                message.msender,
                message.mno
              )
            "
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
        <b-modal id="reply" centered title="쪽지쓰기" @ok="replyMessage()">
          제목 : <input type="text" v-model="newMtitle"/>
          <hr />
          <b-form-textarea
            id="textarea-rows"
            rows="8"
            v-model="newMcontent"
          ></b-form-textarea
        ></b-modal>
        <b-modal
          id="delete"
          centered
          title="쪽지를 삭제하시겠습니까?"
          @ok="delMessage(mno)"
        ></b-modal>
        <b-modal v-model="modalShow" centered hide-footer :title="mtitle">
          <p>보낸사람 : {{ msender }}</p>
          <hr />
          <p>내용 : {{ mcontent }}</p>
          <b-button style="margin-right:15px" v-b-modal.reply>
            답장
          </b-button>

          <b-button v-b-modal.delete>
            삭제
          </b-button>
        </b-modal>
      </template>
    </v-simple-table>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  props: ['uno'],
  data() {
    return {
      messages: Array,
      modalShow: false,
      mtitle: '',
      mcontent: '',
      msender: '',
      msenderUno: '',
      mno: '',
      newMtitle: '',
      newMcontent: '',
    };
  },
  created() {
    this.getMessages();
  },
  methods: {
    modalShowMethod(title, content, sender, mno) {
      this.modalShow = !this.modalShow;
      if (this.modalShow) {
        this.mtitle = title;
        this.mcontent = content;
        this.msenderUno = sender;
        this.mno = mno;
        var params = new URLSearchParams();
        params.append('uno', sender);
        axios
          .post('http://localhost:8080/findUnameByUno', params)
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

      params.append('uno', this.msenderUno);
      axios
        .post('http://localhost:8080/findEmailByUno', params)
        .then((response) => {
          params = new URLSearchParams();
          params.append('email', storage.getItem('user-email'));
          params.append('friendEmail', response.data.data);
          params.append('mtitle', this.newMtitle);
          params.append('mcontent', this.newMcontent);
          axios
            .post('http://localhost:8080/sendMessage', params)
            .then((resp) => {
              console.log(resp);
              this.getMessages();
              alert('쪽지를 보냈습니다.');
              this.newMcontent = '';
              this.newMtitle = '';
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
      params.append('mno', mno);
      axios
        .post('http://localhost:8080/delMessage', params)
        .then((response) => {
          console.log(response);
          this.getMessages();
          alert('쪽지를 삭제했습니다.');
          this.modalShow = !this.modalShow;
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
