<template>
  <b-container>
    <div id="error-window">
      <h1 style="margin-bottom: 50px;">
        <strong>해당 페이지에 오류가 발생했습니다.</strong>
      </h1>
      <div style="text-align: center;">
        <img src="@/assets/images/error.png" width="100" />
      </div>
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
    this.$store.state.isHeader = false;
    this.$store.state.isFooter = false;
  },
};
</script>

<style>
#error-window {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: white;
  padding: 0;
}
</style>
