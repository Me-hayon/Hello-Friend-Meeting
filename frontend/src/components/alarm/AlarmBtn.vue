<template>
  <div class="text-center">
    <v-menu v-model="menu" :close-on-content-click="false" offset-x>
      <template v-slot:activator="{ on, attrs }">
        <v-btn v-bind="attrs" v-on="on">
          <v-badge
            :value="alarmLen != 0"
            color="deep-purple accent-4"
            right
            overlap
          >
            <span slot="badge">{{ alarmLen }}</span>
            <v-icon>mdi-bell</v-icon>
          </v-badge>
        </v-btn>
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

        <v-divider
          style="margin-bottom: 0; border: dashed #D3D3D3 1px;"
        ></v-divider>

        <v-list
          width="300"
          max-height="300"
          style="overflow-y: auto; padding-top: 0;"
        >
          <v-row
            no-gutters
            v-for="alarm in alarms"
            :key="alarm.ano"
            :style="{ backgroundColor: alarmBgColor(alarm.aisRead) }"
            style="margin: 0;"
          >
            <v-col cols="9" style="padding: 0;">
              <v-list-item
                @click="goRouting(alarm.aurl, alarm.aurlNo, alarm.ano)"
                style="padding-right: 0;"
              >
                <v-list-item-title style="letter-spacing: -1px;">
                  {{ alarm.asummary }}
                </v-list-item-title>
              </v-list-item>
            </v-col>
            <v-col style="margin: 0; padding: 0;"
              ><v-btn class="ma-2" text icon @click="delAlarm(alarm.ano)"
                ><v-icon>mdi-close-circle-outline</v-icon></v-btn
              >
            </v-col>
          </v-row>
        </v-list>

        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn text @click="menu = false">Cancel</v-btn>
          <v-btn color="primary" text @click="menu = false">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-menu>
  </div>
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
      alarmLen: '',
      offset: true,
    };
  },

  created() {
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

  computed: {
    alarmBgColor() {
      return (aisRead) => {
        return aisRead ? '#E6E6FA' : 'white';
      };
    },
  },

  methods: {
    goRouting(aurl, myParam, ano) {
      this.readAlarm(ano);

      var params = new URLSearchParams();
      if (aurl === 'FriendInfo') {
        this.$store.commit('setUno', myParam);
        this.$router.push('/user/friend-info').catch(() => {});
      } else if (aurl === 'GroupMainPage') {
        var gno = myParam;
        params.append('email', window.sessionStorage.getItem('user-email'));
        params.append('gno', gno);
        axios
          .post('isGroupMember', params)
          .then((response) => {
            this.$store.commit('setGno', gno);
            this.$store.commit('setMemberStatus', response.data.memberStatus);
            this.$router.push('/group').catch(() => {});
          })
          .catch((error) => {
            console.log(error);
          });
      } else if (aurl === 'BoardDetail') {
        var bno = myParam;
        params.append('bno', bno);
        params.append('email', window.sessionStorage.getItem('user-email'));
        axios.post('boardDetail', params).then((resp) => {
          if (!resp.data.isExist) {
            alert('삭제된 게시글입니다.');
            this.$router.push('/');
            return;
          }
          this.$store.commit('setIsWriter', resp.data.isWriter);

          params = new URLSearchParams();
          params.append('email', window.sessionStorage.getItem('user-email'));
          params.append('gno', resp.data.curBoard.bgno);

          axios.post('isGroupMember', params).then((response) => {
            this.$store.commit('setGno', gno);
            this.$store.commit('setMemberStatus', response.data.memberStatus);
            this.$router.push('/board/detail').catch(() => {});
          });
        });
      }
    },
    getAlarmsList() {
      var storage = window.sessionStorage;
      var params = new URLSearchParams();
      params.append('email', storage.getItem('user-email'));

      axios
        .post('getAlarms', params)
        .then((response) => {
          this.alarms = response.data.alarms;
          this.alarmLen = response.data.notReadAlarm;
          console.log(this.alarms);
        })
        .catch((error) => {
          console.log(error);
        });

      if (this.userInfo.uprofileImg === 'not') {
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
      }
    },
    delAlarm(ano) {
      var params = new URLSearchParams();
      params.append('ano', ano);

      axios
        .post('delAlarm', params)
        .then((response) => {
          console.log(ano);
          this.getAlarmsList();
          // this.reloadAlarm();
          // alert('삭제됨');
        })
        .catch((error) => {
          alert('에러');
          console.log(ano);
        });
    },
    readAlarm(ano) {
      var params = new URLSearchParams();
      params.append('ano', ano);
      axios.post('readAlarm', params).then((resp) => {
        params = new URLSearchParams();
        params.append('email', window.sessionStorage.getItem('user-email'));
        axios
          .post('getAlarms', params)
          .then((response) => {
            this.alarms = response.data.alarms;
            this.alarmLen = response.data.notReadAlarm;
            // console.log(this.alarms);
          })
          .catch((error) => {
            console.log(error);
          });
      });
    },
    // reloadAlarm() {
    //   var storage = window.sessionStorage;
    //   var params = new URLSearchParams();
    //   params.append('email', storage.getItem('user-email'));

    //   axios
    //     .post('getAlarms', params)
    //     .then((response) => {
    //       this.alarms = response.data;
    //       this.alarmLen = response.data.alarms.length;
    //     })
    //     .catch((error) => {
    //       console.log(error);
    //     });
    // },
  },
};
</script>

<style></style>
