<template>
  <div class="feed newsfeed">
    <div class="wrapB" style="margin-bottom:60px">
      <h1>뉴스피드</h1>

      <ul>
        <li v-for="feed in myFeeds" :key="feed.ano">
          <table class="table table-bordered">
            <tr>
              <td>
                <div>
                  <img
                    :src="
                      require(`@/assets/images/avatars/${feed.profileImg}.png`)
                    "
                  />{{ feed.createUserName }}
                </div>
                <div style="text-align:right">
                  {{ feed.adate }}
                </div>
              </td>
            </tr>
            <tr>
              <td>{{ feed.asummary }}</td>
            </tr>
          </table>
        </li>
      </ul>
      <!-- <FeedItem />
      <FeedItem />
      <FeedItem />
      <FeedItem /> -->
    </div>
    <BottomNav />
  </div>
</template>

<script>
// import { mapState } from "vuex";
import '../../components/css/feed/feed-item.scss';
import '../../components/css/feed/newsfeed.scss';
import BottomNav from '../../components/common/BottomNav.vue';
// import FeedItem from '../../components/feed/FeedItem.vue';
import axios from 'axios';

const storage = window.sessionStorage;
export default {
  // props: ['keyword'],

  components: { BottomNav },
  data() {
    return {
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
    };
  },
  created() {
    var params = new URLSearchParams();
    params.append('email', storage.getItem('user-email'));
    // params.append('email', 'test@gmail.com');
    console.log(params);
    axios
      .post('http://localhost:8080/getFeeds', params)
      .then((response) => {
        console.log(response);
        this.myFeeds = response.data;
      })
      .catch((error) => {
        console.log(error);
        console.log('error occur');
      });

    this.$store.state.isHeader = true;
  },
};
</script>
