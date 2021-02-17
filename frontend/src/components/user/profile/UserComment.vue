<template>
  <v-col cols="6" style="padding-top: 0; padding-left: 0; padding-right: 0;">
    <v-subheader>내가 작성한 댓글</v-subheader>

    <v-list
      v-if="comments != null"
      two-line
      subheader
      dense
      flat
      height="200"
      style="overflow-y: auto;"
    >
      <v-list-item-group v-model="commentListSelect">
        <v-list-item
          v-for="(comment, index) in comments"
          :key="index"
          :value="comment"
        >
          <v-list-item-avatar>
            <v-icon>mdi-comment-processing-outline</v-icon>
          </v-list-item-avatar>

          <v-list-item-content>
            <v-list-item-title v-text="comment.ccontent"></v-list-item-title>
            <v-list-item-subtitle v-text="comment.cdate"></v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </v-list-item-group>
    </v-list>

    <v-row
      v-else
      justify="center"
      align="center"
      style="height: 200px;"
      no-gutters
    >
      <p>작성한 댓글이 없습니다.</p>
    </v-row>
  </v-col>
</template>

<script>
export default {
  props: ['currComments'],
  data() {
    return {
      comments: this.currComments,
      commentListSelect: null,
    };
  },
};
</script>

<style></style>
