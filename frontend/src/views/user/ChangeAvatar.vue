<template>
  <b-container>
    <div>
      <table
        class="table b-table table-striped table-hover"
        style="width:100%; text-align:center"
      >
        <tr>
          <td>이미지</td>
          <td>선택</td>
        </tr>
        <tr v-for="item in items" :key="item.ino" style="max-width:100%">
          <td><img :src="item.imgPath" /></td>
          <td style="vertical-align:middle">
            <b-button
              variant="primary"
              v-if="myProfile != item.ino"
              @click="updateProfile(item.ino)"
              >변경</b-button
            >
            <b-button
              variant="primary"
              v-if="myProfile === item.ino"
              :disabled="true"
              >선택됨</b-button
            >
          </td>
        </tr>
      </table>
    </div>
  </b-container>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      myProfileNo: '',
      items: [
        { ino: '01', imgPath: require(`@/assets/images/avatars/01.png`) },
        { ino: '02', imgPath: require(`@/assets/images/avatars/02.png`) },
        { ino: '03', imgPath: require(`@/assets/images/avatars/03.png`) },
        { ino: '04', imgPath: require(`@/assets/images/avatars/04.png`) },
        { ino: '05', imgPath: require(`@/assets/images/avatars/05.png`) },
        { ino: '06', imgPath: require(`@/assets/images/avatars/06.png`) },
        { ino: '07', imgPath: require(`@/assets/images/avatars/07.png`) },
        { ino: '08', imgPath: require(`@/assets/images/avatars/08.png`) },
        { ino: '09', imgPath: require(`@/assets/images/avatars/09.png`) },
        { ino: '10', imgPath: require(`@/assets/images/avatars/10.png`) },
      ],
    };
  },
  computed: {
    myProfile() {
      return this.myProfileNo;
    },
  },
  created() {
    var params = new URLSearchParams();
    var storage = window.sessionStorage;
    params.append('email', storage.getItem('user-email'));

    axios
      .post('profile', params)
      .then((response) => {
        this.myProfileNo = response.data['profile-img'];
      })
      .catch((error) => {
        console.log(error);
      });
  },
  methods: {
    updateProfile(profileName) {
      var storage = window.sessionStorage;
      var params = new URLSearchParams();
      params.append('email', storage.getItem('user-email'));
      params.append('profileName', profileName);
      axios
        .post('updateProfile', params)
        .then((response) => {
          this.myProfileNo = response.data['changed'];
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style></style>
