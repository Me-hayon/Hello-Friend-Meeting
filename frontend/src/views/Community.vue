<template>
  <b-container>
    <Tab />
    <router-view />
  </b-container>
</template>

<script>
import Tab from "@/components/community/Tab.vue";

export default {
  components: { Tab },
  created() {
    this.$store.commit("setIsHeader", true);
    this.$store.commit("setIsFooter", true);
  },
};
</script>

<style></style>
