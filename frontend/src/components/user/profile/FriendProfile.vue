<template>
  <v-container>
    <!-- 프로필 커버 이미지 -->
    <profile-cover />

    <!-- 프로필 사진 -->
    <v-btn
      v-model="profileImgFab"
      style="top: -35px; left: 115px;"
      fab
      disabled
    >
      <v-img
        contain
        height="100"
        :src="require(`@/assets/images/avatars/${info.uprofileImg}.png`)"
      ></v-img>
    </v-btn>

    <!-- 친구 관리 버튼 -->
    <friend-manage-btn :uno="uno" :info="info" @deleteFriend="deleteFriend" />

    <v-divider></v-divider>

    <!-- 사용자 활동 기록 -->
    <user-history
      v-if="!isLoadingProfileImg"
      :currProfileImg="info.uprofileImg"
    />
  </v-container>
</template>

<script>
import axios from 'axios';
import ProfileCover from '@/components/user/profile/ProfileCover.vue';
import FriendManageBtn from '@/components/user/profile/FriendManageBtn.vue';
import UserHistory from '@/components/user/profile/UserHistory.vue';

const storage = window.sessionStorage;

export default {
  components: {
    ProfileCover,
    FriendManageBtn,
    UserHistory,
  },
  props: ['uno', 'info'],
  methods: {
    deleteFriend(friendUno) {
      this.$emit('deleteFriend', friendUno);
    },
  },
  created() {
    // axios
    //   .create({
    //     headers: {
    //       'auth-token': storage.getItem('auth-token'),
    //     },
    //   })
    //   .post('profile', params)
    //   .then((response) => {
    //     this.uname = response.data['user-name'];
    //     this.profileImg = response.data['profile-img'];
    //     this.pImages = require
    //       .context('@/assets/images/avatars', true, /^.*\.png$/)
    //       .keys();
    //     for (let i = 0; i < this.pImages.length; i++) {
    //       this.pImages[i] = this.pImages[i].slice(2);
    //       this.pImages[i] = this.pImages[i].split('.')[0];
    //       if (this.pImages[i] == this.profileImg) this.pImages.splice(i--, 1);
    //     }
    //     this.isLoadingProfileImg = false;
    //   })
    //   .catch((error) => {
    //     alert('토큰이 만료되었습니다ㅜ.ㅜ 다시 로그인 하러 갈까요?');
    //     storage.removeItem('user-email');
    //     storage.removeItem('auth-token');
    //     this.$router.push('/');
    //   });
  },
};
</script>

<style></style>
