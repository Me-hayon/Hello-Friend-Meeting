<template>
  <b-row>
    <b-col style="padding-left: 0; padding-right: 0">
      <b-row
        v-for="friend in friends"
        :key="friend.fno"
        style="border-bottom: 1px solid black;"
        @click="toFriendProfile(friend.email)"
      >
        <b-col cols="3" style="padding-left: 0; padding-right: 0"
          ><img
            width="50"
            :src="require(`@/assets/images/avatars/${friend.uprofileImg}.png`)"
        /></b-col>
        <b-col cols="9" style="padding-left: 0; padding-right: 0">{{
          friend.uname
        }}</b-col>
      </b-row>
    </b-col>
    <button v-b-modal.modal-center class="add-friend-btn">친구+</button>
    <b-modal id="modal-center" centered title="친구찾기">
      <div>
        <input
          style="width:70%"
          type="text"
          id="phone"
          placeholder="-빼고 번호 입력"
        />
        <button v-on:click="findFriend" style="width:30%">
          <b-icon-search></b-icon-search>
        </button>
      </div>
    </b-modal>
  </b-row>
</template>

<script>
import axios from "axios";

export default {
  created() {
    var params = new URLSearchParams();
    params.append("email", this.email);

    axios
      .post("http://localhost:8080/findFriendList", params)
      .then((response) => {
        if (response.data.isSuccess) {
          this.friends = response.data.friendList;
          console.log(this.friends);
        } else {
          console.log("친구가 존재하지 않습니다.");
        }
      })
      .catch((error) => {
        console.log(error);
      });
  },
  data() {
    return {
      email: window.sessionStorage.getItem("user-email"),
      friends: [],
    };
  },
  methods: {
    toFriendProfile(email) {
      var params = new URLSearchParams();
      params.append("email", email);
      axios
        .post("http://localhost:8080/profile", params)
        .then((response) => {
          var friendEmail = response.data["user-email"];
          // console.log(uno);
          this.$router.push({ name: "FriendInfo", params: { friendEmail } });
        })
        .catch((error) => {
          console.log(error);
        });
    },
    findFriend() {
      axios;
    },
  },
};
</script>

<style>
.add-friend-btn {
  width: 70px;
  height: 70px;
  border-radius: 50%;
  background-color: green;
  font-size: 20px;
  color: white;
  text-align: center;

  position: fixed;
  right: 5%;
  bottom: 10%;
}
</style>
