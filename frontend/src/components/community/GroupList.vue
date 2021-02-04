<template
  ><div>
    <v-simple-table style="margin-top:50px; margin-bottom:70px">
      <template v-slot:default>
        <tbody>
          <tr style="height:100px" v-for="item in groups" :key="item.name">
            <td>{{ item.name }}</td>
            <td>
              <i style="margin-right:10px" class="material-icons"
                ><font-awesome-icon :icon="['far', 'user']"/></i
              >{{ item.member }}
            </td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
    <button v-b-modal.modal-center class="add-group-btn">그룹+</button>
    <b-modal id="modal-center" centered title="그룹생성">
      <b-row style="margin-bottom:25px">
        <b-col style="margin-top:10px">그룹명</b-col>
        <b-col><input type="text" class="groupName"/></b-col>
      </b-row>
      <b-row style="margin-bottom:25px">
        <b-col>카테고리</b-col>
        <b-col>
          <b-form-select>
            <option value="여행">여행</option>
            <option value="스터디">스터디</option>
            <option value="게임">게임</option>
            <option value="영화">영화</option>
          </b-form-select>
          <!-- <select name="category" id="category-select">
            <option value="여행">여행</option>
            <option value="스터디">스터디</option>
            <option value="게임">게임</option>
            <option value="영화">영화</option></select> -->
        </b-col>
      </b-row>
      <b-row style="margin-bottom:25px">
        <b-col>노출범위</b-col>
        <b-col>
          <b-row>
            <b-col>
              <b-form-radio name="drone" value="친구만">친구만</b-form-radio>
              <!-- <input
                class="groupRadio"
                type="radio"
                id="친구만"
                name="drone"
                value="친구만"
                checked
              /> -->
            </b-col>
          </b-row>

          <b-row>
            <b-col>
              <b-form-radio name="drone" value="친구의친구까지"
                >친구의친구까지</b-form-radio
              >
            </b-col>
            <!-- <label for="친구의친구까지">친구의친구까지</label>
            <input
              type="radio"
              id="친구의친구까지"
              name="drone"
              value="친구의친구까지"
            /> -->
          </b-row>

          <b-row>
            <b-col>
              <b-form-radio name="drone" value="비공개">비공개</b-form-radio>
            </b-col>
            <!-- <label for="비공개">비공개</label>
            <input type="radio" id="비공개" name="drone" value="비공개" /> -->
          </b-row>
        </b-col>
      </b-row>
    </b-modal>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  created() {
    var storage = window.sessionStorage;
    var params = new URLSearchParams();

    params.append('email', storage.getItem('user-email'));
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
  data() {
    return {
      groupMembers: [],
      groups: [],
    };
  },
  methods: {
    goToGroupPage(gno) {
      this.$router.push({ name: 'GroupMainPage', params: { gno } });
    },
  },
};
</script>

<style>
.add-group-btn {
  width: 70px;
  height: 70px;
  border-radius: 50%;
  background-color: blue;
  font-size: 20px;
  color: white;
  text-align: center;

  position: fixed;
  right: 5%;
  bottom: 10%;
}
</style>
