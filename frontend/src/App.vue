<template>
  <v-app>
    <v-app-bar v-show="$store.state.isHeader" app dark flat>
      <v-spacer></v-spacer>
      <alarm-btn />
    </v-app-bar>
    <v-main>
      <router-view></router-view>
    </v-main>
    <v-bottom-navigation></v-bottom-navigation>
  </v-app>
</template>

<script>
import './components/css/style.scss';
import AlarmBtn from '@/components/alarm/AlarmBtn.vue';

export default {
  name: 'app',
  components: { AlarmBtn },
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
