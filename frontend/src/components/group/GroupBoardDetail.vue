<template>
  <div>
    <button @click="returnToGroup">그룹으로 돌아가기</button>
    <br /><br />
    제목 : {{ article.btitle }} <br />
    글쓴이 : {{ writer }} <br />
    내용 : {{ article.bcontent }}
  </div>
</template>

<script>
import axios from 'axios';
export default {
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
      var params = new URLSearchParams();
      params.append('bno', this.bno);
      axios
        .post('boardDetail', params)
        .then((response) => {
          this.article = response.data.curBoard;
          this.writer = response.data.writer.uname;
        })
        .catch((error) => {
          error;
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
      bno: this.$store.getters.getBno,
      uno: this.$store.getters.getUno,
      article: {},
      writer: '',
    };
  },
  created() {
    var params = new URLSearchParams();
    params.append('bno', this.bno);
    axios
      .post('boardDetail', params)
      .then((response) => {
        this.article = response.data.curBoard;
        this.writer = response.data.writer.uname;
      })
      .catch((error) => {
        error;
      });
  },
  methods: {
    returnToGroup() {
      var gno = this.article.bgno;
      this.$router.push({ name: 'GroupMainPage', params: { gno } });
    },
  },
};
</script>

<style></style>
