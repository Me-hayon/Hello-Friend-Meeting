<template>
  <v-card>
    <v-tabs v-model="tab" background-color="transparent" grow>
      <v-tab>게시판</v-tab>
      <v-tab>일정</v-tab>
      <v-tab>채팅</v-tab>
      <v-tab>그룹원</v-tab>
    </v-tabs>

    <v-tabs-items style="height:800px" v-model="tab">
      <v-tab-item>
        <v-card flat
          ><v-simple-table>
            <template v-slot:default>
              <tbody>
                <tr style="height:100px" v-for="item in table" :key="item.name">
                  <td>{{ item.name }}</td>
                  <td>{{ item.date }}<br />{{ item.writer }}</td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat>
          일정
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat>
          채팅
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card flat>
          그룹원
        </v-card>
      </v-tab-item>
    </v-tabs-items>
  </v-card>
</template>
<script>
export default {
  data() {
    return {
      tab: null,
      table: [
        {
          name: "<공지> 가입인사양식",
          date: "1월 19일 17:13분",
          writer: "박봉현",
        },
        {
          name: "Ice cream sandwich",
          date: "1월 19일 17:13분",
          writer: "박봉현",
        },
        {
          name: "Eclair",
          date: "1월 19일 17:13분",
          writer: "박봉현",
        },
        {
          name: "Cupcake",
          date: "1월 19일 17:13분",
          writer: "박봉현",
        },
        {
          name: "Gingerbread",
          date: "1월 19일 17:13분",
          writer: "박봉현",
        },
        {
          name: "Jelly bean",
          date: "1월 19일 17:13분",
          writer: "박봉현",
        },
        {
          name: "Lollipop",
          date: "1월 19일 17:13분",
          writer: "박봉현",
        },
        {
          name: "Honeycomb",
          date: "1월 19일 17:13분",
          writer: "박봉현",
        },
        {
          name: "Donut",
          date: "1월 19일 17:13분",
          writer: "박봉현",
        },
        {
          name: "KitKat",
          date: "1월 19일 17:13분",
          writer: "박봉현",
        },
      ],
    };
  },
};
</script>

<style></style>
