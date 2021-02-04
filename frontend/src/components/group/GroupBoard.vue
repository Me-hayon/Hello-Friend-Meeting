<template>
  <div>
    <v-simple-table>
      <template v-slot:default>
        <tbody>
          <tr style="height:100px" v-for="item in tableNotice" :key="item.bno">
            <td>{{ item.btitle }}</td>
            <td>{{ item.bdate }}<br />{{ item.writerName }}</td>
          </tr>
          <tr style="height:100px" v-for="item in table" :key="item.bno">
            <td>{{ item.btitle }}</td>
            <td>{{ item.bdate }}<br />{{ item.writerName }}</td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  props: ['gno'],
  data() {
    return {
      table: [],
      tableNotice: [],
    };
  },
  created() {
    var params = new URLSearchParams();
    params.append('bgno', this.gno);
    axios
      .post('http://localhost:8080/getBoardList', params)
      .then((response) => {
        this.table = response.data.notNotice;
        this.tableNotice = response.data.notice;
        for (var i = 0; i < this.table.length; i++) {
          this.table[i].writerName = response.data.notNoticeWriter[i];
        }
        for (i = 0; i < this.tableNotice.length; i++) {
          this.tableNotice[i].writerName = response.data.noticeWriter[i];
        }
      })
      .catch((error) => {
        console.log(error);
      });
  },
};
</script>

<style></style>
