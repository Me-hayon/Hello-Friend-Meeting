<template>
  <v-card>
    <v-tabs v-model="tab" background-color="transparent" grow>
      <v-tab>게시판</v-tab>
      <v-tab>일정</v-tab>
      <v-tab>채팅</v-tab>
      <v-tab>그룹원</v-tab>
    </v-tabs>

    <v-tabs-items v-model="tab">
      <v-tab-item>
        <v-card flat> <GroupBoard /></v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat> <GroupSchedule /></v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat><GroupChat /> </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat><GroupMemberList /> </v-card>
      </v-tab-item>
    </v-tabs-items>
  </v-card>
</template>
<script>
import GroupBoard from "@/components/group/GroupBoard.vue";
import GroupSchedule from "@/components/group/GroupSchedule.vue";
import GroupChat from "@/components/group/GroupChat.vue";
import GroupMemberList from "@/components/group/GroupMemberList.vue";
export default {
  components: {
    GroupBoard,
    GroupSchedule,
    GroupChat,
    GroupMemberList,
  },

  data() {
    return {
      tab: null,
    };
  },
};
</script>

<style></style>
