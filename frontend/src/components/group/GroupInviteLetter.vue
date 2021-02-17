<template>
  <v-container :height="height">
    <h1>HI!!!</h1>
    <v-row>
      <v-btn @click="accept">수락</v-btn>
    </v-row>
    <v-row>
      <v-btn @click="reject">거절</v-btn>
    </v-row>
  </v-container>
</template>

<script>
const storage = window.sessionStorage;
import axios from 'axios';

export default {
  data() {
    return {
      memberStatus: this.$store.getters.getMemberStatus,
      gno: this.$store.getters.getGno,
      uno: this.$store.getters.getUno,
      height: 0,
    };
  },
  props: ['ano'],
  created() {
    this.height = window.screen.height - 112;
  },
  methods: {
    accept() {
      var params = new URLSearchParams();
      params.append('email', storage.getItem('user-email'));
      params.append('gno', this.gno);

      axios
        .post('acceptInviteGroup', params)
        .then((response) => {
          alert(response.data.data);
          this.$store.commit('setMemberStatus', 3);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    reject() {
      var params = new URLSearchParams();
      params.append('email', storage.getItem('user-email'));
      params.append('gno', this.gno);

      axios
        .post('denyInviteGroup', params)
        .then((response) => {
          alert(response.data.data);
          this.delInviteAlarm();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    delInviteAlarm() {
      var params = new URLSearchParams();
      params.append('ano', this.ano);

      axios
        .post('delAlarm', params)
        .then((response) => {
          console.log(response);
          this.$router.push('/feed/main');
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style></style>
