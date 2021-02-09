<template>
  <v-container>
    <!-- 사용자 설정 버튼 -->
    <user-config-btn />

    <!-- 프로필 사진 버튼 -->
    <profile-image
      v-if="!isLoadingProfileImg"
      :email="email"
      :currProfileImg="profileImg"
      :curr_pImages="pImages"
    />

    <!-- 프로필 커버 이미지 -->
    <profile-cover />

    <!-- 사용자 글 관리 -->
    <user-text-manage />

    <v-divider></v-divider>

    <!-- 사용자 활동 기록 -->
    <user-history v-if="!isLoadingProfileImg" :currProfileImg="profileImg" />
  </v-container>
</template>

<script>
import axios from 'axios';
import ProfileCover from '@/components/user/profile/Cover.vue';
import ProfileImage from '@/components/user/profile/ProfileImage.vue';
import UserConfigBtn from '@/components/user/profile/UserConfigBtn.vue';
import UserTextManage from '@/components/user/profile/UserTextManage.vue';
import UserHistory from '@/components/user/profile/UserHistory.vue';

const storage = window.sessionStorage;

export default {
  components: {
    ProfileCover,
    ProfileImage,
    UserConfigBtn,
    UserTextManage,
    UserHistory,
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
        this.uname = response.data['user-name'];
        this.profileImg = response.data['profile-img'];

        this.pImages = require
          .context('@/assets/images/avatars', true, /^.*\.png$/)
          .keys();

        for (let i = 0; i < this.pImages.length; i++) {
          this.pImages[i] = this.pImages[i].slice(2);
          this.pImages[i] = this.pImages[i].split('.')[0];
          if (this.pImages[i] == this.profileImg) this.pImages.splice(i--, 1);
        }

        this.isLoadingProfileImg = false;
      })
      .catch((error) => {
        alert('토큰이 만료되었습니다ㅜ.ㅜ 다시 로그인 하러 갈까요?');
        storage.removeItem('user-email');
        storage.removeItem('auth-token');
        this.$router.push('/');
      });

    this.$store.commit('setIsHeader', true);
    this.$store.commit('setIsFooter', true);
  },
  data() {
    return {
      uname: '',
      email: '',
      profileImg: '',
      pImages: [],
      isLoadingProfileImg: true,
    };
  },
};
</script>

<style></style>
