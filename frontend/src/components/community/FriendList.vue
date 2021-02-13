<template>
  <v-row
    v-if="friends.length != 0"
    id="friendList"
    class="ma-0 overflow-y-auto"
    style="height: 639px;"
    v-scroll:#friendList="onScroll"
  >
    <!-- 상단 바 -->
    <v-toolbar
      width="100%"
      :height="toolbarHeight"
      :absolute="true"
      elevation="0"
      dense
    >
      <v-row no-gutters>
        <v-col>
          <v-text-field
            v-model="search"
            label="검색"
            append-icon="mdi-magnify"
            @input="searchInput"
            single-line
            clearable
          ></v-text-field>

          <v-row v-if="search != null && search != ''" no-gutters>
            <span>검색 결과</span>
          </v-row>

          <v-row v-if="search == null || search == ''" no-gutters>
            <span>즐겨찾는 친구</span>
            <v-spacer></v-spacer>
            <v-btn
              v-if="isFavoriteArea || favoriteIcon == 'mdi-chevron-up'"
              small
              icon
              @click="
                favoriteIcon =
                  favoriteIcon == 'mdi-chevron-down'
                    ? 'mdi-chevron-up'
                    : 'mdi-chevron-down'
              "
              ><v-icon>{{ favoriteIcon }}</v-icon>
            </v-btn>

            <v-btn v-else small icon @click="moveFavorite"
              ><v-icon>mdi-transfer-up</v-icon>
            </v-btn>
          </v-row>

          <v-row
            v-if="(search == null || search == '') && !isFavoriteArea"
            class="mt-4"
            no-gutters
          >
            <span>친구</span>
            <v-spacer></v-spacer>
            <v-btn
              small
              icon
              @click="
                allIcon =
                  allIcon == 'mdi-chevron-down'
                    ? 'mdi-chevron-up'
                    : 'mdi-chevron-down'
              "
            >
              <v-icon>{{ allIcon }}</v-icon>
            </v-btn>
          </v-row>
        </v-col>
      </v-row>
    </v-toolbar>

    <!-- 친구 목록 -->
    <v-col v-if="search == null || search == ''" class="pa-0">
      <transition name="list-transition">
        <v-list
          v-if="favoriteIcon == 'mdi-chevron-down'"
          class="py-0"
          style="margin-top: 100px;"
          width="100%"
          flat
        >
          <v-list-item-group v-model="favoriteFriendListSelect">
            <v-list-item
              v-for="(favoriteFriend, index) in favoriteFriends"
              :key="'favorite-' + index"
              :value="favoriteFriend"
            >
              <v-list-item-avatar>
                <v-img
                  :src="
                    require(`@/assets/images/avatars/${favoriteFriend.uprofileImg}.png`)
                  "
                ></v-img>
              </v-list-item-avatar>

              <v-list-item-content>
                <v-list-item-title
                  v-text="favoriteFriend.uname"
                ></v-list-item-title>
              </v-list-item-content>

              <v-list-item-icon>
                <v-icon color="deep-purple accent-4">mdi-message</v-icon>
              </v-list-item-icon>
            </v-list-item>
          </v-list-item-group>
        </v-list>
      </transition>

      <v-row
        id="allHeader"
        class="px-4"
        style="padding-bottom: 11px;"
        :style="
          favoriteIcon == 'mdi-chevron-up'
            ? 'margin-top: 100px; padding-top: 5px;'
            : 'margin-top: 32px;'
        "
        no-gutters
      >
        <span>친구</span>
        <v-spacer></v-spacer>
        <v-btn
          small
          icon
          @click="
            allIcon =
              allIcon == 'mdi-chevron-down'
                ? 'mdi-chevron-up'
                : 'mdi-chevron-down'
          "
        >
          <v-icon>{{ allIcon }}</v-icon>
        </v-btn>
      </v-row>

      <transition name="list-transition">
        <v-list
          v-if="allIcon == 'mdi-chevron-down'"
          id="allList"
          class="py-0"
          width="100%"
          flat
        >
          <v-list-item-group v-model="allFriendListSelect">
            <v-list-item
              v-for="(friend, index) in friends"
              :key="'all-' + index"
              :value="friend"
            >
              <v-list-item-avatar>
                <v-img
                  :src="
                    require(`@/assets/images/avatars/${friend.uprofileImg}.png`)
                  "
                ></v-img>
              </v-list-item-avatar>

              <v-list-item-content>
                <v-list-item-title v-text="friend.uname"></v-list-item-title>
              </v-list-item-content>

              <v-list-item-icon>
                <v-icon color="deep-purple accent-4">mdi-message</v-icon>
              </v-list-item-icon>
            </v-list-item>
          </v-list-item-group>
        </v-list>
      </transition>
    </v-col>

    <!-- 검색 결과 -->
    <v-col v-else class="pa-0">
      <transition name="list-transition">
        <v-list class="py-0" style="margin-top: 100px;" width="100%" flat>
          <v-list-item-group v-model="searchFriendListSelect">
            <v-list-item
              v-for="(friend, index) in searchFriends"
              :key="index"
              :value="friend"
            >
              <v-list-item-avatar>
                <v-img
                  :src="
                    require(`@/assets/images/avatars/${friend.uprofileImg}.png`)
                  "
                ></v-img>
              </v-list-item-avatar>

              <v-list-item-content>
                <v-list-item-title v-text="friend.uname"></v-list-item-title>
              </v-list-item-content>

              <v-list-item-icon>
                <v-icon color="deep-purple accent-4">mdi-message</v-icon>
              </v-list-item-icon>
            </v-list-item>
          </v-list-item-group>
        </v-list>
      </transition>
    </v-col>

    <!-- 친구 프로필 모달 -->
    <v-dialog
      v-if="search != null"
      v-model="friendProfileModal"
      max-width="400"
      persistent
      scrollable
    >
      <v-card>
        <v-card-title>
          <span
            ><strong>{{ search.uname }}</strong
            >님의 프로필</span
          >
          <v-spacer></v-spacer>
          <v-btn icon @click="friendProfileModal = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-card-title>
        <v-card-text>
          <friend-profile :info="search" />
        </v-card-text>
        <v-card-actions style="padding-top: 0;">
          <v-row class="ma-0" justify="end">
            <v-btn></v-btn>
            <!-- <v-btn
              color="warning"
              class="font-weight-black"
              :disabled="!valid"
              @click="deleteAlertModal = !deleteAlertModal"
            >
              탈퇴하기
            </v-btn> -->
          </v-row>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>

  <!-- 친구가 존재하지 않을 경우 -->
  <v-row v-else class="ma-0" style="height: 639px;" justify="center">
    <v-icon color="red" size="100">mdi-close-circle</v-icon>
  </v-row>
</template>

<script>
import FriendProfile from '@/views/user/FriendProfile.vue';

export default {
  components: { FriendProfile },
  props: ['friendList', 'favoriteFriendList'],
  data() {
    return {
      friends: this.friendList,
      favoriteFriends: this.favoriteFriendList,
      searchFriends: [],
      toolbarHeight: 100,
      search: null,
      isFavoriteArea: true,
      searchFriendListSelect: null,
      favoriteFriendListSelect: null,
      allFriendListSelect: null,
      favoriteIcon: 'mdi-chevron-down',
      allHeaderTop: 0,
      allIcon: 'mdi-chevron-down',
      friendProfileModal: false,
    };
  },
  mounted() {
    this.getAllHeaderTop();
  },
  methods: {
    onScroll(e) {
      if (this.search != null && this.search != '') {
        return;
      } else if (
        this.isFavoriteArea &&
        e.target.scrollTop >= this.allHeaderTop
      ) {
        this.isFavoriteArea = false;
        this.toolbarHeight = 150;
      } else if (
        !this.isFavoriteArea &&
        e.target.scrollTop < this.allHeaderTop
      ) {
        this.isFavoriteArea = true;
        this.toolbarHeight = 100;
      }
    },
    getAllHeaderTop() {
      this.$nextTick(function() {
        this.allHeaderTop =
          document.querySelector('#allHeader').getBoundingClientRect().top -
          150 -
          56 -
          72 +
          28;
      });
    },
    moveFavorite() {
      document.querySelector('#friendList').scrollTop = 0;
    },
    searchInput() {
      this.searchFriends = [];

      for (let i = 0; i < this.friends.length; i++) {
        if (this.friends[i].uname.includes(this.search))
          this.searchFriends.push(this.friends[i]);
      }
    },
  },
  watch: {
    favoriteIcon(favoriteIcon) {
      if (favoriteIcon == 'mdi-chevron-down') {
        this.isFavoriteArea = true;
        this.toolbarHeight = 100;
      }

      this.getAllHeaderTop();

      if (favoriteIcon == 'mdi-chevron-down')
        document.querySelector('#friendList').scrollTop = 0;
      else
        this.allHeaderTop = document.querySelector(
          '#friendList'
        ).scrollTop = this.allHeaderTop;
    },
    allIcon(allIcon) {
      if (allIcon == 'mdi-chevron-down') {
        if (this.favoriteIcon == 'mdi-chevron-up') this.getAllHeaderTop();

        this.$nextTick(function() {
          document.querySelector('#friendList').scrollTop =
            this.allHeaderTop + 5;
        });
      }
    },
    favoriteFriendListSelect(favoriteFriendListSelect) {
      console.log(favoriteFriendListSelect);
    },
    allFriendListSelect(allFriendListSelect) {
      console.log(allFriendListSelect);
    },
  },
};
</script>

<style>
.list-transition-enter {
  opacity: 0;
}

.list-transition-enter-active {
  transition: all 0.2s linear;
}

.list-transition-enter-to {
  transform: translateY(10px);
  opacity: 1;
}

.list-transition-leave-active {
  transition: all 0.2s linear;
}

.list-transition-leave-to {
  transform: translateY(-10px);
  opacity: 0;
}
</style>
