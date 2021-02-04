<template>
  <b-container>
    <div id="page-not-found-window">
      <h1 style="margin-bottom: 50px;">
        <strong>해당 페이지가 존재하지 않습니다.</strong>
      </h1>
      <img src="@/assets/images/page-not-found.png" width="600" />
      <hr class="my-4" />
      <router-link to="/" style="float: right;">
        <b-button style="height: auto;" variant="primary"
          >로그인 페이지로 이동</b-button
        >
      </router-link>
    </div>
  </b-container>
</template>

<script>
export default {
  created() {
    this.$store.commit('setIsHeader', false);
    this.$store.commit('setIsFooter', false);
  },
};
</script>

<style>
#page-not-found-window {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: white;
  padding: 0;
}
</style>
