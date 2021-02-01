<template>
  <div>
    <b-row
      style="border-top: 1px solid black; border-bottom: 1px solid black; margin-top: 30px;"
    >
      <b-col
        style="border-right: 1px solid black; text-align: center; padding: 10px 0;"
      >
        <router-link to="/community/group"
          ><h5 style="margin-bottom: 0;">
            <strong>그룹</strong>
          </h5></router-link
        >
      </b-col>
      <b-col style="text-align: center; padding: 10px 0;">
        <router-link to="/community/friend"
          ><h5 style="margin-bottom: 0;">
            <strong>친구</strong>
          </h5></router-link
        >
      </b-col>
    </b-row>

    <button v-b-modal.modal-center class="add-group-btn">그룹+</button>
    <b-modal id="modal-center" centered title="그룹생성">
      <b-row style="margin-bottom:25px">
        <b-col style="margin-top:10px">그룹명</b-col>
        <b-col><input type="text" class="groupName"/></b-col>
      </b-row>
      <b-row style="margin-bottom:25px">
        <b-col>카테고리</b-col>
        <b-col
          ><select name="category" id="category-select">
            <option value="여행">여행</option>
            <option value="스터디">스터디</option>
            <option value="게임">게임</option>
            <option value="영화">영화</option></select
          ></b-col
        >
      </b-row>
      <b-row style="margin-bottom:25px">
        <b-col>노출범위</b-col>
        <b-col>
          <div style="">
            <input
              class="groupRadio"
              type="radio"
              id="친구만"
              name="drone"
              value="친구만"
              checked
            />
            <label for="친구만">친구만</label>
          </div>

          <div>
            <input
              type="radio"
              id="친구의친구까지"
              name="drone"
              value="친구의친구까지"
            />
            <label for="친구의친구까지">친구의친구까지</label>
          </div>

          <div>
            <input type="radio" id="비공개" name="drone" value="비공개" />
            <label for="비공개">비공개</label>
          </div>
        </b-col>
      </b-row>
    </b-modal>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
a:hover {
  text-decoration: none;
}
.add-group-btn {
  width: 70px;
  height: 70px;
  border-radius: 50%;
  background-color: blue;
  font-size: 20px;
  color: white;
  text-align: center;

  position: fixed;
  right: 5%;
  bottom: 10%;
}
.groupName {
  border: 1px solid black;
}
.groupRadio {
}
</style>
