<template>
  <b-container style="background-color: rgb(247, 246, 232);">
    <Header />
    <Tab />
    <router-view />
    <Footer />
  </b-container>
</template>

<script>
import Header from '@/components/common/Header.vue';
import Footer from '@/components/common/BottomNav.vue';
import Tab from '@/components/community/Tab.vue';

export default {
  components: { Header, Footer, Tab },
};
</script>

<style></style>
