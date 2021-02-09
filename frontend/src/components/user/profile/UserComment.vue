<template>
  <v-col cols="6" style="padding-top: 0; padding-left: 0; padding-right: 0;">
    <v-subheader>내가 작성한 댓글</v-subheader>
    <v-list two-line subheader dense height="200" style="overflow-y: auto;">
      <v-list-item v-for="(comment, index) in comments" :key="index">
        <v-list-item-avatar>
          <v-icon>mdi-comment-processing-outline</v-icon>
        </v-list-item-avatar>

        <v-list-item-content>
          <v-list-item-title v-text="comment.ccontent"></v-list-item-title>
          <v-list-item-subtitle v-text="comment.cdate"></v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-col>
</template>

<script>
export default {
  props: ['currComments'],
  data() {
    return {
      comments: this.currComments,
    };
  },
};
</script>

<style></style>
