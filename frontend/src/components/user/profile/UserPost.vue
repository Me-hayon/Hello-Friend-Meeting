<template>
  <v-col cols="6" style="padding-top: 0; padding-left: 0; padding-right: 0;">
    <v-subheader>내가 작성한 글</v-subheader>

    <v-list
      v-if="posts != null"
      two-line
      subheader
      flat
      height="200"
      style="overflow-y: auto;"
    >
      <v-list-item
        v-for="(post, index) in posts"
        :key="index"
        @click="movePost(index)"
      >
        <v-list-item-avatar>
          <v-icon>mdi-post</v-icon>
        </v-list-item-avatar>

        <v-list-item-content>
          <v-list-item-title v-text="post.btitle"></v-list-item-title>
          <v-list-item-subtitle v-text="post.bdate"></v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
    </v-list>

    <v-row
      v-else
      justify="center"
      align="center"
      style="height: 200px;"
      no-gutters
    >
      <p>작성한 글이 없습니다.</p>
    </v-row>
  </v-col>
</template>

<script>
import axios from 'axios';

export default {
  props: ['currPosts'],
  data() {
    return {
      posts: this.currPosts,
    };
  },
  methods: {
    movePost(index) {
      let params = new URLSearchParams();

      params.append('email', window.sessionStorage.getItem('user-email'));
      params.append('gno', this.posts[index].bgno);

      axios
        .post('isGroupMember', params)
        .then((response) => {
          this.$store.commit('setGno', this.posts[index].bgno);
          this.$store.commit('setBno', this.posts[index].bno);
          this.$store.commit('setMemberStatus', response.data.memberStatus);
          this.$router.push('/board/detail');
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style></style>
