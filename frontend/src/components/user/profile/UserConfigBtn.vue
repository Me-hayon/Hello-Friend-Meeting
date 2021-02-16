<template>
  <div>
    <v-speed-dial
      v-model="userConfigFab"
      absolute
      top
      right
      direction="left"
      transition="slide-x-reverse-transition"
    >
      <template v-slot:activator>
        <v-btn v-model="userConfigFab" fab icon dark>
          <v-icon size="25" color="black">mdi-cog</v-icon>
        </v-btn>
      </template>

      <!-- 회원탈퇴 -->
      <v-btn fab dark small color="red" @click="deleteModal = true">
        <v-icon>mdi-account-remove</v-icon>
      </v-btn>

      <!-- 비밀번호 변경 -->
      <v-btn fab dark small color="indigo" to="/user/modify">
        <v-icon>mdi-lock-reset</v-icon>
      </v-btn>

      <!-- 로그아웃 -->
      <v-btn fab dark small color="green" @click="logout">
        <v-icon>mdi-logout</v-icon>
      </v-btn>
    </v-speed-dial>

    <!-- 회원탈퇴 모달 -->
    <v-dialog v-model="deleteModal" persistent>
      <v-card>
        <v-card-title>
          <span>회원탈퇴</span>
          <v-spacer></v-spacer>
          <v-btn icon @click="deleteModal = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-card-title>
        <v-card-text>
          <v-text-field
            v-model="password"
            placeholder="비밀번호를 입력해주세요."
            clearable
            required
            :type="passwordShow ? 'text' : 'password'"
            :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
            @click:append="passwordShow = !passwordShow"
          ></v-text-field>
        </v-card-text>
        <v-card-actions style="padding-top: 0;">
          <v-row class="ma-0" justify="end">
            <v-btn
              color="warning"
              class="font-weight-black"
              :disabled="!deleteUserValid"
              @click="deleteAlertModal = !deleteAlertModal"
            >
              탈퇴하기
            </v-btn>
          </v-row>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- 회원탈퇴 경고 모달 -->
    <v-dialog v-model="deleteAlertModal" width="270" persistent>
      <v-card>
        <v-card-title>
          <span>정말 탈퇴하시겠습니까?</span>
        </v-card-title>
        <v-card-actions>
          <v-row class="ma-0" justify="end">
            <v-btn
              color="error"
              class="font-weight-black"
              style="margin-right: 10px;"
              :disabled="!deleteUserValid"
              @click="deleteUser"
            >
              확인
            </v-btn>
            <v-btn color="success" @click="deleteAlertModal = false">
              취소
            </v-btn>
          </v-row>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import axios from 'axios';
const storage = window.sessionStorage;

export default {
  data() {
    return {
      userConfigFab: false,
      deleteModal: false,
      deleteAlertModal: false,
      password: '',
      passwordShow: false,
      deleteUserValid: false,
    };
  },
  methods: {
    logout() {
      storage.removeItem('auth-token');
      storage.removeItem('user-email');
      this.$router.push('/');
    },
    deleteUser() {
      let params = new URLSearchParams();
      params.append('email', storage.getItem('user-email'));
      params.append('password', this.password);

      axios
        .post('delete', params)
        .then((response) => {
          if (response.data['is-success']) {
            storage.removeItem('auth-token');
            storage.removeItem('user-email');
            alert('회원탈퇴가 완료되었습니다. 로그인 페이지로 이동합니다.');
            this.$router.push('/');
          } else {
            alert('잘못된 비밀번호를 입력하였습니다.');
            this.deleteAlertModal = false;
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  watch: {
    deleteModal(deleteModal) {
      if (!deleteModal) this.password = '';
    },
    password(password) {
      console.log(password);
      if (password == null || password.length == 0)
        this.deleteUserValid = false;
      else this.deleteUserValid = true;
    },
  },
};
</script>

<style></style>
