export default {
  getIsHeader: function(state) {
    return state.isHeader;
  },
  getIsFooter: function(state) {
    return state.isFooter;
  },
  getGno: function (state) {
    return state.vuexGno;
  },
  getUno: function (state) {
    return state.vuexUno;
  },
  getMemberStatus: function (state) {
    return state.vuexMemberStatus;
  },
  getBno: function (state) {
    return state.vuexBno;
  }
};
