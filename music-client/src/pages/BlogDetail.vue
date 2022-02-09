<template>
  <div class="home" >
    <!--图片-->
    <div class="swiper-container" :style=style>

      <div class="focusinfo">
      <h1 class="entry-title">{{blog.blogTitle}}</h1>
        <span class="topic-line"></span>
        <p class="entry-census">
        <span>
          <img class="user" src="https://blog-img-qrx.oss-cn-beijing.aliyuncs.com/img/header.jpg" alt="">
        </span>
          <span class="user-name">cheifsteam</span>
          <span class="bull">.</span>
          <span class="text">发布于{{dateFormat(blog.createTime)}}</span>
          <span class="bull">.</span>
          <span class="text">阅览量{{blog.blogViews}}</span>
        </p>
      </div>
    </div>

    <!--热门歌单-->
    <div class="section markdown-body">
      <div  v-html="blog.blogContent">
        {{blog.blogContent}}
      </div>
    </div>
  </div>
</template>

<script>
import ContentList from '../components/ContentList'
import { HttpManager } from '../api/index'
import { mixin } from "../mixins";
import moment from 'moment'
import 'github-markdown-css/github-markdown.css'
export default {
  name: 'blog-detail',
  mixin:[mixin],
  components: {
    ContentList
  },

  data () {
    return {
      blog:{
        blogTitle:'',
        blogImg:'',
        createTime:'',
        blogViews:0,
        blogContent:''
      },
      index:0,
      currentPage:10,
      style:{},

    }
  },
  created () {
    // 获取歌单列表
    this.getBlogDetail()
  },
  watch: {
    'blog.blogImg' :function () {
      this.style={background:'url('+this.blog.blogImg+')',backgroundSize:  '100% 100%'}
    },
    deep:true
  },
  filters:{
    ellipsis(value){
      if (!value) return '';
      if (value.length > 30) {
        return value.slice(0,50) + '...'
      }
      return value
    }
  },
  methods: {
    getBlogDetail() {
      let id = this.$route.query.id
      HttpManager.getBlogDetail(id)
        .then(res => {
          console.log(res.data)
          this.blog = res.data

        })
        .catch(err => {
          console.log(err)
        })
    },
    dateFormat(data) {
      return moment(new Date(data)).utcOffset(0).format('YYYY-MM-DD HH:mm')
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/blogDeatil.scss';
@import '../assets/css/home.scss';
</style>
