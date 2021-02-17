<template>
  <v-bottom-navigation
    v-if="$store.getters.getIsFooter"
    v-model="currentPage"
    app
    grow
    shift
    dark
    color="purple lighten-2"
  >
    <v-btn value="home" to="/feed/main">
      <span>홈</span>
      <v-icon>mdi-home</v-icon>
    </v-btn>

    <v-btn value="community" to="/community">
      <span>커뮤니티</span>
      <v-icon>mdi-account-group</v-icon>
    </v-btn>

    <v-btn value="message" to="/message">
      <span>쪽지</span>
      <v-icon>mdi-email-check</v-icon>
    </v-btn>

    <v-btn value="info" to="/user/profile">
      <span>내정보</span>
      <v-icon>mdi-card-account-details</v-icon>
    </v-btn>
  </v-bottom-navigation>
</template>

<script>
export default {
  data() {
    return {
      currentPage: 'home',
    };
  },
  computed: {
    getRouteUrl() {
      return this.$store.getters.getRouteUrl;
    },
  },
  watch: {
    getRouteUrl(routeUrl) {
      if (routeUrl == '/') {
        this.currentPage = 'home';
      } else if (routeUrl == '/group') {
        this.currentPage = 'community';
      } else if (routeUrl == '/board/detail') {
        this.currentPage = 'community';
      } else if (routeUrl == '/friendProfile') {
        this.currentPage = 'community';
      } else if (routeUrl == '/naegi') {
        this.currentPage = 'community';
      }

      this.$store.commit('setRouteUrl', null);
    },
  },
};
</script>

<style></style>
