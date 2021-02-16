import Login from '@/views/user/Login.vue';
import Join from '@/views/user/Join.vue';
import PasswordModify from '@/views/user/PasswordModify.vue';
import FeedMain from '@/views/feed/IndexFeed.vue';
import Community from '@/views/Community.vue';
import Profile from '@/views/user/Profile.vue';
import GroupMainPage from '@/components/group/GroupMainPage.vue';
import GroupBoardDetail from '@/components/group/GroupBoardDetail.vue';
import Message from '@/views/message/Message.vue';
import ErrorPage from '@/views/Error.vue';
import PageNotFound from '@/views/PageNotFound.vue';
import FriendTest from '@/views/FriendTest.vue';
import Naegi from '@/components/group/NaegiTab.vue'

export default [
  {
    path: '/naegi',
    name: 'naegi',
    component:Naegi
  },
  {
    path: '/ft/:uno',
    name: 'ft',
    component: FriendTest,
  },
  {
    path: '/',
    name: 'Login',
    component: Login,
  },
  {
    path: '/user/join',
    name: 'Join',
    component: Join,
  },
  {
    path: '/user/profile',
    name: 'Profile',
    component: Profile,
  },
  {
    path: '/user/modify',
    name: 'PasswordModify',
    component: PasswordModify,
  },
  {
    path: '/feed/main',
    name: 'FeedMain',
    component: FeedMain,
  },
  {
    path: '/community',
    name: 'Community',
    component: Community,
  },
  {
    path: '/message',
    name: 'Message',
    component: Message,
  },
  {
    path: '/group',
    name: 'GroupMainPage',
    component: GroupMainPage,
  },

  {
    path: '/error',
    name: 'ErrorPage',
    component: ErrorPage,
  },
  {
    path: '/board/detail',
    name: 'GroupBoardDetail',
    component: GroupBoardDetail,
  },
  {
    path: '*',
    name: 'PageNotFound',

    component: PageNotFound,
  },
];
