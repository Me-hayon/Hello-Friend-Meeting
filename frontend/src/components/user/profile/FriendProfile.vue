<template>
  <v-container style="height: 600px;">
    <!-- 프로필 커버 이미지 -->
    <profile-cover />

    <!-- 프로필 사진 -->
    <v-btn style="top: -35px; left: 115px;" fab disabled>
      <v-img
        contain
        height="100"
        :src="require(`@/assets/images/avatars/${info.uprofileImg}.png`)"
      ></v-img>
    </v-btn>

    <!-- 친구 관리 버튼 -->
    <friend-manage-btn :uno="uno" :info="info" @deleteFriend="deleteFriend" />

    <v-divider></v-divider>

    <!-- 그룹 가입 목록 -->
    <friend-group-list :uno="uno" :info="info" :categoryList="categoryList" />
  </v-container>
</template>

<script>
import ProfileCover from '@/components/user/profile/ProfileCover.vue';
import FriendManageBtn from '@/components/user/profile/FriendManageBtn.vue';
import FriendGroupList from '@/components/user/profile/FriendGroupList.vue';

const storage = window.sessionStorage;

export default {
  components: {
    ProfileCover,
    FriendManageBtn,
    FriendGroupList,
  },
  props: ['uno', 'info', 'categoryList'],
  methods: {
    deleteFriend(friendUno) {
      this.$emit('deleteFriend', friendUno);
    },
  },
};
</script>

<style></style>
