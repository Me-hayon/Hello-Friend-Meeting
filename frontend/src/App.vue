<template>
  <v-app>
    <Header />
    <v-main>
      <router-view></router-view>
    </v-main>
    <Footer />
  </v-app>
</template>

<script>
import './components/css/style.scss';
import Header from '@/components/common/Header.vue';
import Footer from '@/components/common/Footer.vue';

export default {
  name: 'app',
  components: { Header, Footer },
};
</script>

<style>
.container {
  padding-left: 0 !important;
  padding-right: 0 !important;
}

.container > div {
  padding: 0;
}

.row {
  margin-left: 0 !important;
  margin-right: 0 !important;
}

.btn {
  height: auto;
  box-shadow: none;
}
</style>
