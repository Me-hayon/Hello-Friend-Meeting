<template>
  <div>
    <GroupNav />
  </div>
</template>

<script>
import GroupNav from "@/components/group/GroupNav.vue";
export default {
  components: {
    GroupNav,
  },
  created() {
    this.$store.state.isHeader = true;
    this.$store.state.isFooter = true;
  },
};
</script>

<style></style>
