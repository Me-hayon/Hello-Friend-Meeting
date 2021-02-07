<template>
  <v-speed-dial
    style="top: 160px; left: 175px;"
    v-model="profileImgFab"
    absolute
    direction="bottom"
    transition="slide-y-transition"
  >
    <template v-slot:activator>
      <v-btn v-model="profileImgFab" fab>
        <v-img
          contain
          height="100"
          :src="require(`@/assets/images/avatars/${profileImg}.png`)"
        ></v-img>
      </v-btn>
    </template>
    <v-row justify="center" style="width: 300px; overflow-y: visible;">
      <v-btn
        v-for="(pImage, index) in pImages"
        :key="index"
        fab
        @click="changeAvatar(pImage, index)"
      >
        <v-img
          contain
          height="100"
          :src="require(`@/assets/images/avatars/${pImage}.png`)"
        ></v-img>
      </v-btn>
    </v-row>
  </v-speed-dial>
</template>

<script>
import axios from 'axios';

export default {
  props: ['email', 'currProfileImg', 'curr_pImages'],
  data() {
    return {
      profileImg: this.currProfileImg,
      pImages: this.curr_pImages,
      profileImgFab: false,
    };
  },
  created() {
    console.log('image create');
  },
  methods: {
    changeAvatar(pImage, index) {
      let prevImg = this.profileImg;
      this.profileImg = pImage;
      this.pImages.splice(index, 1);
      this.pImages.splice(index, 0, prevImg);

      let params = new URLSearchParams();
      params.append('email', this.email);
      params.append('profileImg', pImage);

      axios
        .put('http://localhost:8080/changeAvatar', params)
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style></style>
