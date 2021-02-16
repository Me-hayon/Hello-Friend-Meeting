<template>
  <v-row>
    <v-subheader>나의 활동</v-subheader>
    <v-timeline>
      <v-timeline-item v-for="timeline in timelines" :key="timeline.tno" large>
        <template v-slot:icon>
          <v-avatar>
            <v-img
              :src="require(`@/assets/images/avatars/${profileImg}.png`)"
            />
          </v-avatar>
        </template>
        <template v-slot:opposite>
          <span>{{ timeline.tdate }}</span>
        </template>
        <v-card class="elevation-2">
          <v-card-title class="headline"
            >이날엔 무슨일이 있었을까요?</v-card-title
          >
          <v-card-text>{{ timeline.tcontent }}</v-card-text>
        </v-card>
      </v-timeline-item>
    </v-timeline>
  </v-row>
</template>

<script>
export default {
  props: ['currProfileImg', 'timelines'],
  data() {
    return {
      profileImg: this.currProfileImg,
    };
  },
};
</script>

<style></style>
