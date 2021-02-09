<template>
  <v-row>
    <v-subheader>나의 활동</v-subheader>
    <v-timeline>
      <v-timeline-item v-for="n in 4" :key="n" large>
        <template v-slot:icon>
          <v-avatar>
            <v-img
              :src="require(`@/assets/images/avatars/${profileImg}.png`)"
            />
          </v-avatar>
        </template>
        <template v-slot:opposite>
          <span>Tus eu perfecto</span>
        </template>
        <v-card class="elevation-2">
          <v-card-title class="headline">Lorem ipsum</v-card-title>
          <v-card-text
            >Lorem ipsum dolor sit amet, no nam oblique veritus. Commune
            scaevola imperdiet nec ut, sed euismod convenire principes at. Est
            et nobis iisque percipit, an vim zril disputando voluptatibus, vix
            an salutandi sententiae.</v-card-text
          >
        </v-card>
      </v-timeline-item>
    </v-timeline>
  </v-row>
</template>

<script>
export default {
  props: ['currProfileImg'],
  data() {
    return {
      profileImg: this.currProfileImg,
    };
  },
};
</script>

<style></style>
