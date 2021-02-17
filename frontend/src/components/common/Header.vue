<template>
  <v-app-bar v-if="$store.getters.getIsHeader" app dark flat>
    <v-btn @click="$router.go(-1)" icon>
      <v-icon>mdi-arrow-left</v-icon>
    </v-btn>
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
