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
              <td @click="goRouting(feed.aurl, feed.aurlNo)">
                {{ feed.asummary }}
              </td>
            </tr>
          </table>
        </li>
      </ul>
      <!-- <FeedItem />
      <FeedItem />
      <FeedItem />
      <FeedItem /> -->
    </div>
  </div>
</template>

<script>
// import { mapState } from "vuex";
import '../../components/css/feed/feed-item.scss';
import '../../components/css/feed/newsfeed.scss';
// import FeedItem from '../../components/feed/FeedItem.vue';
import axios from 'axios';

const storage = window.sessionStorage;
export default {
  // props: ['keyword'],

  methods: {
    goRouting(aurl, myParam) {
      var params = new URLSearchParams();
      if (aurl === 'FriendInfo') {
        this.$store.commit('setUno', myParam);
        this.$router.push('/user/friend-info').catch(() => {});
      } else if (aurl === 'GroupMainPage') {
        var gno = myParam;
        params.append('email', window.sessionStorage.getItem('user-email'));
        params.append('gno', gno);
        axios
          .post('isGroupMember', params)
          .then((response) => {
            this.$store.commit('setGno', gno);
            this.$store.commit('setMemberStatus', response.data.memberStatus);
            this.$router.push('/group').catch(() => {});
          })
          .catch((error) => {
            console.log(error);
          });
      } else if (aurl === 'BoardDetail') {
        var bno = myParam;
        params.append('bno', bno);
        params.append('email', window.sessionStorage.getItem('user-email'));
        axios.post('boardDetail', params).then((resp) => {
          this.$store.commit('setIsWriter', resp.data.isWriter);

          params = new URLSearchParams();
          params.append('email', window.sessionStorage.getItem('user-email'));
          params.append('gno', resp.data.curBoard.bgno);

          axios.post('isGroupMember', params).then((response) => {
            this.$store.commit('setGno', gno);
            this.$store.commit('setMemberStatus', response.data.memberStatus);
            this.$router.push('/board/detail').catch(() => {});
          });
        });
      }
    },
  },
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
      .post('getFeeds', params)
      .then((response) => {
        console.log('asdfasdf');
        console.log(response);
        console.log('asdfa');

        this.myFeeds = response.data.feeds;
      })
      .catch((error) => {
        console.log(error);
        console.log('error occur');
      });

    this.$store.commit('setIsHeader', true);
    this.$store.commit('setIsFooter', true);
  },
};
</script>
