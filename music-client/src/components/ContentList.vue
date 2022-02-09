<template>
  <div class="">
    <ul class="">
      <li class="content-item" v-for="(item, index) in contentList" :key="index">
        <div class="kuo" @click="goInfo(item)">
          <img class="item-img" :src="item.categoryImg" alt="">
          <p class="item-name">{{item.categoryName}}</p>
        </div>

      </li>
    </ul>
  </div>
</template>

<script>
import mixin from '../mixins'
import { ICON } from '../assets/icon/index'
import {HttpManager} from "../api";

export default {
  name: 'content-list',
  mixins: [mixin],
  props: {
    contentList: Array,
    path: String
  },
  data () {
    return {
      blog:{}
    }
  },
  methods:{
    goInfo (item) {
      let category = JSON.stringify(item);
      this.$router.push({path: '/blogInfo', query: {category: category}})
    }
  }

}
</script>

<style lang="scss" scoped>
@import '../assets/css/content-list.scss';
</style>
