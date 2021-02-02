<template>
  <b-container style="background-color: rgb(247, 246, 232);">
    <Tab />
    <router-view />
  </b-container>
</template>

<script>
import Tab from '@/components/community/Tab.vue';

export default {
  components: { Tab },
  created() {
    this.$store.state.isHeader = true;
  },
};
</script>

<style></style>
