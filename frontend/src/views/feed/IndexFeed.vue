<template>
  <v-card flat tile>
    <v-row style="margin: 10px;">
      <div style="font-size: 1.5rem;">
        안녕하세요, <strong>{{ myName }}</strong
        >님!
      </div>
      <div>친구들이 만든 그룹과 새로 등록된 일정을 볼 수 있어요.</div></v-row
    >

    <!-- <v-container v-for="type in types" :key="type" class="grey lighten-4" fluid> -->
    <!-- <v-subheader>{{ type }}</v-subheader> -->
    <v-container>
      <v-row>
        <v-spacer></v-spacer>
        <v-col v-for="feed in myFeeds" :key="feed.ano" cols="12" sm="6" md="4">
          <v-card>
            <v-img
              :src="`https://picsum.photos/200/300?image=${getImage()}`"
              gradient="to top right, rgba(0,0,0,.7), rgba(25,32,72,0)"
              height="150px"
            >
              <span
                class="white--text pl-4 pt-4 d-inline-block"
                v-text="feed.asummary"
              ></span>
            </v-img>

            <v-card-actions class="white justify-left">
              <span
                class="white--text"
                @click="goRouting(feed.aurl, feed.createUser)"
                style="position: absolute; bottom: 40px; "
                >클릭해서 보러 갈래요</span
              >
              <!-- <v-btn
                v-for="(social, i) in socials"
                :key="i"
                :color="social.color"
                class="white--text"
                fab
                icon
                small
              >
                <v-icon>{{ social.icon }}</v-icon>
              </v-btn> -->
              <span
                >From. <b>{{ feed.createUserName }}</b></span
              >
              <!-- <img
                height="101"
                style="position: absolute; right: 8px; bottom: 6px; "
                src="@/assets/images/circle.png"
              /> -->

              <img
                height="100"
                style="position: absolute; right: 10px; bottom: 10px;  border-radius: 70%;"
                :src="require(`@/assets/images/avatars/${feed.profileImg}.png`)"
              />
            </v-card-actions>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-card>
</template>

<script>
// import { mapState } from "vuex";
// import "../../components/css/feed/feed-item.scss";
// import "../../components/css/feed/newsfeed.scss";
// import FeedItem from '../../components/feed/FeedItem.vue';
import axios from 'axios';

const storage = window.sessionStorage;

export default {
  created() {
    this.$store.commit('setIsHeader', true);
    this.$store.commit('setIsFooter', true);

    var params = new URLSearchParams();
    params.append('email', storage.getItem('user-email'));
    // params.append('email', 'test@gmail.com');
    console.log(params);
    axios
      .post('http://localhost:8080/profile', params)
      .then((response) => {
        console.log(response);
        this.myName = response.data['user-name'];
      })
      .catch((error) => {
        console.log(error);
      });

    axios
      .post('getFeeds', params)
      .then((response) => {
        console.log(response);
        this.myFeeds = response.data.list;
      })
      .catch((error) => {
        console.log(error);
        console.log('error occur');
      });
  },
  data: () => ({
    myFeeds: [
      {
        ano: '',
        aurl: '',
        asummary: '',
        adate: '',
        createUser: '',
        createUserName: '',
        profileImg: '',
      },
    ],
    myName: 'Hana',
  }),

  methods: {
    goRouting(aurl, auno) {
      var params = new URLSearchParams();
      params.append('uno', auno);
      axios.post('findEmailByUno', params).then((response) => {
        var friendEmail = response.data.data;
        this.$router.push({ name: aurl, params: { friendEmail } });
      });
    },
    getImage() {
      const min = 550;
      const max = 560;

      return Math.floor(Math.random() * (max - min + 1)) + min;
    },
  },
};
</script>

<style></style>
