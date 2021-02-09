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
  console.log(param)

  var params = new URLSearchParams();
  params.append('startDate',param.sstartdate)
  params.append('endDate',param.senddate)
  params.append('title',param.stitle)
  params.append('content',param.scontent)
  params.append('smaster',param.smaster)
  params.append('gno',param.sgno)
  axios
    .post("addSchedule", params)
    .then((res) => {
      console.log(res);
      console.log("ehoTek")
    })
    .catch((error) => {
      console.log(error);
    });
};
const getTime = () => {
  return;
};
