<template>
  <div>
    그룹 멤버
    <ul style="margin:0; padding:0;" v-if="memberStatus === 4">
      <h5 style="margin:30px;">가입신청자</h5>
      <ol
        style="height:70px; position:relative; 
        
        margin:0; 
        "
        v-for="applier in applierList"
        :key="applier.uno"
      >
        <div>
          <img
            style="position:absolute;
                top:50%; left:9%;
                transform: translate(-50%, -50%);"
            width="50px;"
            :src="require(`@/assets/images/avatars/${applier.uprofileImg}.png`)"
          />
          <h6
            style="position:absolute;
          top:50%; left:20%;
          transform: translate(0%, -50%);"
          >
            {{ applier.uname }}
          </h6>

          <div
            style="position:absolute; top:50%; right:3%;
                transform: translate(0%, -50%);"
          >
            <v-btn @click="acceptApplier(applier.uno)">수락</v-btn>
            <v-btn @click="denyApplier(applier.uno)">거절</v-btn>
          </div>
        </div>
      </ol>
    </ul>
    <hr />

    <ul
      style="margin:0; padding:0;"
      v-if="memberStatus === 3 || memberStatus === 4"
    >
      <ol
        style="margin: 0; padding:0; "
        v-for="member in memberList"
        :key="member.uno"
      >
        <v-dialog v-model="dialog" width="300">
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              style="color:black; height:60px; width:100%;"
              color="blue"
              dark
              v-bind="attrs"
              v-on="on"
            >
              <img
                style="position:absolute;
                top:50%; left:5%;
                transform: translate(-50%, -50%);"
                width="50px;"
                :src="
                  require(`@/assets/images/avatars/${member.uprofileImg}.png`)
                "
              />
              <h6
                style="position:absolute;
                top:50%; left:20%;
                transform: translate(0%, -50%);"
              >
                {{ member.uname }}
              </h6>
              <h6 v-if="email == member.email">나ㅋ</h6>
              <v-icon v-if="member.memberStatus == 4">mdi-arrow-right</v-icon>
            </v-btn>
          </template>
          <v-card>
            <v-card-title class="headline grey lighten-2"> 관리 </v-card-title>
            <div
              style="height:100px; display:flex; justify-content: center; align-items:center"
            >
              <v-btn
                @click="banishMember(member.uno)"
                v-if="memberStatus === 4 && email != member.email"
              >
                추방
              </v-btn>
              <v-btn
                @click="changeGmaster(member.uno)"
                v-if="memberStatus === 4 && email != member.email"
              >
                새 그룹장으로 임명
              </v-btn>
              <v-btn v-if="email === member.email" @click="getoutGroup()">
                탈퇴
              </v-btn>
            </div></v-card
          >
        </v-dialog>
      </ol>
    </ul>
  </div>
</template>

<script>
import axios from "axios";

export default {
  computed: {
    vuexGno() {
      return this.$store.getters.getGno;
    },
    vuexUno() {
      return this.$store.getters.getUno;
    },
    vuexBno() {
      return this.$store.getters.getBno;
    },
    vuexMemberStatus() {
      return this.$store.getters.getMemberStatus;
    },
  },
  watch: {
    vuexGno(val) {
      this.gno = val;
      this.getUsers();
    },
    vuexUno(val) {
      this.uno = val;
    },
    vuexBno(val) {
      this.bno = val;
    },
    vuexMemberStatus(val) {
      this.memberStatus = val;
      this.getUsers();
    },
  },
  data() {
    return {
      memberStatus: this.$store.getters.getMemberStatus,
      gno: this.$store.getters.getGno,
      uno: this.$store.getters.getUno,
      memberList: [],
      applierList: [],
      email: window.sessionStorage.getItem('user-email'),
    };
  },
  created() {
    var params = new URLSearchParams();
    params.append('email', this.email);
    params.append('gno', this.gno);
    axios
      .post('isGroupMember', params)
      .then((response) => {
        var memberStatus = response.data.memberStatus;
        var gno = this.gno;
        this.$store.commit('setGno', gno);
        this.$store.commit('setMemberStatus', memberStatus);
        console.log(this.memberStatus);
        this.getUsers();
      })
      .catch((error) => {
        console.log(error);
      });
  },
  methods: {
    getUsers() {
      var params = new URLSearchParams();
      params.append('gno', this.gno);
      axios
        .post('getUserListInGroup', params)
        .then((response) => {
          this.memberList = response.data.userList;
          console.log(this.memberList);
        })
        .catch((error) => {
          console.log(error);
        });
      axios
        .post('getGroupApplier', params)
        .then((response) => {
          this.applierList = response.data.applierList;
          console.log(this.applierList);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    acceptApplier(uno) {
      var params = new URLSearchParams();
      params.append('uno', uno);
      params.append('gno', this.gno);
      axios
        .post('acceptApplyGroup', params)
        .then((response) => {
          alert(response.data.data);
          this.getUsers();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    denyApplier(uno) {
      var params = new URLSearchParams();
      params.append('uno', uno);
      params.append('gno', this.gno);
      axios
        .post('denyApplyGroup', params)
        .then((response) => {
          alert(response.data.data);
          this.getUsers();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    banishMember(uno) {
      var params = new URLSearchParams();
      params.append('uno', uno);
      params.append('gno', this.gno);
      axios
        .post('banishMember', params)
        .then((response) => {
          alert(response.data.data);
          this.getUsers();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getoutGroup() {
      if (this.memberStatus === 4) {
        alert('다른 사람을 그룹장으로 임명하고 다시 시도해주세요.');
      } else {
        var params = new URLSearchParams();
        params.append('email', this.email);
        params.append('gno', this.gno);
        axios
          .post('getoutGroup', params)
          .then((response) => {
            alert(response.data.data);
            this.$router.push('/');
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
    changeGmaster(uno) {
      var params = new URLSearchParams();
      params.append('nextMaster', uno);
      params.append('gno', this.gno);
      axios
        .post('changeGroupMaster', params)
        .then((response) => {
          alert(response.data.data);
          params = new URLSearchParams();
          params.append('email', this.email);
          params.append('gno', this.gno);
          axios
            .post('isGroupMember', params)
            .then((response) => {
              var memberStatus = response.data.memberStatus;
              var gno = this.gno;
              this.$store.commit('setGno', gno);
              this.$store.commit('setMemberStatus', memberStatus);
            })
            .catch((error) => {
              console.log(error);
            });
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style></style>
