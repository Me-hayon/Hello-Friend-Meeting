<template>
  <div>
    그룹멤버리스트
    <ul v-if="memberStatus === 4">
      <li v-for="applier in applierList" :key="applier.uno">
        {{ applier.uname }}
        <button @click="acceptApplier(applier.uno)">가입신청수락</button>
        <button @click="denyApplier(applier.uno)">가입신청거절</button>
      </li>
    </ul>
    <hr />
    <ul v-if="memberStatus === 3 || memberStatus === 4">
      <li v-for="member in memberList" :key="member.uno">
        {{ member.uname }}
        <button
          @click="banishMember(member.uno)"
          v-if="memberStatus === 4 && email != member.email"
        >
          추방
        </button>
        <button
          @click="changeGmaster(member.uno)"
          v-if="memberStatus === 4 && email != member.email"
        >
          새 그룹장으로 임명
        </button>
        <button v-if="email === member.email" @click="getoutGroup()">
          탈퇴
        </button>
      </li>
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
      email: window.sessionStorage.getItem("user-email"),
    };
  },
  created() {
    var params = new URLSearchParams();
    params.append("email", this.email);
    params.append("gno", this.gno);
    axios
      .post("isGroupMember", params)
      .then((response) => {
        var memberStatus = response.data.memberStatus;
        var gno = this.gno;
        this.$store.commit("setGno", gno);
        this.$store.commit("setMemberStatus", memberStatus);
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
      params.append("gno", this.gno);
      axios
        .post("getUserListInGroup", params)
        .then((response) => {
          this.memberList = response.data.userList;
        })
        .catch((error) => {
          console.log(error);
        });
      axios
        .post("getGroupApplier", params)
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
      params.append("uno", uno);
      params.append("gno", this.gno);
      axios
        .post("acceptApplyGroup", params)
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
      params.append("uno", uno);
      params.append("gno", this.gno);
      axios
        .post("denyApplyGroup", params)
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
      params.append("uno", uno);
      params.append("gno", this.gno);
      axios
        .post("banishMember", params)
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
        alert("다른 사람을 그룹장으로 임명하고 다시 시도해주세요.");
      } else {
        var params = new URLSearchParams();
        params.append("email", this.email);
        params.append("gno", this.gno);
        axios
          .post("getoutGroup", params)
          .then((response) => {
            alert(response.data.data);
            this.$router.push("/");
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
    changeGmaster(uno) {
      var params = new URLSearchParams();
      params.append("nextMaster", uno);
      params.append("gno", this.gno);
      axios
        .post("changeGroupMaster", params)
        .then((response) => {
          alert(response.data.data);
          params = new URLSearchParams();
          params.append("email", this.email);
          params.append("gno", this.gno);
          axios
            .post("isGroupMember", params)
            .then((response) => {
              var memberStatus = response.data.memberStatus;
              var gno = this.gno;
              this.$store.commit("setGno", gno);
              this.$store.commit("setMemberStatus", memberStatus);
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
