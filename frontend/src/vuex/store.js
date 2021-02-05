import Vue from 'vue';
import Vuex from 'vuex';
import getters from './getters';
import actions from './actions';
import mutations from './mutations';

Vue.use(Vuex);

const state = {
  isHeader: false,
  isFooter: false,
  vuexGno: { type: Number },
  vuexUno: { type: Number },
  vuexMemberStatus: { type: Number }
};

export default new Vuex.Store({
  state,
  mutations,
  getters,
  actions,
});
