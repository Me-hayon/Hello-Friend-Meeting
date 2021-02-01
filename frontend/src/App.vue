<template>
  <div id="app">
    <v-app>
      <router-view></router-view>
    </v-app>
  </div>
</template>

<script>
import './components/css/style.scss';

export default {
  name: 'app',
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
