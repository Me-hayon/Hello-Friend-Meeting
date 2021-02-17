<template>
  <v-col cols="6" style="padding-top: 0; padding-left: 0; padding-right: 0;">
    <v-subheader>내가 작성한 댓글</v-subheader>

    <v-list
      v-if="comments != null"
      two-line
      subheader
      flat
      height="200"
      style="overflow-y: auto;"
    >
      <v-list-item
        v-for="(comment, index) in comments"
        :key="index"
        @click="moveComment(index)"
      >
        <v-list-item-avatar>
          <v-icon>mdi-comment-processing-outline</v-icon>
        </v-list-item-avatar>

        <v-list-item-content>
          <v-list-item-title v-text="comment.ccontent"></v-list-item-title>
          <v-list-item-subtitle v-text="comment.cdate"></v-list-item-subtitle>
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
      <p>작성한 댓글이 없습니다.</p>
    </v-row>
  </v-col>
</template>

<script>
import axios from 'axios';

export default {
  props: ['currComments'],
  data() {
    return {
      comments: this.currComments,
    };
  },
  methods: {
    moveComment(index) {
      axios
        .post('findGnoByBno', { bno: this.comments[index].bno })
        .then((response) => {
          if (response.data['is-success']) {
            let gno = response.data.gno;
            let params = new URLSearchParams();

            params.append('email', window.sessionStorage.getItem('user-email'));
            params.append('gno', this.comments[index].bno);

            axios
              .post('isGroupMember', params)
              .then((response) => {
                this.$store.commit('setGno', gno);
                this.$store.commit('setBno', this.comments[index].bno);
                this.$store.commit(
                  'setMemberStatus',
                  response.data.memberStatus
                );
                this.$router.push('/board/detail');
              })
              .catch((error) => {
                console.log(error);
              });
          } else alert('오류가 발생하였습니다.');
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style></style>
