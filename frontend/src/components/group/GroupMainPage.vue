<template>
  <div>
    <GroupNav :gno="gno" :memberStatus="memberStatus" />
  </div>
</template>

<script>
import GroupNav from '@/components/group/GroupNav.vue';
import axios from 'axios';
export default {
  props: ['gno'],
  components: {
    GroupNav,
  },
  created() {
    this.$store.state.isHeader = true;
    this.$store.state.isFooter = true;

    var storage = window.sessionStorage;
    var params = new URLSearchParams();
    params.append('email', storage.getItem('user-email'));
    params.append('gno', this.gno);
    axios
      .post('http://localhost:8080/isGroupMember', params)
      .then((response) => {
        this.memberStatus = response.data.memberStatus;
      })
      .catch((error) => {
        console.log(error);
      });
  },
  data() {
    return {
      memberStatus: '',
    };
  },
};
</script>

<style></style>
