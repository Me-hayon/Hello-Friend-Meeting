export default {
  setIsHeader: function(state, payload) {
    state.isHeader = payload;
  },
  setIsFooter: function(state, payload) {
    state.isFooter = payload;
  },
  setGno: function(state, payload) {
    state.vuexGno = payload;
  },
  setUno: function(state, payload) {
    state.vuexUno = payload;
  },
  setMemberStatus: function(state, payload) {
    state.vuexMemberStatus = payload;
  },

  OPEN_CALENDAR_DIALOG(state, payload) {
    console.log("dddddasdffasdfasfasf");
    state.calendar.startDate = payload.date;
    state.calendar.startTime = payload.time;
    state.calendar.hasTime = payload.hasTime;
    state.dialog = true;
  },
  CLOSE_CALENDAR_DIALOG(state) {
    state.dialog = false;
  },
  ADD_EVENTS(state, evnet) {
    state.events[0] = evnet;
    state.events[1] = evnet;
    state.events[2] = evnet;
    state.dialog = false;
  },
  ADD_EVENT(state, getEvent) {
    state.events.push(getEvent);
    state.dialog = false;
    state.event = initEvent();
  },
};

function initEvent() {
  return {
    startDate: "",
    startTime: "",
    endDate: "",
    endTime: "",
    content: "",
    title: "",
  };
}
