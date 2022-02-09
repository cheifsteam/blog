<template>
  <div class="home">
    <!--图片-->
    <div class="swiper-container" >
      <div class="focusinfo">
        <h1 class="cat-title">分类</h1>
      </div>
    </div>
      <div class="index-center">
        <content-list :contentList="categoryList" path="song-list-album"></content-list>
    </div>
  </div>
</template>

<script>
import ContentList from '../components/ContentList'
import { HttpManager } from '../api/index'
import mixin from "../mixins";
import moment from 'moment'
import 'github-markdown-css/github-markdown.css'
export default {
  name: 'blog-index',
  mixin:[mixin],
  components: {
    ContentList
  },

  data () {
    return {
      categoryList: [], // 歌单列表
      index:0,
      currentPage:10,
    }
  },
  created () {
    // 获取歌单列表
    this.getAllCategory()
  },
  methods: {
    getAllCategory() {
      HttpManager.getAllCategory()
        .then(res => {
          console.log(res.data)
          this. categoryList= res.data
        })
        .catch(err => {
          console.log(err)
        })
    },
    dateFormat(data) {
      return moment(new Date(data).getTime()).format('YYYY-MM-DD HH:mm')
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/home.scss';
@import '../assets/css/blogIndex.scss';
</style>
