<template>
  <div>
    <the-header/>
    <router-view class="music-content"/>
    <the-footer/>
  </div>
</template>

<script>
import TheHeader from '../components/TheHeader'
import TheFooter from '../components/TheFooter'

export default {
  components: {
    TheHeader,
    TheFooter
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/main.scss';
</style>
