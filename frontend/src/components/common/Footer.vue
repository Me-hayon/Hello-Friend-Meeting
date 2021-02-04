<template>
  <v-bottom-navigation
    v-show="$store.state.isFooter"
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
      <span>그룹/친구</span>
      <v-icon>mdi-account-group</v-icon>
    </v-btn>

    <v-btn value="message" to="/message">
      <span>쪽지</span>
      <v-icon>mdi-message</v-icon>
    </v-btn>

    <v-btn value="info" to="/user/info">
      <span>내정보</span>
      <v-icon>mdi-account-box</v-icon>
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
};
</script>

<style></style>
