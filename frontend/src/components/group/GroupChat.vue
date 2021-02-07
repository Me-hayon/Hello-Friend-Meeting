<template>
  <div>
    그룹채팅

    <button @click="aa">aaaaaaaa</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      memberStatus: this.$store.getters.getMemberStatus,
      gno: this.$store.getters.getGno,
    };
  },
  methods: {
    aa() {
      alert(this.memberStatus + ' ' + this.gno);
    },
  },
  computed: {
    vuexGno() {
      return this.$store.getters.getGno;
    },
    vuexUno() {
      return this.$store.getters.getUno;
    },
    vuexBno() {
      return this.$store.getters.getBno;
    },
    vuexMemberStatus() {
      return this.$store.getters.getMemberStatus;
    },
  },
  watch: {
    vuexGno(val) {
      this.gno = val;
    },
    vuexUno(val) {
      this.uno = val;
    },
    vuexBno(val) {
      this.bno = val;
    },
    vuexMemberStatus(val) {
      this.memberStatus = val;
    },
  },
};
</script>

<style></style>
