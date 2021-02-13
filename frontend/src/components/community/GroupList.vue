<template>
  <!-- 그룹이 존재할 경우 -->
  <v-list
    v-if="groups.length != 0"
    height="639"
    style="overflow-y: auto;"
    two-line
  >
    <template v-for="(group, index) in groups">
      <v-divider
        v-if="index == 0"
        :key="'dividerUp' + index"
        style="margin-top: 0;"
      ></v-divider>

      <v-list-item :key="group.gno">
        <v-list-item-avatar>
          <v-img src="@/assets/images/group-img/group_default.png"></v-img>
        </v-list-item-avatar>

        <v-list-item-content>
          <v-list-item-title v-text="group.gname"></v-list-item-title>
          <v-list-item-subtitle v-text="group.gname"></v-list-item-subtitle>
        </v-list-item-content>

        <v-list-item-icon>
          <v-icon color="deep-purple accent-4">{{
            icons[group.gcategory - 1]
          }}</v-icon>
        </v-list-item-icon>
      </v-list-item>

      <v-divider :key="'dividerDown' + index"></v-divider>
    </template>
  </v-list>

  <!-- 그룹이 존재하지 않을 경우 -->
  <v-row v-else class="ma-0" style="height: 639px;" justify="center">
    <v-icon color="red" size="100">mdi-close-circle</v-icon>
  </v-row>
</template>

<script>
export default {
  props: ['groupList', 'categoryList', 'friendList'],
  data() {
    return {
      groups: this.groupList,
      categories: this.categoryList,
      friends: this.friendList,
      icons: [
        'mdi-controller-classic',
        'mdi-book-open-page-variant',
        'mdi-soccer',
        'mdi-music-note',
        'mdi-hand-shake',
        'mdi-food-turkey',
        'mdi-face-woman-shimmer',
      ],
    };
  },
};
</script>

<style></style>
