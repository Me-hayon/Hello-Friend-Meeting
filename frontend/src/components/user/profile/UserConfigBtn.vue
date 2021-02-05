<template>
  <v-speed-dial
    v-model="userConfigFab"
    absolute
    top
    right
    direction="left"
    transition="slide-x-reverse-transition"
  >
    <template v-slot:activator>
      <v-btn v-model="userConfigFab" fab icon dark>
        <v-icon size="25" color="black">mdi-cog</v-icon>
      </v-btn>
    </template>
    <v-btn fab dark small color="red">
      <v-icon>mdi-account-remove</v-icon>
    </v-btn>
    <v-btn fab dark small color="indigo" to="/user/modify">
      <v-icon>mdi-lock-reset</v-icon>
    </v-btn>
    <v-btn fab dark small color="green" @click="logout">
      <v-icon>mdi-logout</v-icon>
    </v-btn>
  </v-speed-dial>
</template>

<script>
const storage = window.sessionStorage;

export default {
  data() {
    return {
      userConfigFab: false,
    };
  },
  methods: {
    logout() {
      storage.removeItem('auth-token');
      storage.removeItem('user-email');
      this.$router.push('/');
    },
  },
};
</script>

<style></style>
