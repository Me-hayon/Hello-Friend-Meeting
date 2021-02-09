import axios from "axios";

export default {
  async REQUEST_ADD_EVENT(context, calendar) {
    try {
      const response = await requestAddEvent(calendar);

      const addedEvent = makeEvent(response.data);
      context.commit("ADD_EVENT", addedEvent);
    } catch (e) {
      console.log("일정 추가 에러" + e);
    }
  },
};
const colors = ["blue", "indigo", "deep-purple", "green", "orange", "red"];
const makeEvent = (event) => {
  return {
    name: event.title,
    start: event.startDate + getTime(event.startTime),
    end: event.endDate + getTime(event.endTime),
    color: colors[Math.floor(Math.random() * 6)],
  };
};
const requestAddEvent = (param) => {
  axios
    .post("addSchedule", param)
    .then((res) => {
      console.log(res);
    })
    .catch((error) => {
      console.log(error);
    });
};
const getTime = () => {
  return;
};
