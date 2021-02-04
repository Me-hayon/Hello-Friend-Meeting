<template>
  <v-simple-table style="margin-top:50px; margin-bottom:70px">
    <template v-slot:default>
      <tbody>
        <tr
          style="height:100px"
          v-for="item in groups"
          :key="item.gno"
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

<style></style>
