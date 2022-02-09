<template>
  <div class="home">
    <!--图片-->
    <div class="swiper-container" :style="{background:'url(' + category.categoryImg + ')',backgroundSize:  '100% 100%'}">
      <div class="focusinfo" >
        <h1 class="cat-title">{{category.categoryName}}</h1>
      </div>
    </div>
    <div class="tag">
      <h3 class="link-title">
        <span class="link-fix">热门标签  </span>
      </h3>
      <div v-for="(o,index) in arr" >
        <router-link class="link-text" :to="{path: '/blogTagInfo', query: {tagName:o}}">
        <span class="post-span tag-span" :style="randomRgb(index)"  >
                  <i class="iconfont el-icon-collection-tag" >
                  {{o}}
                  </i>
        </span>
        </router-link>
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
import moment from 'moment'
import 'github-markdown-css/github-markdown.css'
import BlogList from "../components/BlogList";
import Sorry from "../components/Sorry";
export default {
  name: 'blog-info',
  mixin:[mixin],
  components: {
    ContentList,
    BlogList,
    Sorry
  },

  data () {
    return {
      categoryList: [], // 歌单列表
      index:0,
      currentPage:10,
      pageSize:10,
      isdata:true,
      style:{},
      img:{},
      arr:[],
      total:0,
      category:{},
      blogList:[],
      colorlists: [
        'rgb(226, 106, 198)',
        'rgb(84, 171, 226)',
        'rgb(153, 168, 235)',
        'rgb(103, 228, 235)',
        'rgb(203, 128, 105)',
      ]
    }
  },
  created () {
    // 获取歌单列表
    this.getBlogByCategoryName()

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
    getTags () {
      const map =new Map()
      this.blogList.forEach(item => {
        let strings = item.blogTag.split(',');
        strings.forEach(s =>
            map.set(s,s))
        }
      )
      this.arr=map.keys()
      console.log('arr',this.arr)
    },
    getBlogByCategoryName(){
      this.category=JSON.parse(this.$route.query.category)
      HttpManager.getBlogByCategoryName(this.category.categoryName,this.currentPage)
        .then(res => {
          console.log(res.data)
          this.blogList= res.data.data
          this.total=res.data.total
          console.log(this.blogList)
          this.getTags()
        })
        .catch(err => {
          console.log(err)
        })
    },
    randomRgb (data) {
      console.log(data)
      return {
        background: this.colorlists[data%5]
      }
    },
    dateFormat(data) {
      return moment(new Date(data).getTime()).format('YYYY-MM-DD HH:mm')
    },
    getMore() {
      if (this.currentPage>=this.total) {
        this.isdata = false
      } else {
        this.currentPage+=this.pageSize
        this.isdata= true
        this.getBlogByCategoryName()
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
</style>
