<template>
  <div>
    <div style="margin-left:5%; margin-right:5%">
      <img
        style="float: left; margin-right: 25px;"
        width="100"
        :src="require(`@/assets/images/avatars/${profileImg}.png`)"
      />
      <div>
        <p style="font-size: 1.5rem; margin-bottom: 3px;">
          <strong>{{ fname }}</strong>
        </p>
        <p style="margin-bottom: 5px;">
          <strong>📧 {{ friendEmail }}</strong>
        </p>
      </div>
    </div>
    <b-button
      variant="primary"
      v-show="fStatus === 0"
      style="width:80%; margin-left:10%; margin-top:50px"
      @click="friendRequest(tel)"
      >친구 요청</b-button
    >
    <b-button
      variant="primary"
      v-show="fStatus === 1"
      style="width:80%; margin-left:10%; margin-top:50px"
      @click="friendCancel(friendEmail)"
      >친구 요청 취소</b-button
    >
    <b-button
      variant="primary"
      v-show="fStatus === 2"
      style="width:80%; margin-left:10%; margin-top:50px"
      @click="friendAccept(friendEmail)"
      >친구 요청 수락</b-button
    >
    <b-button
      variant="danger"
      v-show="fStatus === 2"
      style="width:80%; margin-left:10%; margin-top:20px"
      @click="friendDeny(friendEmail)"
      >친구 요청 거절</b-button
    >
    <b-button
      variant="primary"
      v-show="fStatus === 3"
      style="width:80%; margin-left:10%; margin-top:50px"
      @click="friendDelete(friendEmail)"
      >친구 삭제</b-button
    >
  </div>
</template>

<script>
import axios from 'axios';
const storage = window.sessionStorage;

export default {
  props: ['friendEmail'],
  methods: {
    friendRequest(tel) {
      var params = new URLSearchParams();
      params.append('myEmail', storage.getItem('user-email'));
      params.append('targetTel', tel);
      axios
        .post('http://localhost:8080/addFriendByTel', params)
        .then((response) => {
          alert(response.data.data);
          this.friendCheck();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    friendCancel(friendEmail) {
      var params = new URLSearchParams();
      params.append('myEmail', storage.getItem('user-email'));
      params.append('friendEmail', friendEmail);
      axios
        .post('http://localhost:8080/cancelRequest', params)
        .then((response) => {
          alert(response.data.data);
          this.friendCheck();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    friendAccept(friendEmail) {
      var params = new URLSearchParams();
      console.log('aaaaaaaaaaaaaaaaaaaaaaaaaaaa');
      params.append('myEmail', storage.getItem('user-email'));
      params.append('friendEmail', friendEmail);
      axios
        .post('http://localhost:8080/acceptFriend', params)
        .then((response) => {
          alert(response.data.data);
          this.friendCheck();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    friendDeny(friendEmail) {
      var params = new URLSearchParams();
      params.append('myEmail', storage.getItem('user-email'));
      params.append('friendEmail', friendEmail);
      axios
        .post('http://localhost:8080/denyFriend', params)
        .then((response) => {
          alert(response.data.data);
          this.friendCheck();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    friendDelete(friendEmail) {
      var params = new URLSearchParams();
      params.append('myEmail', storage.getItem('user-email'));
      params.append('friendEmail', friendEmail);
      axios
        .post('http://localhost:8080/delFriend', params)
        .then((response) => {
          alert(response.data.data);
          this.friendCheck();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    friendCheck() {
      var params = new URLSearchParams();
      params.append('myEmail', storage.getItem('user-email'));
      params.append('friendEmail', this.friendEmail);
      axios
        .post('http://localhost:8080/isFriend', params)
        .then((response) => {
          this.friendStatus = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  data() {
    return {
      fname: '',
      email: '',
      tel: '',
      friendStatus: { type: Number, default: 4 },
      profileImg: '',
    };
  },
  created() {
    var params = new URLSearchParams();
    params.append('email', this.friendEmail);
    axios
      .post('http://localhost:8080/profile', params)
      .then((response) => {
        this.fname = response.data['user-name'];
        this.tel = response.data['user-tel'];
        this.profileImg = response.data['profile-img'];
      })
      .catch((error) => {
        console.log(error);
      });
    this.friendCheck();
  },
  computed: {
    fStatus() {
      return this.friendStatus;
    },
  },
};
</script>

<style></style>
