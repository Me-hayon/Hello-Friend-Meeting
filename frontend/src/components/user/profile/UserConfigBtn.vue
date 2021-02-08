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
      <v-btn fab dark small color="red" @click="modal = true">
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
    <v-dialog v-model="modal" persistent max-width="290">
      <v-card>
        <v-card-title>
          <span>회원탈퇴</span>
          <v-spacer></v-spacer>
          <v-btn icon @click="modal = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-card-title>
        <v-card-text>
          <v-text-field
            v-model="password"
            hint="비밀번호를 입력해주세요."
            single-line
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
              color="error"
              class="font-weight-black"
              :disabled="!valid"
              @click="deleteUser"
            >
              탈퇴하기
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
      modal: false,
      password: '',
      passwordShow: false,
      valid: false,
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
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  watch: {
    password(password) {
      console.log(password);
      if (password == null || password.length == 0) this.valid = false;
      else this.valid = true;
    },
  },
};
</script>

<style></style>
