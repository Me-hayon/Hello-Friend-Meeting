<template>
  <v-row>
    <v-col v-if="friendStatus != -1">
      <v-btn v-if="friendStatus == 0" color="primary" block>
        친구 요청
      </v-btn>

      <v-btn v-else-if="friendStatus == 1" color="warning" block>
        친구 요청 취소
      </v-btn>

      <v-btn v-else-if="friendStatus == 2" color="success" block>
        친구 요청 수락
      </v-btn>

      <v-btn
        v-else-if="friendStatus == 3"
        color="error"
        @click="deleteModal = true"
        block
      >
        친구 삭제
      </v-btn>
    </v-col>
    <v-col v-if="friendStatus == 2">
      <v-btn color="warning" block>
        친구 요청 거절
      </v-btn>
    </v-col>

    <!-- 친구 삭제 모달 -->
    <v-dialog v-model="deleteModal" max-width="270px" persistent>
      <v-card>
        <v-card-title>
          <span>정말 삭제하시겠습니까?</span>
        </v-card-title>
        <v-card-actions>
          <v-row class="ma-0" justify="end">
            <v-btn
              color="error"
              class="font-weight-black"
              style="margin-right: 10px;"
              @click="deleteFriend"
            >
              확인
            </v-btn>
            <v-btn color="success" @click="deleteModal = false">
              취소
            </v-btn>
          </v-row>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import axios from 'axios';

export default {
  props: ['uno', 'info'],
  data() {
    return {
      friendStatus: -1,
      deleteModal: false,
    };
  },
  created() {
    axios
      .post('isFriend', { myUno: this.uno, friendUno: this.info.uno })
      .then((response) => {
        this.friendStatus = response.data;
      })
      .catch((error) => {
        console.log(error);
      });
  },
  methods: {
    deleteFriend() {
      axios
        .post('deleteFriend', { myUno: this.uno, friendUno: this.info.uno })
        .then((response) => {
          if (response.data['is-success']) {
            this.$emit('deleteFriend', this.info.uno);
            this.friendStatus = 0;
            this.deleteModal = false;
          } else alert('친구를 삭제하는데 오류가 발생하였습니다.');
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style></style>
