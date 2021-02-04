<template>
  <div>
    그룹멤버리스트

    <ul>
      <li v-for="member in memberList" :key="member.uno">{{ member.uname }}</li>
    </ul>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  props: ['gno', 'isGmaster'],
  data() {
    return {
      memberList: [],
    };
  },
  created() {
    var params = new URLSearchParams();
    params.append('gno', this.gno);
    axios
      .post('http://localhost:8080/getUserListInGroup', params)
      .then((response) => {
        this.memberList = response.data.userList;
      })
      .catch((error) => {
        console.log(error);
      });
  },
};
</script>

<style></style>
