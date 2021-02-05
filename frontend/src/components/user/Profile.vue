<template>
  <v-row>
    <v-col>
      <v-row>
        <v-img
          src="https://cdn.vuetifyjs.com/images/cards/cooking.png"
          height="200"
        ></v-img>
      </v-row>

      <!-- 사용자 설정 버튼 -->
      <v-speed-dial
        v-model="userConfigFab"
        absolute
        top
        right
        direction="left"
        transition="slide-x-reverse-transition"
      >
        <template v-slot:activator>
          <v-btn v-model="userConfigFab" fab icon dark>
            <v-icon size="25" color="black">mdi-cog</v-icon>
          </v-btn>
        </template>
        <v-btn fab dark small color="red">
          <v-icon>mdi-account-remove</v-icon>
        </v-btn>
        <v-btn fab dark small color="indigo" to="/user/modify">
          <v-icon>mdi-lock-reset</v-icon>
        </v-btn>
        <v-btn fab dark small color="green" @click="logout">
          <v-icon>mdi-logout</v-icon>
        </v-btn>
      </v-speed-dial>

      <!-- 프로필 사진 버튼 -->
      <v-speed-dial
        style="top: 160px; left: 175px;"
        v-model="profileImgFab"
        absolute
        direction="bottom"
        transition="slide-y-transition"
      >
        <template v-slot:activator>
          <v-btn v-model="profileImgFab" fab>
            <v-img
              contain
              height="100"
              :src="require(`@/assets/images/avatars/${profileImg}.png`)"
            ></v-img>
          </v-btn>
        </template>
        <v-row justify="center" style="width: 300px; overflow-y: visible;">
          <v-btn
            v-for="(pImage, index) in pImages"
            :key="index"
            fab
            @click="changeAvatar(pImage, index)"
          >
            <v-img
              contain
              height="100"
              :src="require(`@/assets/images/avatars/${pImage}.png`)"
            ></v-img>
          </v-btn>
        </v-row>
      </v-speed-dial>
    </v-col>
  </v-row>
</template>

<script>
import axios from 'axios';
const storage = window.sessionStorage;

export default {
  data() {
    return {
      uname: '',
      email: '',
      tel: '',
      profileImg: '',
      pImages: [],
      profileImgFab: false,
      userConfigFab: false,
    };
  },
  methods: {
    changeAvatar(pImage, index) {
      let prevImg = this.profileImg;
      this.profileImg = pImage;
      this.pImages.splice(index, 1);
      this.pImages.splice(index, 0, prevImg);

      let params = new URLSearchParams();
      params.append('email', storage.getItem('user-email'));
      params.append('profileImg', pImage);

      axios
        .put('http://localhost:8080/changeAvatar', params)
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    logout() {
      storage.removeItem('auth-token');
      storage.removeItem('user-email');
      this.$router.push('/');
    },
  },
  created() {
    this.email = storage.getItem('user-email');

    let params = new URLSearchParams();
    params.append('email', this.email);
    params.append('auth-token', storage.getItem('auth-token'));

    axios
      .create({
        headers: {
          'auth-token': storage.getItem('auth-token'),
        },
      })
      .post('profile', params)
      .then((response) => {
        this.tel = response.data['user-tel'];
        this.uname = response.data['user-name'];
        this.tel =
          this.tel.substr(0, 3) +
          '-' +
          this.tel.substr(3, 4) +
          '-' +
          this.tel.substr(7, 4);

        this.profileImg = response.data['profile-img'];

        this.pImages = require
          .context('@/assets/images/avatars', true, /^.*\.png$/)
          .keys();

        for (let i = 0; i < this.pImages.length; i++) {
          this.pImages[i] = this.pImages[i].slice(2);
          this.pImages[i] = this.pImages[i].split('.')[0];
          if (this.pImages[i] == this.profileImg) this.pImages.splice(i--, 1);
        }
      })
      .catch((error) => {
        alert('토큰이 만료되었습니다ㅜ.ㅜ 다시 로그인 하러 갈까요?');
        storage.removeItem('user-email');
        storage.removeItem('auth-token');
        this.$router.push('/');
      });
  },
};
</script>

<style></style>
