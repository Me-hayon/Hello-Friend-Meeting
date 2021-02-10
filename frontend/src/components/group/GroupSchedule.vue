<template>
  <div class="pa-5">
    <v-row>
      <v-col cols="12" md="6" class="mb-4">
        <v-row>
          <v-col cols="2" align="center">
            <v-icon @click="preMonth">mdi-chevron-left</v-icon>
          </v-col>
          <v-col cols="8">
            <h3 style="text-align:center">{{ curYM }}</h3>
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
        </v-sheet>
      </v-col>
    </v-row>
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
    console.log(today.getMonth());
    this.curYear = year;
    this.curMonth = month + 1;
    if (this.curMonth < 10) this.curMonth = '0' + this.curMonth;
    this.start = startDate;
    console.log(this.curYear);
    console.log(this.curMonth);
    console.log(this.start);
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
      curMonth: this.month,
      curYM: '',
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
    showEvent() {},
    moreEvent() {},
    getSchedules() {
      var params = new URLSearchParams();
      params.append('gno', this.gno);
      axios.post('getSchedulesList', params).then((resp) => {
        if (resp.data.isPresent)
          this.$store.commit('ADD_EVENTS', resp.data.list);
      });
    },
  },
};
</script>
