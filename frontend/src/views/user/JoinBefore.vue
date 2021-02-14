<template>
  <!-- 전체 -->
  <v-card color="white">
    <!-- 이미지 상단 타이틀 -->
    <v-img
      gradient="to top right, rgba(0,0,0,.8), rgba(25,32,72,.2)"
      height="300"
      src="@/assets/images/friend.jpg"
    >
      <v-row style="margin-top: 0px; color: white;">
        <v-col>
          <v-row
            style="margin-top: 150px; margin-left: 15px; letter-spacing: -1px;"
          >
            <h1>{{ titleName }}</h1>
          </v-row>
          <v-row
            style="margin-left: 15px; margin-right: 15px; letter-spacing: -1px;"
          >
            새로운 친구의 친구가 되러 와주셨네요. <br />
            아래의 칸이 모두 채워지면 새 친구가 될 수 있어요 :)
          </v-row>
        </v-col>
      </v-row>
    </v-img>

    <v-row justify="center" style="margin: 0px; ">
      <v-col cols="12" sm="10" md="8" lg="6" ref="form">
        <v-card-text>
          <v-text-field
            ref="name"
            v-model="name"
            :rules="nameRules"
            :error-messages="errorMessages"
            label="내 이름은..."
            placeholder="찬찬규"
            counter="10"
            required
          ></v-text-field>
          <v-row justify="center">
            <v-col
              ><v-text-field
                ref="emailFront"
                v-model="emailFront"
                :rules="[() => !!emailFront || 'This field is required']"
                label="내 이메일 주소는..."
                placeholder="ID"
                required
              ></v-text-field
            ></v-col>
            <v-col
              cols="1"
              align="center"
              align-self="center"
              style="margin: 0px; padding: 0px;"
              >@</v-col
            >
            <v-col>
              <v-autocomplete
                ref="emailBack"
                v-model="emailBack"
                :rules="[() => !!emailBack || 'This field is required']"
                :items="emailBacks"
                label=""
                placeholder="선택..."
                required
              >
              </v-autocomplete>
            </v-col>
            <v-col>
              <v-btn small color="success">인증번호 받기</v-btn>
            </v-col>
          </v-row>

          <v-text-field
            ref="password"
            v-model="password"
            :rules="passwordRules"
            label="쉿! 비밀번호는..."
            placeholder="●●●●●●●●"
            required
          ></v-text-field>
          <v-text-field
            ref="passwordConfirm"
            v-model="passwordConfirm"
            :rules="passwordConfirmRules"
            label="비밀번호 다시..."
            required
            placeholder="●●●●●●●●"
          ></v-text-field>
          <v-row style="margin-left: 0; margin-right: 0; margin-top: 5px;">
            <v-col>
              <v-row>
                <v-text-field
                  ref="tel"
                  v-model="tel"
                  :rules="[() => !!tel || 'This field is required']"
                  label="내 휴대폰 번호는..."
                  required
                  hint="-없이 숫자만 입력"
                  placeholder="01012345678"
                ></v-text-field
              ></v-row>
              <v-row>
                <v-text-field
                  ref="tel"
                  v-model="tel"
                  :rules="[() => !!tel || 'This field is required']"
                  label="내 휴대폰 번호는..."
                  required
                  hint="-없이 숫자만 입력"
                  placeholder="01012345678"
                ></v-text-field>
              </v-row>
            </v-col>
            <v-col cols="4" style="padding: 0;" align-self="center">
              <v-row style="margin: 10px;"
                ><v-btn small color="success">인증번호 받기</v-btn></v-row
              >
              <v-row style="margin: 10px;"
                ><v-btn small color="success">다시 받기</v-btn></v-row
              >
            </v-col>
          </v-row>
        </v-card-text>
        <v-divider class="mt-12"></v-divider>
        <v-card-actions>
          <v-btn text>Cancel</v-btn>
          <v-spacer></v-spacer>
          <!-- <v-slide-x-reverse-transition>
            <v-tooltip v-if="formHasErrors" left>
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  icon
                  class="my-0"
                  v-bind="attrs"
                  @click="resetForm"
                  v-on="on"
                >
                  <v-icon>mdi-refresh</v-icon>
                </v-btn>
              </template>
              <span>Refresh form</span>
            </v-tooltip>
          </v-slide-x-reverse-transition> -->
          <v-btn color="primary" text @click="submit">Submit</v-btn>
        </v-card-actions>
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
let tmp;
export default {
  created() {
    tmp = this;
  },
  data: () => ({
    emailFront: '',
    emailBack: '',
    emailBacks: [
      'naver.com',
      'gmail.com',
      'hanmail.net',
      'ssafy.com',
      '직접 입력',
    ],
    errorMessages: '',
    titleName: '반가워요!',
    name: '',
    password: '',
    passwordConfirm: '',
    nameRules: [
      (v) => !!v || 'This field is required',
      (v) => (!!v && v.length <= 10) || '이름은 10자 이내로 멋지게 지어주세요!',
    ],
    passwordRules: [
      (v) => !!v || '비밀번호를 입력해주세요.',
      (v) => (v && v.length >= 5) || 'Password must have 5+ characters',
      // (v) => /(?=.*[A-Z])/.test(v) || 'Must have one uppercase character',
      (v) => /(?=.*\d)/.test(v) || 'Must have one number',
      (v) => /([!@$%])/.test(v) || 'Must have one special character [!@#$%]',
    ],
    passwordConfirmRules: [
      (v) => !!v || '비밀번호를 입력해주세요.',
      (v) => v == tmp.password || '비밀번호와 일치해야해요.',
    ],
    tel: null,
    auth: null,
    formHasErrors: false,
  }),

  computed: {
    form() {
      return {
        name: this.name,
        emailFront: this.emailFront,
        emailBack: this.emailBack,
        password: this.password,
        tel: this.tel,
      };
    },
  },

  watch: {
    name(v, w) {
      this.errorMessages = '';
      if (this.name.length == 0) {
        this.titleName = '반가워요!';
      } else {
        this.titleName = this.name + '님!';
      }

      if (this.name.length > 10) {
        this.name = w;
        // console.log(this.name);
      }
      console.log('old: ' + v);
      console.log('nnnnnnnn:  ' + w);
    },
  },

  methods: {
    addressCheck() {
      this.errorMessages =
        this.address && !this.name ? "Hey! I'm required" : '';

      return true;
    },
    resetForm() {
      this.errorMessages = [];
      this.formHasErrors = false;

      Object.keys(this.form).forEach((f) => {
        this.$refs[f].reset();
      });
    },
    submit() {
      this.formHasErrors = false;

      Object.keys(this.form).forEach((f) => {
        if (!this.form[f]) this.formHasErrors = true;
        // this.$refs.form.validate();
        this.$refs[f].validate(true);
      });
    },
  },
};
</script>
<style></style>
