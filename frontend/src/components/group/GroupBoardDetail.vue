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
  data() {
    return {
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
  props: {
    bno: {
      type: Number,
    },
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
