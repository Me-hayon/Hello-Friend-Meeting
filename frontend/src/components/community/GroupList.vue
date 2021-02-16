<template>
  <v-row class="ma-0 overflow-y-auto" style="height: 639px;" no-gutters>
    <v-col>
      <!-- 상단 바 -->
      <v-toolbar elevation="0" dense>
        <v-btn icon x-large>
          <v-icon>mdi-plus</v-icon>
        </v-btn>

        <v-spacer></v-spacer>

        <v-btn icon>
          <v-icon>mdi-magnify</v-icon>
        </v-btn>
      </v-toolbar>

      <!-- 그룹 목록 -->
      <v-row class="pa-4 overflow-y-auto" style="height: 591px;" no-gutters>
        <v-col>
          <v-row v-for="row in groupListRow" :key="'row-' + row" no-gutters>
            <template v-if="row != groupListRow">
              <v-col
                v-for="col in 4"
                :key="'col-' + 4 * (row - 1) + (col - 1)"
                cols="3"
              >
                <v-row justify="center" no-gutters>
                  <v-col align="center">
                    <v-btn
                      @click="moveGroup(4 * (row - 1) + (col - 1))"
                      depressed
                      fab
                    >
                      <v-img
                        width="50"
                        :src="
                          require(`@/assets/images/group-img/${
                            groups[4 * (row - 1) + (col - 1)].gimg
                          }.png`)
                        "
                      ></v-img>
                    </v-btn>
                    <p>
                      {{ groups[4 * (row - 1) + (col - 1)].gname }}
                    </p>
                  </v-col>
                </v-row>
              </v-col>
            </template>
            <template v-else>
              <v-col
                v-for="col in groups.length % 4"
                :key="'col-' + 4 * (row - 1) + (col - 1)"
                cols="3"
              >
                <v-row justify="center" no-gutters>
                  <v-col align="center">
                    <v-btn
                      @click="moveGroup(4 * (row - 1) + (col - 1))"
                      depressed
                      fab
                    >
                      <v-img
                        width="50"
                        :src="
                          require(`@/assets/images/group-img/${
                            groups[4 * (row - 1) + (col - 1)].gimg
                          }.png`)
                        "
                      ></v-img>
                    </v-btn>
                    <p>
                      {{ groups[4 * (row - 1) + (col - 1)].gname }}
                    </p>
                  </v-col>
                </v-row>
              </v-col>
            </template>
          </v-row>
        </v-col>
      </v-row>
    </v-col>
  </v-row>

  <!-- 그룹이 존재하지 않을 경우 -->
  <!-- <v-row v-else class="ma-0" style="height: 639px;" justify="center">
    <v-icon color="red" size="100">mdi-close-circle</v-icon>
  </v-row> -->
</template>

<script>
export default {
  props: ['groupList', 'categoryList', 'friendList'],
  data() {
    return {
      groups: this.groupList,
      categories: this.categoryList,
      friends: this.friendList,
      groupListRow: 0,
      groupListcol: 4,
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
  created() {
    this.groupListRow =
      this.groups.length % 4 == 0
        ? parseInt(this.groups.length / 4)
        : parseInt(this.groups.length / 4) + 1;
  },
  methods: {
    moveGroup(index) {
      this.$store.commit('setGno', this.groups[index].gno);
      this.$router.push('/group');
    },
  },
};
</script>

<style></style>
