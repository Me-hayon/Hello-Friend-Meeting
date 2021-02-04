<template>
  <v-menu v-model="menu" :close-on-content-click="false" offset-x>
    <template v-slot:activator="{ on, attrs }">
      <v-btn v-bind="attrs" v-on="on"><v-icon>mdi-bell</v-icon></v-btn>
    </template>
    <v-card>
      <v-list>
        <v-list-item>
          <v-list-item-avatar>
            <img :src="imgPath" />
          </v-list-item-avatar>

          <v-list-item-content>
            <v-list-item-title>{{ userInfo.uname }}</v-list-item-title>
            <v-list-item-subtitle>{{ userInfo.utel }}</v-list-item-subtitle>
          </v-list-item-content>

          <!-- <v-list-item-action>
            <v-btn :class="fav ? 'red--text' : ''" icon @click="fav = !fav">
              <v-icon>mdi-heart</v-icon>
            </v-btn>
          </v-list-item-action> -->
        </v-list-item>
      </v-list>

      <v-divider></v-divider>

      <v-list max-height="300" style="overflow-y: auto;">
        <v-list-item v-for="alarm in alarms" :key="alarm.ano">
          <v-list-item-title>
            {{ alarm.asummary }}
          </v-list-item-title>
        </v-list-item>
      </v-list>

      <v-card-actions>
        <v-spacer></v-spacer>

        <v-btn text @click="menu = false">Cancel</v-btn>
        <v-btn color="primary" text @click="menu = false">Save</v-btn>
      </v-card-actions>
    </v-card>
  </v-menu>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      menu: false,
      alarms: [],
      userInfo: {},
      imgPath: '',
    };
  },
  created() {
    var storage = window.sessionStorage;
    var params = new URLSearchParams();
    params.append('email', storage.getItem('user-email'));

    axios
      .post('http://localhost:8080/getAlarms', params)
      .then((response) => {
        this.alarms = response.data;
      })
      .catch((error) => {
        console.log(error);
      });

    axios
      .post('http://localhost:8080/profile', params)
      .then((response) => {
        this.userInfo.uname = response.data['user-name'];
        this.userInfo.utel = response.data['user-tel'];
        this.userInfo.uprofileImg = response.data['profile-img'];
        console.log(this.userInfo.uprofileImg);
        this.imgPath = require(`@/assets/images/avatars/${this.userInfo.uprofileImg}.png`);
      })
      .catch((error) => {
        console.log(error);
      });
  },
};
</script>

<style></style>
