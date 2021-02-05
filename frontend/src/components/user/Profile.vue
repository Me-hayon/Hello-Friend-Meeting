<template>
  <div style="margin-left:5%; margin-right:5%">
    <img
      style="float: left; margin-right: 25px;"
      width="100"
      :src="require(`@/assets/images/avatars/${profileImg}.png`)"
      @click="changeAvatar"
    />
    <div>
      <p style="font-size: 1.5rem; margin-bottom: 3px;">
        <strong>{{ uname }}</strong>
      </p>
      <p style="margin-bottom: 5px;">
        <strong>📧 {{ email }}</strong>
      </p>
      <p>
        <strong>📞 {{ tel }}</strong>
      </p>
    </div>
    <div style="float:right">
      <router-link to="/user/modify"
        ><b-button variant="dark" size="sm"
          >비밀번호 변경</b-button
        ></router-link
      >
    </div>
  </div>
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
    };
  },
  methods: {
    changeAvatar() {
      this.$router.push('/user/changeAvatar');
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
