<template>
  <div class="pa-5">
    <v-row>
      <v-col cols="12" md="6" class="mb-4">
        <v-row>
          <v-col cols="2" align="center">
            <v-icon @click="preMonth">mdi-chevron-left</v-icon>
          </v-col>
          <v-col cols="8" align="center">
            <h3 align="center">{{ curYM }}</h3>
          </v-col>
          <v-col cols="2" align="center">
            <v-icon @click="nextMonth">mdi-chevron-right</v-icon>
          </v-col>
        </v-row>
        <v-sheet height="500">
          <v-calendar
            :event-color="getEventColor"
            :events="localEvents"
            :start="start"
            :type="type"
            @click:date="open"
            @click:event="showEvent"
            @click:more="moreEvent"
            @click:time="open"
            ref="calendar"
            v-model="start"
          ></v-calendar>
          <v-menu
            v-model="selectedOpen"
            :close-on-content-click="false"
            :activator="selectedElement"
            offset-x
          >
            <v-card color="grey lighten-4" min-width="200px" flat>
              <v-toolbar :color="selectedEvent.color" dark>
                <v-toolbar-title v-html="selectedEvent.name"></v-toolbar-title>
                <v-spacer></v-spacer>
                <v-icon
                  v-if="selectedEvent.smasterEmail === myEmail"
                  @click="delSchedule"
                  >mdi-trash-can</v-icon
                >
                <v-icon @click="closeSchedule">mdi-close</v-icon></v-toolbar
              ><v-spacer
                >{{ selectedEvent.content }}
                <hr />
                <ul class="mb-0">
                  <li
                    v-for="participant in selectedEvent.participants"
                    :key="participant.uno"
                  >
                    {{ participant.name }}
                  </li>
                </ul>
              </v-spacer></v-card
            >
          </v-menu>
        </v-sheet>
      </v-col>
    </v-row>
    <v-dialog v-model="moreEventsDialog" width="500">
      <v-card>
        <v-card-title class="headline grey lighten-2">
          {{ moreDate }}
        </v-card-title>

        <v-card-text>
          <p
            v-for="event in moreEvents"
            :key="event.sno"
            @click="showEventAtMore(event)"
          >
            {{ event.name }}
          </p>
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" text @click="moreEventsDialog = false">
            close
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <Dialog />
  </div>
</template>

<script>
import Dialog from '@/components/group/Dialog.vue';
import axios from 'axios';
export default {
  components: {
    Dialog,
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
    events() {
      return this.$store.getters.getEvents;
    },
    eventsNum() {
      return this.$store.getters.getEvents.length;
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
    events(val) {
      this.localEvents = val;
      // this.getSchedules(); //led에디션
    },
    eventsNum(val) {
      this.getSchedules();
    },
    curMonth() {
      this.start = `${this.curYear}-${this.curMonth}-01`;
      this.curYM = `${this.curYear} . ${this.curMonth}`;
    },
  },
  created() {
    const today = new Date();
    const year = today.getFullYear();
    const month = today.getMonth();
    const date = today.getDate();
    const startDate = `${year}-${month}-${date}`;
    this.curYear = year;
    this.curMonth = month + 1;
    if (this.curMonth < 10) this.curMonth = '0' + this.curMonth;
    this.start = startDate;
    this.getSchedules();
  },

  data() {
    return {
      localEvents: [],
      openedDialog: {
        sno: { type: Number },
        sgno: { type: Number },
        smaster: { type: Number },
        senddate: '',
        sstartdate: '',
        stitle: '',
        sspace: '',
        scontetnt: '',
      },
      memberStatus: this.$store.getters.getMemberStatus,
      gno: this.$store.getters.getGno,
      bno: this.$store.getters.getBno,
      uno: this.$store.getters.getUno,
      dateOpen: false,
      start: this.startDate,
      type: 'month',
      curYear: this.year,
      curMonth: this.month + 1,
      curYM: '',
      selectedEvent: {},
      selectedElement: null,
      selectedOpen: false,
      myEmail: window.sessionStorage.getItem('user-email'),
      moreEvents: '',
      moreEventsDialog: false,
      moreDate: '',
    };
  },
  methods: {
    preMonth() {
      this.curMonth *= 1;
      this.curMonth--;
      if (this.curMonth === 0) {
        this.curMonth = 12;
        this.curYear *= 1;
        this.curYear--;
        this.curYear += '';
      }
      this.curMonth += '';
      if (this.curMonth.length === 1) this.curMonth = '0' + this.curMonth;
    },
    nextMonth() {
      this.curMonth *= 1;
      this.curMonth++;
      if (this.curMonth === 13) {
        this.curMonth = 1;
        this.curYear *= 1;
        this.curYear++;
        this.curYear += '';
      }
      this.curMonth += '';
      if (this.curMonth.length === 1) this.curMonth = '0' + this.curMonth;
    },
    open(date) {
      console.log(date);
      this.$store.commit('OPEN_CALENDAR_DIALOG', date);
    },
    getEventColor(event) {
      return event.color;
    },
    showEvent({ nativeEvent, event }) {
      var params = new URLSearchParams();
      params.append('sno', event.sno);
      axios.post('getScheduleParticipants', params).then((resp) => {
        var len = resp.data.list.length;
        var participantList = [];
        for (var i = 0; i < len; i++) {
          var participantInfo = resp.data.list[i];
          participantInfo.email = resp.data.emailList[i];
          participantInfo.name = resp.data.nameList[i];
          participantList.push(participantInfo);
        }
        const open = () => {
          this.selectedEvent = event;
          this.selectedElement = nativeEvent.target;
          setTimeout(() => {
            this.selectedOpen = true;
          }, 10);
        };

        if (this.selectedOpen) {
          this.selectedOpen = false;
          setTimeout(open, 10);
        } else open();
        nativeEvent.stopPropagation();
        this.selectedEvent.participants = participantList;
      });
    },
    showEventAtMore(event) {
      var params = new URLSearchParams();
      params.append('sno', event.sno);
      axios.post('getScheduleParticipants', params).then((resp) => {
        var len = resp.data.list.length;
        var participantList = [];
        for (var i = 0; i < len; i++) {
          var participantInfo = resp.data.list[i];
          participantInfo.email = resp.data.emailList[i];
          participantInfo.name = resp.data.nameList[i];
          participantList.push(participantInfo);
        }
        const open = () => {
          this.selectedEvent = event;
          setTimeout(() => {
            this.selectedOpen = true;
          }, 10);
        };
        if (this.selectedOpen) {
          this.selectedOpen = false;
          setTimeout(open, 10);
        } else open();
        this.selectedEvent.participants = participantList;
      });
    },
    moreEvent(date) {
      console.log(date.date);
      this.moreDate = date.date + '의 일정은??';
      console.log(this.localEvents);
      this.moreEvents = [];
      var selectedDate = this.returnToNum(date.date);
      for (var i = 0; i < this.localEvents.length; i++) {
        if (
          this.returnToNum(this.localEvents[i].start.split(' ')[0]) <=
            selectedDate &&
          this.returnToNum(this.localEvents[i].end.split(' ')[0]) >=
            selectedDate
        ) {
          this.moreEvents.push(this.localEvents[i]);
        }
      }
      console.log(this.moreEvents);
      this.moreEventsDialog = true;
    },
    getSchedules() {
      var params = new URLSearchParams();
      params.append('gno', this.gno);
      axios.post('getSchedulesList', params).then((resp) => {
        if (resp.data.isPresent) {
          var eventList = resp.data.list;
          var emailList = resp.data.emailList;
          for (var i = 0; i < eventList.length; i++)
            eventList[i].smasterEmail = emailList[i];
          this.$store.commit('ADD_EVENTS', resp.data.list);
        }
      });
    },
    delSchedule() {
      console.log(this.selectedEvent);
      var params = new URLSearchParams();
      params.append('sno', this.selectedEvent.sno);
      axios.post('delSchedule', params).then(() => {
        this.getSchedules();
        this.selectedOpen = false;
        alert('일정을 삭제했습니다.');
      });
      this.moreEventsDialog = false;
    },
    closeSchedule() {
      this.selectedOpen = false;
    },
    returnToNum(date) {
      var toReturn = date.replace(/-/g, '');
      toReturn *= 1;
      return toReturn;
    },
  },
};
</script>
