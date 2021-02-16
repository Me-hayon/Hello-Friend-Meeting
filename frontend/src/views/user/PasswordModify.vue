<template>
  <div class="wrapC">
    <router-link to="/user/profile">
      <v-icon>mdi-arrow-left</v-icon>
    </router-link>
    <h4 style="text-align:right">더 안전한 비밀번호로 바꿔봐요!</h4>
    <div class="input-with-label" style="margin-bottom: 30px">
      <input
        v-model="password"
        type="password"
        v-bind:class="{
          error: error.password,
        }"
        id="password"
        placeholder="비밀번호를 입력하세요."
      />
      <label for="password">비밀번호</label>
      <div class="error-text" v-if="error.password">{{ error.password }}</div>
    </div>

    <div class="input-with-label" style="margin-bottom: 30px">
      <input
        v-model="newPassword"
        type="password"
        v-bind:class="{
          error: error.newPassword,
        }"
        id="newPassword"
        placeholder="새 비밀번호를 입력하세요."
      />
      <label for="newPassword">새 비밀번호</label>
      <div class="error-text" v-if="error.newPassword">
        {{ error.newPassword }}
      </div>
    </div>
    <div class="input-with-label" style="margin-bottom: 200px">
      <input
        v-model="passwordConfirm"
        type="password"
        v-bind:class="{
          error: error.passwordConfirm,
        }"
        id="password-confirm"
        placeholder="비밀번호를 다시한번 입력하세요."
      />
      <label for="password-confirm">비밀번호 확인</label>
      <div class="error-text" v-if="error.passwordConfirm">
        {{ error.passwordConfirm }}
      </div>
    </div>
    <div style="text-align: right">
      <b-button
        style="height: auto; margin-left: 10px"
        variant="success"
        @click="modify()"
        >변경하기</b-button
      >
    </div>
  </div>
</template>

<script>
import PV from 'password-validator';
import axios from 'axios';

const storage = window.sessionStorage;
let tmp;
export default {
  created() {
    tmp = this;
    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()
      .digits()
      .has()
      .letters();

    this.$store.commit('setIsHeader', true);
    this.$store.commit('setIsFooter', true);
  },
  data() {
    return {
      email: storage.getItem('user-email'),
      password: '',
      passwordSchema: new PV(),
      newPassword: '',
      passwordConfirm: '',
      error: {
        password: false,
        newPassword: false,
        passwordConfirm: false,
      },
      isSubmit: false,
      passwordRules: [
        (v) => !!v || '비밀번호를 입력해주세요.',
        (v) => (v && v.length >= 8) || '너무 짧으면 위험하지 않을까요? ㅠ.ㅠ',
        (v) => /(?=.*[A-Za-z])/.test(v) || '문자도 포함해볼까요?',
        (v) => /(?=.*\d)/.test(v) || '숫자를 꼭 포함해야 해요!',
        (v) =>
          /([!@$%])/.test(v) ||
          '특수문자를 통해 더 안전한 비밀번호를 만들어요! [!@#$%]',
      ],
      passwordConfirmRules: [
        (v) => !!v || '비밀번호를 입력해주세요.',
        (v) => v == tmp.password || '비밀번호와 일치해야해요.',
      ],
    };
  },
  watch: {
    password: function(v) {
      this.checkForm();
      this.checkComplete();
    },
    newPassword: function(v) {
      this.checkForm();
      this.checkComplete();
    },

    passwordConfirm: function(v) {
      this.checkForm();
      this.checkComplete();
    },
  },
  methods: {
    checkForm() {
      if (
        this.newPassword.length > 0 &&
        !this.passwordSchema.validate(this.newPassword)
      )
        this.error.newPassword = '영문, 숫자 포함 8자리 이상이어야 합니다.';
      else this.error.newPassword = false;

      if (
        this.passwordConfirm.length > 0 &&
        this.newPassword != this.passwordConfirm
      )
        this.error.passwordConfirm = '새 비밀번호가 일치하지 않습니다.';
      else this.error.passwordConfirm = false;
    },
    checkComplete() {
      if (this.password.length > 0 && this.passwordConfirm.length > 0) {
        let isSubmit = true;

        Object.values(this.error).map((v) => {
          if (v) isSubmit = false;
        });

        this.isSubmit = isSubmit;
      }
    },
    modify() {
      if (this.isSubmit) {
        this.isSubmit = false;

        let params = new URLSearchParams();
        params.append('email', this.email);
        params.append('oldPassword', this.password);
        params.append('newPassword', this.newPassword);

        axios
          .create({
            headers: {
              'auth-token': storage.getItem('auth-token'),
            },
          })
          .post('modify', params)
          .then(
            (response) => {
              if (response.data['is-success']) {
                this.isSubmit = true;
                storage.removeItem('auth-token');
                storage.removeItem('user-email');
                alert('비밀번호 변경이 완료되었습니다. 다시 로그인 해주세요.');
                this.$router.push('/');
              } else {
                this.password = '';
                this.newPassword = '';
                this.passwordConfirm = '';
                alert('비밀번호가 틀린것같아요 ㅠ.ㅠ');
              }
            },
            (error) => {
              this.isSubmit = true;
              alert('비밀번호 변경 도중 오류가 발생하였습니다.');
              this.$router.push('/error');
            }
          );
      }
    },
  },
};
</script>

<style>
#password-modify-window {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  background-color: #f8f9fa;
  border: 1px solid rgba(0, 0, 0, 0.125);
  width: 500px;
  padding: 50px;
}
</style>
