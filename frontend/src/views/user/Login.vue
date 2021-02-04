<template>
  <div class="user" id="login">
    <div class="wrapC">
      <h1 style="text-align: center; margin-top: 55px">우리 친구하자</h1>
      <img
        class="logo"
        style="display: block; margin: 0 auto 50px"
        src="@/assets/images/logo.png"
        alt=""
      />

      <div class="input-with-label">
        <input
          v-model="email"
          v-bind:class="{
            error: error.email,
            complete: !error.email && email.length !== 0,
          }"
          @keyup.enter="Login"
          id="email"
          placeholder="이메일을 입력하세요"
          type="text"
        />
        <label for="email">이메일</label>
        <div class="error-text" v-if="error.email">{{ error.email }}</div>
      </div>

      <div class="input-with-label">
        <input
          v-model="password"
          type="password"
          v-bind:class="{
            error: error.password,
            complete: !error.password && password.length !== 0,
          }"
          id="password"
          @keyup.enter="Login"
          placeholder="비밀번호를 입력하세요"
        />
        <label for="password">비밀번호</label>
        <div class="error-text" v-if="error.password">{{ error.password }}</div>
      </div>
      <button
        class="btn btn--back btn--login"
        @click="onLogin"
        :disabled="!isSubmit"
        :class="{ disabled: !isSubmit }"
        style="background-color: tomato; color: white"
      >
        로그인
      </button>
      <div class="add-option">
        <div class="text">
          <p>혹시</p>
          <div class="bar"></div>
        </div>
        <div class="wrap">
          <p>아직 회원이 아니신가요?</p>
          <router-link to="/user/join" class="btn--text">가입하기</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import "../../components/css/user.scss";
import PV from "password-validator";
import * as EmailValidator from "email-validator";
import axios from "axios";

const storage = window.sessionStorage;

export default {
  created() {
    if (storage.getItem("auth-token")) {
      this.$router.push("/feed/main");
    }

    this.component = this;

    this.passwordSchema
      .is()
      .min(8)
      .is()
      .max(100)
      .has()
      .digits()
      .has()
      .letters();

    this.$store.commit("setIsHeader", false);
    this.$store.commit("setIsFooter", false);
  },
  watch: {
    password: function(v) {
      this.checkForm();
    },
    email: function(v) {
      this.checkForm();
    },
  },
  methods: {
    checkForm() {
      if (this.email.length > 0 && !EmailValidator.validate(this.email))
        this.error.email = "이메일 형식이 아닙니다.";
      else this.error.email = false;

      if (
        this.password.length > 0 &&
        !this.passwordSchema.validate(this.password)
      )
        this.error.password = "영문, 숫자 포함 8자리 이상이어야 합니다.";
      else this.error.password = false;

      let isSubmit = true;
      Object.values(this.error).map((v) => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    onLogin() {
      if (this.isSubmit) {
        this.email = this.email.toLowerCase();
        this.isSubmit = false;
        console.log(this.email);
        console.log(this.password);

        var params = new URLSearchParams();
        params.append("email", this.email);
        params.append("password", this.password);

        axios
          .post("http://localhost:8080/login", params)
          .then((response) => {
            this.isSubmit = true;
            if (response.data["is-success"]) {
              storage.setItem("auth-token", response.data["auth-token"]);
              storage.setItem("user-email", response.data["user-email"]);
              this.$router.push("/feed/main");
            } else {
              alert("아이디 또는 비밀번호를 잘못 입력하였습니다.");
            }
          })
          .catch((error) => {
            this.isSubmit = true;
            alert("로그인 도중 오류가 발생하였습니다.");
          });
      }
    },
  },
  data: () => {
    return {
      email: "",
      password: "",
      passwordSchema: new PV(),
      error: {
        email: false,
        password: false,
      },
      isSubmit: false,
      component: this,
    };
  },
};
</script>
