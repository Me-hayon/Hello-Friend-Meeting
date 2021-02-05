<template>
  <v-container>
    <Profile />
    <user-delete-modal />
  </v-container>
</template>

<script>
import Profile from '@/components/user/Profile.vue';
import UserDeleteModal from '@/components/user/UserDeleteModal.vue';

const storage = window.sessionStorage;

export default {
  components: { Profile, UserDeleteModal },
  created() {
    this.$store.commit('setIsHeader', true);
    this.$store.commit('setIsFooter', true);
  },
};
</script>

<style></style>
