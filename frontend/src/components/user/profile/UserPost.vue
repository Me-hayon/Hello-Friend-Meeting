<template>
  <v-col cols="6" style="padding-top: 0; padding-left: 0; padding-right: 0;">
    <v-subheader>내가 작성한 글</v-subheader>
    <v-list two-line subheader height="200" style="overflow-y: auto;">
      <v-list-item v-for="(post, index) in posts" :key="index">
        <v-list-item-avatar>
          <v-icon>mdi-post</v-icon>
        </v-list-item-avatar>

        <v-list-item-content>
          <v-list-item-title v-text="post.btitle"></v-list-item-title>
          <v-list-item-subtitle v-text="post.bdate"></v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-col>
</template>

<script>
export default {
  props: ['currPosts'],
  data() {
    return {
      posts: this.currPosts,
    };
  },
};
</script>

<style></style>
