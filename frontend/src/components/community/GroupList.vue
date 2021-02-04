<template
  ><div>
    <v-simple-table style="margin-top:50px; margin-bottom:70px">
      <template v-slot:default>
        <tbody>
          <tr
            style="height:100px"
            v-for="item in groups"
            :key="item.gname"
            @click="goToGroupPage(item.gno)"
          >
            <td>{{ item.gname }}</td>
            <td>
              <i style="margin-right:10px" class="material-icons"
                ><font-awesome-icon :icon="['far', 'user']"/></i
              >{{ item.members }}
            </td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
    <button v-b-modal.modal-center class="add-group-btn">그룹+</button>
    <b-modal id="modal-center" centered title="그룹생성" @ok="makeGroup">
      <b-row style="margin-bottom:25px">
        <b-col style="margin-top:10px">그룹명</b-col>
        <b-col
          ><b-form-input v-model="gname" type="text" class="groupName"
        /></b-col>
      </b-row>
      <b-row style="margin-bottom:25px">
        <b-col>카테고리</b-col>
        <b-col>
          <b-form-select v-model="selectedCategory">
            <option
              :value="item.cno"
              v-for="item in category"
              :key="item.cno"
              >{{ item.cname }}</option
            >
          </b-form-select>
        </b-col>
      </b-row>
      <b-row style="margin-bottom:25px">
        <b-col>노출범위</b-col>
        <b-form-radio-group>
          <b-col>
            <b-row>
              <b-col>
                <b-form-radio v-model="boundary" name="drone" value="1"
                  >친구만</b-form-radio
                >
              </b-col>
            </b-row>

            <b-row>
              <b-col>
                <b-form-radio v-model="boundary" name="drone" value="2"
                  >친구의친구까지</b-form-radio
                >
              </b-col>
            </b-row>

            <b-row>
              <b-col>
                <b-form-radio v-model="boundary" name="drone" value="0"
                  >비공개</b-form-radio
                >
              </b-col>
            </b-row>
          </b-col>
        </b-form-radio-group>
      </b-row>
    </b-modal>
  </div>
</template>

<script>
import axios from "axios";

export default {
  created() {
    var storage = window.sessionStorage;
    var params = new URLSearchParams();

    params.append("email", storage.getItem("user-email"));
    axios
      .post("http://localhost:8080/getGroupList", params)
      .then((response) => {
        this.groups = response.data.groupList;
        for (var i = 0; i < this.groups.length; i++) {
          this.groups[i].members =
            this.groups[i].guserList.split(" ").length - 1;
        }

        console.log("this is groups");
        console.log(this.groups);
      })
      .catch((error) => {
        console.log(error);
      });
    axios
      .post("http://localhost:8080/getCategory")
      .then((response) => {
        this.category = response.data.list;

        console.log("this is category");
        console.log(this.category);
      })
      .catch((error) => {
        console.log(error);
      });
  },
  data() {
    return {
      groupMembers: [],
      groups: [],
      category: [],
      boundary: "",
      selectedCategory: "",
      gname: "",
    };
  },
  methods: {
    goToGroupPage(gno) {
      this.$router.push({ name: "GroupMainPage", params: { gno } });
    },
    makeGroup() {
      var storage = window.sessionStorage;
      var params = new URLSearchParams();
      params.append("email", storage.getItem("user-email"));
      params.append("gname", this.gname);
      params.append("gcategory", this.selectedCategory);
      params.append("gboundary", this.boundary);

      axios
        .post("http://localhost:8080/makeGroup", params)
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
