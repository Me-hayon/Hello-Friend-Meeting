<template>
  <b-container style="background-color: rgb(247, 246, 232);">
    <Tab />
    <router-view />
    <Footer />
  </b-container>
</template>

<script>
import Footer from '@/components/common/BottomNav.vue';
import Tab from '@/components/community/Tab.vue';

export default {
  components: { Footer, Tab },
  created() {
    this.$store.state.isHeader = true;
  },
};
</script>

<style></style>
