<template>
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
</template>

<script>
export default {
  data() {
    return {
      groups: [
        {
          name: "토익스터디",
          member: 59,
        },
        {
          name: "스트라이크",
          member: 27,
        },
        {
          name: "영화보러가자",
          member: 22,
        },
        {
          name: "롤팸",
          member: 35,
        },
        {
          name: "토익스터디",
          member: 59,
        },
        {
          name: "스트라이크",
          member: 27,
        },
        {
          name: "영화보러가자",
          member: 22,
        },
        {
          name: "롤팸",
          member: 35,
        },
      ],
    };
  },
};
</script>

<style></style>
