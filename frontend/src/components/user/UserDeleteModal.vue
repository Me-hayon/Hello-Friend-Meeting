<template>
  <b-modal
    id="user-delete-modal"
    ref="modal"
    title="정말 계정을 탈퇴하시겠습니까?"
    centered
    @show="reset"
    @hidden="reset"
    @ok="deleteUser"
  >
    <b-form-input
      type="password"
      placeholder="비밀번호 입력"
      v-model="password"
      required
    ></b-form-input>
  </b-modal>
</template>

<script>
import axios from 'axios';

const storage = window.sessionStorage;

export default {
  data() {
    return {
      password: '',
    };
  },
  methods: {
    reset() {
      this.password = '';
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

      this.$bvModal.hide('user-delete-modal');
    },
  },
};
</script>

<style></style>
