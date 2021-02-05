export default {
  setIsHeader: function(state, payload) {
    state.isHeader = payload;
  },
  setIsFooter: function(state, payload) {
    state.isFooter = payload;
  },
  setGno: function (state, payload) {
    state.vuexGno = payload;
  },
  setUno: function (state, payload) {
    state.vuexUno = payload;
  },
  setMemberStatus: function (state, payload) {
    state.vuexMemberStatus = payload;
  }
};
