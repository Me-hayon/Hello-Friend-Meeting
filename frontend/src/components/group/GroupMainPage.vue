<template>
  <div>
    <GroupNav />
  </div>
</template>

<script>
import GroupNav from '@/components/group/GroupNav.vue';
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
    },
    vuexMemberStatus(val) {
      this.memberStatus = val;
    },
  },
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
      .post('isGroupMember', params)
      .then((response) => {
        this.memberStatus = response.data.memberStatus;
      })
      .catch((error) => {
        console.log(error);
      });
  },
  data() {
    return {
      memberStatus: this.$store.getters.getMemberStatus,
      gno: this.$store.getters.getGno,
      uno: this.$store.getters.getUno,
    };
  },
};
</script>

<style></style>
