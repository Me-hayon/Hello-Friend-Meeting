<template>
  <div>
    그룹채팅

    <button @click="aa">aaaaaaaa</button>
  </div>
</template>

<script>
export default {
  props: ['gno', 'memberStatus'],
  methods: {
    aa() {
      alert(this.memberStatus + ' ' + this.gno);
    },
  },
};
</script>

<style></style>
