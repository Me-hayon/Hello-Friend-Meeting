<template>
  <v-simple-table style="margin-top:50px; margin-bottom:70px">
    <template v-slot:default>
      <tbody>
        <tr class="groupTd" v-for="item in groups" :key="item.name">
          <td>{{ item.name }}</td>
          <td>
            <i style="margin-right:10px" class="material-icons"
              ><font-awesome-icon :icon="['far', 'user']"/></i
            >{{ item.calories }}
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
          calories: 59,
        },
        {
          name: "스트라이크",
          calories: 27,
        },
        {
          name: "영화보러가자",
          calories: 22,
        },
        {
          name: "롤팸",
          calories: 35,
        },
        {
          name: "토익스터디",
          calories: 59,
        },
        {
          name: "스트라이크",
          calories: 27,
        },
        {
          name: "영화보러가자",
          calories: 22,
        },
        {
          name: "롤팸",
          calories: 35,
        },
      ],
    };
  },
};
</script>

<style>
.groupTd {
  height: 100px;
}
</style>
