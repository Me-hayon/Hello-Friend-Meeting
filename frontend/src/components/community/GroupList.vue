<template>
  <v-row class="ma-0" style="height: 639px;" no-gutters>
    <v-col>
      <!-- 상단 바 -->
      <v-toolbar height="70" elevation="0" dense>
        <v-row no-gutters>
          <v-col cols="2">
            <v-btn color="success" fab dark depressed small>
              <v-icon>mdi-plus</v-icon>
            </v-btn>
          </v-col>
          <v-col>
            <v-text-field
              v-model="search"
              label="검색"
              append-icon="mdi-magnify"
              @input="searchInput"
              single-line
              clearable
            ></v-text-field>
          </v-col>
        </v-row>
      </v-toolbar>

      <v-row
        v-if="groups.length != 0"
        class="pa-4 overflow-y-auto"
        style="height: 591px;"
        no-gutters
      >
        <!-- 그룹 목록 -->
        <v-col v-if="search == null || search == ''">
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

        <!-- 검색 결과 -->
        <v-col v-else>
          <v-row
            v-for="row in searchGroupListRow"
            :key="'row-' + row"
            no-gutters
          >
            <template v-if="row != searchGroupListRow">
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
                            searchGroups[4 * (row - 1) + (col - 1)].gimg
                          }.png`)
                        "
                      ></v-img>
                    </v-btn>
                    <p>
                      {{ searchGroups[4 * (row - 1) + (col - 1)].gname }}
                    </p>
                  </v-col>
                </v-row>
              </v-col>
            </template>
            <template v-else>
              <v-col
                v-for="col in searchGroups.length % 4"
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
                            searchGroups[4 * (row - 1) + (col - 1)].gimg
                          }.png`)
                        "
                      ></v-img>
                    </v-btn>
                    <p>
                      {{ searchGroups[4 * (row - 1) + (col - 1)].gname }}
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
      search: null,
      searchGroups: [],
      searchGroupListRow: 0,
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
    searchInput() {
      this.searchGroups = [];

      if (this.search != null && this.search != '') {
        for (let i = 0; i < this.groups.length; i++) {
          if (this.groups[i].gname.includes(this.search))
            this.searchGroups.push(this.groups[i]);
        }

        this.searchGroupListRow =
          this.searchGroups.length % 4 == 0
            ? parseInt(this.searchGroups.length / 4)
            : parseInt(this.searchGroups.length / 4) + 1;
      }
    },
  },
};
</script>

<style></style>
