<template>
  <div>
    <GroupNav :gno="gno" />
  </div>
</template>

<script>
import GroupNav from '@/components/group/GroupNav.vue';
export default {
  props: ['gno'],
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
