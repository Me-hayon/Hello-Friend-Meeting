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
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
a:hover {
  text-decoration: none;
}

.groupName {
  border: 1px solid black;
}
.groupRadio {
}

input {
  height: auto;
}
</style>
