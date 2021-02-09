<template>
  <div>
    <v-simple-table>
      <template v-slot:default>
        <tbody>
          <tr
            @click="boardDetail(item.bno)"
            style="height:100px"
            v-for="item in tableNotice"
            :key="item.bno"
          >
            <td>{{ item.btitle }}</td>
            <td>{{ item.bdate }}<br />{{ item.writerName }}</td>
          </tr>
          <tr
            @click="boardDetail(item.bno)"
            style="height:100px"
            v-for="item in table"
            :key="item.bno"
          >
            <td>{{ item.btitle }}</td>
            <td>{{ item.bdate }}<br />{{ item.writerName }}</td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
    <button v-b-modal.create-article class="create-article-btn">게시글+</button>
    <b-modal
      id="create-article"
      centered
      title="게시글작성"
      @ok="createArticle"
      @hide="resetDatas"
    >
      <b-row style="margin-bottom:10px">
        <b-col>
          제목 공지사항으로 설정<input
            type="checkbox"
            v-model="newIsNotice"
            :disabled="memberStatus != 4"
          />
        </b-col>
        <b-col><b-form-input v-model="newTitle"></b-form-input></b-col>
      </b-row>
      <b-form-textarea
        rows="8"
        style="margin-bottom:10px"
        v-model="newContent"
      ></b-form-textarea>
    </b-modal>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  computed: {
    vuexGno() {
      return this.$store.getters.getGno;
    },
    vuexMemberStatus() {
      return this.$store.getters.getMemberStatus;
    },
  },
  watch: {
    vuexGno(val) {
      this.gno = val;
      var params = new URLSearchParams();
      params.append('bgno', this.gno);
      axios
        .post('getBoardList', params)
        .then((response) => {
          this.table = response.data.notNotice;
          this.tableNotice = response.data.notice;
          for (var i = 0; i < this.table.length; i++) {
            this.table[i].writerName = response.data.notNoticeWriter[i];
          }
          for (i = 0; i < this.tableNotice.length; i++) {
            this.tableNotice[i].writerName = response.data.noticeWriter[i];
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
    vuexMemberStatus(val) {
      this.memberStatus = val;
    },
  },
  data() {
    return {
      memberStatus: this.$store.getters.getMemberStatus,
      gno: this.$store.getters.getGno,
      table: [],
      tableNotice: [],
      newTitle: '',
      newContent: '',
      newIsNotice: false,
    };
  },
  created() {
    this.getBoardList();
  },
  methods: {
    boardDetail(bno) {
      this.$store.commit('setBno', bno);
      this.$router.push('/board/detail');
    },
    createArticle() {
      var params = new URLSearchParams();
      params.append('email', window.sessionStorage.getItem('user-email'));
      params.append('bgno', this.gno);
      params.append('title', this.newTitle);
      params.append('content', this.newContent);
      params.append('bisNotice', this.newIsNotice);

      axios.post('writeBoard', params).then((resp) => {
        alert(resp.data.data);
        this.getBoardList();
      });
    },
    resetDatas() {
      this.newContent = '';
      this.newTitle = '';
      this.newIsNotice = false;
    },
    getBoardList() {
      var params = new URLSearchParams();
      params.append('bgno', this.gno);
      axios
        .post('getBoardList', params)
        .then((response) => {
          this.table = response.data.notNotice;
          this.tableNotice = response.data.notice;
          for (var i = 0; i < this.table.length; i++) {
            this.table[i].writerName = response.data.notNoticeWriter[i];
          }
          for (i = 0; i < this.tableNotice.length; i++) {
            this.tableNotice[i].writerName = response.data.noticeWriter[i];
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style>
.create-article-btn {
  width: 70px;
  height: 70px;
  border-radius: 50%;
  background-color: blue;
  font-size: 15px;
  color: white;
  text-align: center;

  position: fixed;
  right: 5%;
  bottom: 10%;
}
</style>
