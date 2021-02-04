<template>
  <div class="pa-5">
    <v-row>
      <v-col cols="12" md="6" class="mb-4">
        <v-row>
          <v-col cols="6">
            <v-menu
              ref="dateOpen"
              v-model="dateOpen"
              :close-on-content-click="false"
              :return-value.sync="start"
              offset-y
            >
              <template v-slot:activator="{ on }">
                <v-text-field
                  v-model="start"
                  label="Start Date"
                  prepend-icon="mdi-calendar"
                  dense
                  readonly
                  outlined
                  hide-details
                  v-on="on"
                ></v-text-field>
              </template>

              <v-date-picker v-model="start" no-title>
                <v-spacer />
                <v-btn text color="primary" @click="dateOpen = false"
                  >Cancel</v-btn
                >
                <v-btn text color="primary" @click="$refs.dateOpen.save(start)"
                  >OK</v-btn
                >
              </v-date-picker>
            </v-menu>
          </v-col>
          <v-col cols="6">
            <v-select
              v-model="type"
              :items="typeOptions"
              label="Type"
              class="my-auto"
              hide-details
              outlined
              dense
            ></v-select>
          </v-col>
        </v-row>
        <v-sheet height="500">
          <v-calendar
            ref="calendar"
            @click:date="open"
            :start="start"
            :type="type"
          ></v-calendar>
        </v-sheet>
      </v-col>
    </v-row>
  </div>
</template>

<script>
export default {
  created() {
    const today = new Date();
    console.log(today);
    const year = today.getFullYear();
    const month = today.getMonth();
    const date = today.getDate();
    const startDate = `${year}-${month}-${date}`;
    console.log(month);
    console.log(startDate);
  },
  methods: {
    open(date) {
      this.$store.commit("OPEN_CALENDAR_DIALOG", date);
    },
  },
  data() {
    return {
      dateOpen: false,
      start: this.startDate,
      type: "month",
      typeOptions: [
        { text: "Day", value: "day" },
        { text: "Week", value: "week" },
        { text: "Month", value: "month" },
      ],
    };
  },
};
</script>

<style></style>
