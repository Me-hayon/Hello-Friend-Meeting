<template>
  <div>{{ bno }}</div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      article: [],
    };
  },
  created() {
    var params = new URLSearchParams();
    params.append("bno", this.bno);
    axios.post("http://localhost:8080/boardDetail", params).then((response) => {
      console.log(response);
    });
  },
  props: {
    bno: {
      type: Number,
    },
  },
};
</script>

<style></style>
