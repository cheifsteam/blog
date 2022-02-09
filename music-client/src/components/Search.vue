<template>
  <div class="search-songs" @touchmove.prevent @mousewheel.prevent>
    <el-dialog :append-to-body="true"  :modal=false :fullscreen="visible" class="search"  :visible="visible" >
      <el-form class=" search-form fullscreen " method="get"  role="search" action="http://localhost:8080/blogSearch">
        <div class="search-form-inner ">
          <div class="micro">
            <p class="micro mb-">想要找点什么呢？</p>
            <svg class="icon-search" >
              <use :xlink:href="SOUSUO"></use>
            </svg>
            <input  class="text-input" type="search" name="filterName" placeholder="搜索" required="">
          </div>
        </div>
        <div class="search-close" @click="close()">
        </div>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import mixin from '../mixins'
import { mapGetters } from 'vuex'
import {ICON} from "../assets/icon";
import { HttpManager } from '../api/index'

export default {
  name: 'search',
  mixins: [mixin],
  props: {
    visible: Boolean
  },
  data () {
    return {
      SOUSUO: ICON.SOUSUO,
      filterName: ''
    }
  },
  methods: {
    close () {
      this.visible=false
      this.move()
     this.$emit('listenToChildEvent',this.visible)
    },
    move(){
      let mo=function(e){e.preventDefault();};
      document.body.style.overflow='';//出现滚动条
      document.removeEventListener("touchmove",mo,false);
    },
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/search.scss';
</style>
