<template>
  <div>
    <button @click="returnToGroup">그룹으로 돌아가기</button>
    <br /><br />
    제목 : {{ article.btitle }} <br />
    글쓴이 : {{ writer }} <br />
    내용 : {{ article.bcontent }}<br />
    <b-button v-b-modal.modal-modify v-if="localIsWriter">글 수정</b-button>
    <b-modal
      id="modal-modify"
      title="글 수정"
      @ok="modifyBoard"
      @hide="resetDatas"
      >제목 : <input type="text" v-model="newTitle" />
      <b-form-textarea
        id="textarea-rows"
        rows="8"
        v-model="newContent"
      ></b-form-textarea>
    </b-modal>
    <button @click="delBoard" v-if="localIsWriter || memberStatus === 4">
      글 삭제
    </button>

    <hr />
    <b-button v-b-modal.modal-1>댓글작성</b-button>
    <b-modal
      id="modal-1"
      title="댓글쓰기"
      @ok="writeComment"
      @hide="resetDatas"
    >
      <b-form-textarea
        id="textarea-rows"
        rows="8"
        v-model="newCommentContent"
      ></b-form-textarea>
    </b-modal>
    <p v-for="comment in comments" :key="comment.cno">
      {{ comment.ccontent }}

      <b-button v-b-modal.modal-2 v-if="comment.isWriter">댓글수정</b-button>
      <b-modal
        id="modal-2"
        title="댓글수정"
        @ok="modifyComment(comment.cno)"
        @hide="resetDatas"
      >
        <b-form-textarea
          id="textarea-rows"
          rows="8"
          v-model="newCommentContent"
        ></b-form-textarea>
      </b-modal>

      <button
        v-if="comment.isWriter || memberStatus === 4"
        @click="delComment(comment.cno)"
      >
        삭제
      </button>
    </p>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  computed: {
    vuexGno() {
      return this.$store.getters.getGno;
    },
    vuexBno() {
      return this.$store.getters.getBno;
    },
    vuexMemberStatus() {
      return this.$store.getters.getMemberStatus;
    },
    isWriter() {
      return this.$store.getters.getIsWriter;
    },
  },
  watch: {
    vuexGno(val) {
      this.gno = val;
    },
    vuexBno(val) {
      this.bno = val;
      var params = new URLSearchParams();
      params.append('bno', this.bno);
      params.append('email', window.sessionStorage.getItem('user-email'));
      axios
        .post('boardDetail', params)
        .then((response) => {
          this.article = response.data.curBoard;
          this.writer = response.data.writer.uname;
          this.localIsWriter = response.data.isWriter;
          this.newTitle = this.article.btitle;
          this.newContent = this.article.bcontent;

          axios.post('getCommentList', params).then((resp) => {
            this.comments = resp.data.comments;
            var writerList = resp.data.writerList;
            var isWriterList = resp.data.isWriterList;

            for (var i = 0; i < this.comments.length; i++) {
              this.comments[i].writerName = writerList[i];
              this.comments[i].isWriter = isWriterList[i];
            }
          });
        })
        .catch((error) => {
          error;
        });
    },
    vuexMemberStatus(val) {
      this.memberStatus = val;
    },
    isWriter(val) {
      this.LocalIsWriter = val;
    },
  },
  data() {
    return {
      memberStatus: this.$store.getters.getMemberStatus,
      gno: this.$store.getters.getGno,
      bno: this.$store.getters.getBno,
      LocalIsWriter: this.$store.getters.getIsWriter,
      article: {},
      writer: '',
      comments: [],
      newCommentContent: '',
      newTitle: '',
      newContent: '',
    };
  },
  created() {
    this.getBoardDetail();
  },
  methods: {
    getBoardDetail() {
      var params = new URLSearchParams();
      params.append('bno', this.bno);
      params.append('email', window.sessionStorage.getItem('user-email'));
      axios
        .post('boardDetail', params)
        .then((response) => {
          this.article = response.data.curBoard;
          this.writer = response.data.writer.uname;
          this.localIsWriter = response.data.isWriter;
          this.newTitle = this.article.btitle;
          this.newContent = this.article.bcontent;
          this.getComments();
        })
        .catch((error) => {
          error;
        });
    },
    getComments() {
      var params = new URLSearchParams();
      params.append('bno', this.bno);
      params.append('email', window.sessionStorage.getItem('user-email'));
      axios.post('getCommentList', params).then((resp) => {
        this.comments = resp.data.comments;
        var writerList = resp.data.writerList;
        var isWriterList = resp.data.isWriterList;

        for (var i = 0; i < this.comments.length; i++) {
          this.comments[i].writerName = writerList[i];
          this.comments[i].isWriter = isWriterList[i];
        }
      });
    },
    returnToGroup() {
      var gno = this.article.bgno;
      this.$router.push({ name: 'GroupMainPage', params: { gno } });
    },
    resetDatas() {
      this.newCommentContent = '';
      this.newTitle = this.article.btitle;
      this.newContent = this.article.bcontent;
    },
    writeComment() {
      var params = new URLSearchParams();
      params.append('email', window.sessionStorage.getItem('user-email'));
      params.append('ccontent', this.newCommentContent);
      params.append('bno', this.bno);

      axios.post('writeComment', params).then((response) => {
        alert(response.data.data);
        this.getComments();
        this.resetDatas();
      });
    },
    modifyComment(cno) {
      var params = new URLSearchParams();
      params.append('cno', cno);
      params.append('ccontent', this.newCommentContent);
      axios.post('modifyComment', params).then((resp) => {
        alert(resp.data.data);
        this.getComments();
      });
    },
    delComment(cno) {
      var params = new URLSearchParams();
      params.append('cno', cno);
      axios.post('delComment', params).then((resp) => {
        alert(resp.data.data);
        this.getComments();
      });
    },
    delBoard() {
      var params = new URLSearchParams();
      params.append('bno', this.bno);
      axios.post('delBoard', params).then((resp) => {
        alert(resp.data.data);
        this.$store.commit('setGno', this.gno);
        this.$router.push('/group');
      });
    },
    modifyBoard() {
      var params = new URLSearchParams();
      params.append('bno', this.bno);
      params.append('title', this.newTitle);
      params.append('content', this.newContent);
      axios.post('modifyBoard', params).then((resp) => {
        this.getBoardDetail();
        alert(resp.data.data);
      });
    },
  },
};
</script>

<style></style>
