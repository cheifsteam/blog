<template>
  <div class="home">
    <!--图片-->
    <div class="swiper-container" :style="{background:'url(' +pic + ')',backgroundSize:'100% 100%'}">
      <div class="focusinfo" >
        <h1 class="cat-title">关于”{{filterName|ellipsis}}“的搜索结果</h1>
      </div>
    </div>

     <div class="section">
      <sorry v-if="total=== 0"></sorry>
       <div  v-else >
    <blog-list :blogs="blogList"></blog-list>
    <div class="pagination">
      <el-button v-if="isdata" class="el-button"  size="large" plain @click="getMore()">更多文章</el-button>
      <span class="span" v-else>很高兴你翻到这里，但是真的没有了...</span>
    </div>
       </div>
  </div>
  </div>
</template>

<script>
import ContentList from '../components/ContentList'
import { HttpManager } from '../api/index'
import mixin from "../mixins";
import 'github-markdown-css/github-markdown.css'
import {PICURL} from "../api/config";
import Search from "../components/Search";
import {ICON} from "../assets/icon";
import Sorry from "../components/Sorry";
import BlogList from "../components/BlogList";
export default {
  name: 'blog-tag-info',
  mixin:[mixin],
  components: {
    BlogList,
    Sorry,
    Search,
    ContentList
  },

  data () {
    return {
      SOUSUO: ICON.SOUSUO,
      index:0,
      currentPage:10,
      pageSize:10,
      isdata:true,
      total:0,
      style:{},
      img:{},
      arr:[],
      filterName:'',
      blogList:[],
      data:PICURL,
      pic :''
    }
  },
  created () {
    // 获取歌单列表
    this.getLike()
    this.getPicUrl()
  },
  filters:{
    ellipsis(value){
      if (!value) return '';
      if (value.length > 50) {
        return value.slice(0,50) + '...'
      }
      return value
    }
  },

  methods: {
    getLike(){
      this.filterName=this.$route.query.filterName
      HttpManager.getLike(this.filterName,this.currentPage)
        .then(res => {
          console.log(res.data)
          this.blogList= res.data.data
          this.total=res.data.total
          console.log(this.blogList)
        })
        .catch(err => {
          console.log(err)
        })
    },
    getPicUrl () {
      let index=1+Math.floor(Math.random() * 16);
      let result=this.data[index];
      this.pic=result
    },

    getMore() {
      if (this.currentPage>=this.total) {
        this.isdata = false
      } else {
        this.currentPage+=this.pageSize
        this.isdata= true
        this.getLike()
      }
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/home.scss';
@import '../assets/css/blogIndex.scss';
@import '../assets/css/blogInfo.scss';
@import '../assets/css/link.scss';
@import '../assets/css/sorry.scss';
</style>
