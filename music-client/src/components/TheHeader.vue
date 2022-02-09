<template>
  <div class="the-header">
    <div class="site-top">
<!--    图标-->
    <div onmouseenter="" class="header-logo" @click="goHome">

      <span>{{Name}}</span>
    </div>
  <div class="lower">
    <ul class="navbar" ref="change">
      <li  v-for="item in navMsg" :key="item.path" @click="goPage(item.path, item.name)">
        {{item.name}}

      </li>
      <div class="search-btn"  @click="goSearch()" >
        <svg class="icon" >
          <use :xlink:href="SOUSUO"></use>
        </svg>
      </div>
    </ul>
  </div>

    </div>
    <search :visible=visible @listenToChildEvent="showMsgFromChild"></search>
    <!--设置-->
    </div>

</template>

<script>
import mixin from '../mixins'
import { mapGetters } from 'vuex'
import { navMsg, loginMsg, menuList } from '../assets/data/header'
import { ICON } from '../assets/icon/index'
import Search from "./Search";

export default {
  name: 'the-header',
  components: {Search},
  mixins: [mixin],
  data () {
    return {
      Name: 'cheifsteam',
      navMsg: navMsg, // 左侧导航栏
      keywords: '',
      ERJI: ICON.ERJI,
      SOUSUO: ICON.SOUSUO,
      visible:false
    }
  },
  mounted () {


    // 点击“菜单”内部时，阻止事件冒泡。(这样点击内部时，菜单不会关闭)
    // document.querySelector('.the-header').addEventListener('click', function (e) {
    //   e.stopPropagation()
    // }, false)
    // document.addEventListener('click', function () {
    //   document.querySelector('.the-header').classList.remove('show')
    // }, false)
    window.addEventListener('scroll', function() {
          document.querySelector('.the-header').classList.toggle('sticky',window.scrollY > 0 )
      });
  },
  methods: {
    goHome () {
      this.$router.push({path: '/'})
    },
    goPage (path, value) {
        this.$router.push({path: path})

    },
    goSearch () {
      this.visible=true;
      this.stop()
    },
    showMsgFromChild(data){

      this.visible=data; //将子组件触发该事件时传递的参数赋值给父组件的属性

      console.log(data);

    },
    /***取消滑动限制***/

    stop(){
      let mo=function(e){e.preventDefault();};
      document.body.style.overflow='hidden';
      document.addEventListener("touchmove",mo,false);//禁止页面滑动
    }

  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/the-header.scss';

</style>
