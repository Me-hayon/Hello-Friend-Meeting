<template>
  <b-container style="background-color: rgb(247, 246, 232)">
    <FriendProfile />
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
  computed: {
    vuexUno() {
      return this.$store.getters.getUno;
    },
  },
  watch: {
    vuexUno(val) {
      this.uno = val;
      var params = new URLSearchParams();
      params.append('uno', this.uno);
      axios
        .post('findEmailByUno', params)
        .then((resp) => {
          if (!resp.data.isPresent) {
            alert('삭제된 사용자입니다.');
            this.$router.push('/');
            return;
          }
          params = new URLSearchParams();
          this.friendEmail = resp.data.data;
          params.append('email', this.friendEmail);
          axios
            .post('profile', params)
            .then((response) => {
              this.fname = response.data['user-name'];
              this.tel = response.data['user-tel'];
              this.profileImg = response.data['profile-img'];

              params = new URLSearchParams();
              params.append('email', this.friendEmail);
              axios
                .post('getGroupList', params)
                .then((response1) => {
                  this.groups = response1.data.groupList;
                  for (var i = 0; i < this.groups.length; i++) {
                    this.groups[i].members =
                      this.groups[i].guserList.split(' ').length - 1;
                  }
                })
                .catch((error) => {
                  console.log(error);
                });
            })
            .catch((error) => {
              console.log(error);
            });
          this.friendCheck();
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  created() {
    this.$store.commit('setIsHeader', true);
    this.$store.commit('setIsFooter', true);

    var params = new URLSearchParams();
    params.append('uno', this.uno);
    axios.post('findEmailByUno', params).then((resp) => {
      this.friendEmail = resp.data.data;
      params = new URLSearchParams();
      params.append('email', this.friendEmail);
      axios
        .post('getGroupList', params)
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
    });
  },
  data() {
    return {
      groups: [],
      friendEmail: '',
      uno: this.$store.getters.getUno,
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
        .post('applyGroup', params)
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
