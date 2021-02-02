<template>
  <v-bottom-navigation v-show="$store.state.isFooter" v-model="currentPage" app>
    <v-btn value="home" to="/feed/main">
      <v-icon>mdi-home</v-icon>
    </v-btn>

    <v-btn value="community" to="/community">
      <v-icon>mdi-account-group</v-icon>
    </v-btn>

    <v-btn value="message" to="/message">
      <v-icon>mdi-message</v-icon>
    </v-btn>

    <v-btn value="info" to="/user/info">
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
