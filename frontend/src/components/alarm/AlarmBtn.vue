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
        <v-list-item
          v-for="alarm in alarms"
          :key="alarm.ano"
          @click="goRouting(alarm.aurl, alarm.createUser, alarm.ano)"
        >
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
    console.log('ssssssssssss');
    this.getAlarmsList();
    setInterval(this.getAlarmsList, 10000);
    var storage = window.sessionStorage;
    var params = new URLSearchParams();
    params.append('email', storage.getItem('user-email'));

    axios
      .post('profile', params)
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
  methods: {
    goRouting(aurl, myParam, ano) {
      var params = new URLSearchParams();
      params.append('ano', ano);
      axios.post('readAlarm', params);

      params = new URLSearchParams();
      if (aurl === 'FriendInfo') {
        params.append('uno', myParam);
        axios.post('findEmailByUno', params).then((response) => {
          var friendEmail = response.data.data;
          this.$router.push({ name: aurl, params: { friendEmail } });
        });
      } else if (aurl === 'GroupMainPage') {
        params.append('gno', myParam);
        var gno = myParam;
        console.log(gno);
        this.$router.push({ name: aurl, params: { gno } }).catch(() => {
          // this.$emit('changeProps', memberStatus);
        });
      }
      // else if(augb===''){//보드로보낼거

      // }
    },
    getAlarmsList() {
      var storage = window.sessionStorage;
      var params = new URLSearchParams();
      params.append('email', storage.getItem('user-email'));

      axios
        .post('getAlarms', params)
        .then((response) => {
          this.alarms = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style></style>
