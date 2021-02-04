<template>
  <div>
    <v-simple-table>
      <template v-slot:default>
        <tbody>
          <tr
            @click="boardDetail(item.bno)"
            style="height:100px"
            v-for="item in tableNotice"
            :key="item.bno"
          >
            <td>{{ item.btitle }}</td>
            <td>{{ item.bdate }}<br />{{ item.writerName }}</td>
          </tr>
          <tr
            @click="boardDetail(item.bno)"
            style="height:100px"
            v-for="item in table"
            :key="item.bno"
          >
            <td>{{ item.btitle }}</td>
            <td>{{ item.bdate }}<br />{{ item.writerName }}</td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
    <button v-b-modal.create-article class="create-article-btn">게시글+</button>
    <b-modal
      id="create-article"
      centered
      title="게시글작성"
      @ok="createArticle"
    >
      <b-row style="margin-bottom:10px">
        <b-col>제목</b-col>
        <b-col><b-form-input></b-form-input></b-col>
      </b-row>
      <b-form-textarea rows="8" style="margin-bottom:10px"></b-form-textarea>

      <b-row>
        <b-col>댓글</b-col>
        <b-col></b-col>
      </b-row>
    </b-modal>
  </div>
</template>

<script>
import axios from "axios";
export default {
  props: ["gno", "isGmaster"],
  data() {
    return {
      table: [],
      tableNotice: [],
    };
  },
  created() {
    var params = new URLSearchParams();
    params.append("bgno", this.gno);
    axios
      .post("http://localhost:8080/getBoardList", params)
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
  methods: {
    boardDetail(bno) {
      this.$router.push({ name: "GroupBoardDetail", params: { bno } });
    },
    createArticle() {},
  },
};
</script>

<style>
.create-article-btn {
  width: 70px;
  height: 70px;
  border-radius: 50%;
  background-color: blue;
  font-size: 15px;
  color: white;
  text-align: center;

  position: fixed;
  right: 5%;
  bottom: 10%;
}
</style>
