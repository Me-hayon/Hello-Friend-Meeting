<template>
  <b-container style="background-color: rgb(247, 246, 232)">
    <FriendProfile :friendEmail="friendEmail" />
    <div>
      <table
        class="table b-table table-striped table-hover"
        style="width:100%; text-align:center; margin-top:30px"
      >
        <tr>
          <td>그룹명</td>
          <td>그룹신청</td>
          <td>그룹원수</td>
        </tr>
        <tr v-for="group in groups" :key="group.gno">
          <td>{{ group.gname }}</td>
          <td><b-button variant="success">신청하기</b-button></td>
          <td>{{ group.gpeople }}</td>
        </tr>
        <!-- <tr>
          <td>볼링</td>
          <td><b-button>신청하기</b-button></td>
          <td>17</td>
        </tr>
        <tr>
          <td>영화</td>
          <td><b-button>신청하기</b-button></td>
          <td>25</td>
        </tr> -->
      </table>
    </div>
  </b-container>
</template>

<script>
import FriendProfile from '@/components/user/FriendProfile.vue';

const storage = window.sessionStorage;

export default {
  created() {
    this.$store.state.isHeader = true;
    this.$store.state.isFooter = true;
  },
  props: {
    friendEmail: {
      type: String,
      default: '',
    },
  },
  data() {
    return {
      groups: [
        { gname: '토익스터디', gno: 1, gpeople: 20 },
        { gname: '볼링', gno: 'Larsen', gpeople: 31 },
      ],
    };
  },
  components: { FriendProfile },
  methods: {
    onClickState() {
      storage.removeItem('auth-token');
      storage.removeItem('user-email');
      this.$router.push('/');
    },
  },
};
</script>

<style></style>
