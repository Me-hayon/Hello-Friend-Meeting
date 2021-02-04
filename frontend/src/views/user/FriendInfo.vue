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
          <td>
            <b-button variant="success" @click="applyGroup(group.gno)"
              >신청하기</b-button
            >
          </td>
          <td>{{ group.members }}</td>
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
import axios from 'axios';

const storage = window.sessionStorage;

export default {
  created() {
    this.$store.commit('setIsHeader', true);
    this.$store.commit('setIsFooter', true);

    var params = new URLSearchParams();
    params.append('email', this.friendEmail);
    axios
      .post('http://localhost:8080/getGroupList', params)
      .then((response) => {
        this.groups = response.data.groupList;
        for (var i = 0; i < this.groups.length; i++) {
          this.groups[i].members =
            this.groups[i].guserList.split(' ').length - 1;
        }
      })
      .catch((error) => {
        console.log(error);
      });
  },
  props: {
    friendEmail: {
      type: String,
      default: '',
    },
  },
  data() {
    return {
      groups: [],
    };
  },
  components: { FriendProfile },
  methods: {
    onClickState() {
      storage.removeItem('auth-token');
      storage.removeItem('user-email');
      this.$router.push('/');
    },
    applyGroup(gno) {
      var storage = window.sessionStorage;
      var params = new URLSearchParams();
      params.append('email', storage.getItem('user-email'));
      params.append('gno', gno);
      axios
        .post('http://localhost:8080/applyGroup', params)
        .then((response) => {
          alert(response.data.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style></style>
