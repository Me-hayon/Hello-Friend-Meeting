import FriendInfo from "@/views/user/FriendInfo.vue";

import Login from "@/views/user/Login.vue";
import Join from "@/views/user/Join.vue";
import UserInfo from "@/views/user/Info.vue";
import PasswordModify from "@/views/user/PasswordModify.vue";
import FeedMain from "@/views/feed/IndexFeed.vue";
import Community from "@/views/Community.vue";
import GroupList from "@/components/community/GroupList.vue";
import FriendList from "@/components/community/FriendList.vue";
import Message from "@/views/message/Message.vue";
import ErrorPage from "@/views/Error.vue";
import PageNotFound from "@/views/PageNotFound.vue";
import ChangeAvatar from "@/views/user/ChangeAvatar.vue";

import FriendTest from "@/views/FriendTest.vue";

import GroupMainPage from "@/components/group/GroupMainPage.vue";
export default [
  {
    path: "/ft/:uno",
    name: "ft",
    component: FriendTest,
  },
  {
    path: "/",
    name: "Login",
    component: Login,
  },
  {
    path: "/user/join",
    name: "Join",
    component: Join,
  },
  {
    path: "/user/info",
    name: "Info",
    component: UserInfo,
  },
  {
    path: "/user/friend-info",
    name: "FriendInfo",
    component: FriendInfo,
    props: true,
  },
  {
    path: "/user/modify",
    name: "PasswordModify",
    component: PasswordModify,
  },
  {
    path: "/user/changeAvatar",
    name: "ChangeAvatar",
    component: ChangeAvatar,
  },
  {
    path: "/feed/main",
    name: "FeedMain",
    component: FeedMain,
  },
  {
    path: "/community",
    name: "Community",
    component: Community,
    children: [
      {
        path: "",
        component: GroupList,
      },
      {
        path: "group",
        component: GroupList,
      },
      {
        path: "friend",
        component: FriendList,
      },
    ],
  },
  {
    path: "/message",
    name: "Message",
    component: Message,
  },
  {
    path: "/group",
    name: "GroupMainPage",
    component: GroupMainPage,
    props: true,
  },

  {
    path: "/error",
    name: "ErrorPage",
    component: ErrorPage,
  },
  {
    path: "*",
    name: "PageNotFound",
    component: PageNotFound,
  },
];
