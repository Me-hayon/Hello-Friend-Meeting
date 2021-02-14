<template>
  <div></div>
</template>

<script>
export default {
  //그룹에서 번개모임 신청하면
  //그룹원들한테 다 알림감
  //수락누르면 지도에 해당 사용자도 뜨도록
  //소켓통신사용하면 될듯
  created() {
    function getLocation() {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(
          function(position) {
            console.log(position.coords.latitude);
            console.log(position.coords.longitude);
            alert(position.coords.latitude + " " + position.coords.longitude);
          },
          function(error) {
            console.error(error);
          },
          {
            enableHighAccuracy: false,
            maximumAge: 0,
            timeout: Infinity,
          }
        );
      } else {
        alert("GPS를 지원하지 않습니다");
      }
    }
    getLocation();
  },
};
</script>

<style></style>
