<template>
  <b-container style="background-color: rgb(247, 246, 232)">
    <Header />
    <Profile />
    <b-row style="padding-top: 90%" class="justify-content-center">
      <b-button
        variant="warning"
        style="width:70%; margin-left:35%; color:white"
        @click="onClickLogout"
        >로그아웃</b-button
      >
    </b-row>
    <b-row style="position: absolute; top: 80%; left: 80%">
      <a v-b-modal.user-delete-modal style="color: #007bff;">회원탈퇴</a>
    </b-row>
    <Footer />
    <user-delete-modal />
  </b-container>
</template>

<script>
import Header from "@/components/common/Header.vue";
import Profile from "@/components/user/Profile.vue";
import Footer from "@/components/common/BottomNav.vue";
import UserDeleteModal from "@/components/user/UserDeleteModal.vue";

const storage = window.sessionStorage;

export default {
  components: { Header, Profile, UserDeleteModal, Footer },
  methods: {
    onClickLogout() {
      storage.removeItem("auth-token");
      storage.removeItem("user-email");
      this.$router.push("/");
    },
  },
};
</script>

<style></style>
