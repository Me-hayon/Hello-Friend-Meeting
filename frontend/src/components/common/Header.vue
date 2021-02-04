<template>
  <v-app-bar v-if="$store.state.isHeader" app dark flat>
    <v-spacer></v-spacer>
    <alarm-btn />
  </v-app-bar>
</template>

<script>
import AlarmBtn from '@/components/alarm/AlarmBtn.vue';

export default {
  components: { AlarmBtn },
};
</script>

<style></style>
