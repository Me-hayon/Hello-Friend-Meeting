<template>
  <div>
    <v-file-input
      v-model="files"
      accept="image/*"
      label="File input"
    ></v-file-input>
    <v-btn @click="upload">업로드</v-btn>
    <v-btn @click="download">출력</v-btn>
    <div v-if="!myImg">로딩중?</div>
    <div v-else><img :src="'data:image/png;base64,' + myImg" /></div>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  data() {
    return {
      files: [],
      myImg: false,
    };
  },
  methods: {
    upload() {
      console.log(this.files);
      var params = new FormData();
      params.append('file', this.files);
      axios
        .post('fileUpload', params, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        })
        .then((resp) => {
          alert(resp.data.result);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    download() {
      axios
        .get('getFile?fno=11')
        .then((resp) => {
          this.myImg = resp.data.file;
          console.log(resp.data.file);
          // console.log(resp.data);
          const intArray = new Uint8Array(resp.data.file);
          const reducedArray = intArray.reduce(
            (data, byte) => data + String.fromCharCode(byte),
            ''
          );

          const base64String = `data:image/png;base64, ${btoa(reducedArray)}`;
          // this.myImg = base64String;
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style></style>
