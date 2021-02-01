<template>
  <b-row
    style="padding-top: 0px; padding-right: 30px;"
    class="justify-content-end"
  >
    <nav class="mobile-bottom-nav">
      <div class="mobile-bottom-nav__item">
        <div class="mobile-bottom-nav__item-content">
          <i class="material-icons"
            ><font-awesome-icon :icon="['fas', 'home']"
          /></i>
          홈
        </div>
      </div>
      <div class="mobile-bottom-nav__item">
        <div class="mobile-bottom-nav__item-content">
          <i class="material-icons"
            ><font-awesome-icon :icon="['fas', 'user-friends']"
          /></i>
          그룹/친구
        </div>
      </div>
      <div class="mobile-bottom-nav__item">
        <div class="mobile-bottom-nav__item-content">
          <i class="material-icons"
            ><font-awesome-icon :icon="['fas', 'envelope']"
          /></i>
          쪽지
        </div>
      </div>

      <div class="mobile-bottom-nav__item">
        <div class="mobile-bottom-nav__item-content">
          <i class="material-icons"
            ><font-awesome-icon :icon="['fas', 'user']"
          /></i>
          내정보
        </div>
      </div>
    </nav>
  </b-row>
</template>

<script>
export default {
  mounted() {
    let router = this.$router;
    let navItems = document.querySelectorAll('.mobile-bottom-nav__item');
    let path = window.location.pathname;

    if (path.includes('/feed')) {
      navItems[0].classList.add('mobile-bottom-nav__item--active');
    } else if (path.includes('/community')) {
      navItems[1].classList.add('mobile-bottom-nav__item--active');
    } else if (path.includes('/message')) {
      navItems[2].classList.add('mobile-bottom-nav__item--active');
    } else if (path.includes('/user')) {
      navItems[3].classList.add('mobile-bottom-nav__item--active');
    }

    navItems.forEach(function(e, i) {
      e.addEventListener('click', function(e) {
        if (i == 0) {
          router.push('/feed/main');
        } else if (i == 1) {
          router.push('/community');
        } else if (i == 2) {
          router.push('/message');
        } else if (i == 3) {
          router.push('/user/info');
        }
      });
    });
  },
  methods: {
    changeNav() {
      var router = this.$router;
      var navItems = document.querySelectorAll('.mobile-bottom-nav__item');

      navItems.forEach(function(e, i) {
        e.addEventListener('click', function(e) {
          navItems.forEach(function(e2, i2) {
            e2.classList.remove('mobile-bottom-nav__item--active');
            if (i2 == 0) {
              console.log('feed');
              router.push('/feed/main');
            } else if (i2 == 1) {
              console.log('community');
              router.push('/community');
            } else if (i2 == 2) {
              router.push('/message');
            } else if (i2 == 3) {
              router.push('/user/info');
            }
          });
          this.classList.add('mobile-bottom-nav__item--active');
        });
      });
    },
  },
};
</script>

<style lang="scss">
.mobile-bottom-nav {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  z-index: 1000;

  //give nav it's own compsite layer
  will-change: transform;
  transform: translateZ(0);

  display: flex;

  height: 50px;

  box-shadow: 0 -2px 5px -2px #333;
  background-color: #fff;

  &__item {
    border: 1px black solid;
    width: 25%;
    flex-grow: 1;
    text-align: center;
    font-size: 12px;

    display: flex;
    flex-direction: column;
    justify-content: center;
  }
  &__item--active {
    //dev
    color: red;
  }
  &__item-content {
    display: flex;
    flex-direction: column;
  }
}
</style>
