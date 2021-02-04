<template>
  <v-container>
    <v-row style="margin-top: 100px;">
      <v-col align="center">
        <p class="font-weight-black" style="font-size: 2.5rem;">
          우리 친구하자
        </p>
      </v-col>
    </v-row>
    <v-row>
      <v-col align="center">
        <img src="@/assets/images/logo.png" />
      </v-col>
    </v-row>
    <v-row style="margin-top: 30px;">
      <v-col style="padding-left: 40px; padding-right: 40px;">
        <v-form ref="form" v-model="valid" lazy-validation>
          <v-text-field
            v-model="email"
            label="이메일"
            hint="@를 포함해주세요."
            outlined
            rounded
            clearable
            required
            :rules="emailRules"
          ></v-text-field>

          <v-text-field
            v-model="password"
            label="비밀번호"
            outlined
            rounded
            clearable
            required
            :type="passwordShow ? 'text' : 'password'"
            :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
            @click:append="passwordShow = !passwordShow"
          ></v-text-field>

          <v-btn
            :disabled="!valid"
            width="100%"
            color="success"
            class="font-weight-black"
            @click="validate"
          >
            로그인
          </v-btn>
        </v-form>
      </v-col>
    </v-row>
    <v-divider></v-divider>
    <v-row style="padding-left: 40px; padding-right: 40px;">
      <v-col style="padding-left: 0; padding-right: 0;" cols="8">
        <p class="font-weight-bold">혹시 아직 회원이 아니신가요?</p>
      </v-col>
      <v-col style="padding-left: 0; padding-right: 0;" align="end">
        <router-link to="/user/join" class="font-weight-bold"
          >가입하기</router-link
        >
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from 'axios';

const storage = window.sessionStorage;

export default {
  data() {
    return {
      valid: false,
      email: '',
      password: '',
      emailRules: [
        (v) =>
          /.+@.+\..+/.test(v) ||
          v == null ||
          v.length == 0 ||
          '올바른 이메일 형식을 입력해주세요.',
      ],
      passwordShow: false,
    };
  },
  created() {
    if (storage.getItem('auth-token')) {
      this.$router.push('/feed/main');
    }

    this.$store.commit('setIsHeader', false);
    this.$store.commit('setIsFooter', false);
  },
  methods: {
    validate() {
      if (this.$refs.form.validate()) this.login();
    },
    login() {
      var params = new URLSearchParams();
      params.append('email', this.email);
      params.append('password', this.password);

      axios
        .post('http://localhost:8080/login', params)
        .then((response) => {
          if (response.data['is-success']) {
            storage.setItem('auth-token', response.data['auth-token']);
            storage.setItem('user-email', response.data['user-email']);
            this.$router.push('/feed/main');
          } else {
            alert('아이디 또는 비밀번호를 잘못 입력하였습니다.');
          }
        })
        .catch((error) => {
          alert('로그인 도중 오류가 발생하였습니다.');
        });
    },
  },
  watch: {
    valid(valid) {
      if (valid) {
        if (this.email == null || this.email.length == 0) this.valid = false;
        else if (this.password == null || this.password.length == 0)
          this.valid = false;
      }
    },
    email(email) {
      if (email == null || email.length == 0) this.valid = false;
    },
    password(password) {
      if (this.valid && (password == null || password.length == 0))
        this.valid = false;
      else if (!this.valid && password.length > 0) this.valid = true;
    },
  },
};
</script>
