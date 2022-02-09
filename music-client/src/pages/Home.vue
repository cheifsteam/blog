<template>
  <div class="home">
    <!--轮播图-->
    <div class="swiper-container" >
      <div class="focusinfo">
        <h1 class="center-text glitch is-glitching Ubuntu-font">cheifsteam</h1>
        <div class="header-info">
          <marquee class="p-text">(≥◇≤)(≥◇≤)欢迎来到cheifsteam博客(≥◇≤)(≥◇≤)</marquee>
        </div>
      </div>
    </div>
    <!--热门歌单-->
    <div class="section">
    <h1 class="main-title">
      <i class="fa el-icon-collection-tag"> </i> 热门文章
    </h1>
      <div v-model="index" v-for="(item,i) in blogList" :value="i">
      <article class="post-list-thumb post-list-show" :style="i===index?style:{}" @mouseover="Mouseover(i)"
               @mouseleave="Mouseleave()">
        <div class="post-thumb" :style="  {background:'url(' + item.blogImg + ')',backgroundSize: '100% 100%'}" @mouseover="Mouseover(i)"
             @mouseleave="Mouseleave()">
        </div>
        <div class="post-content-wrap">
          <div class="post-content">
            <div class="post-date">
              <i class="iconfont el-icon-collection-tag">发布于{{dateFormat(item.createTime)}}</i>
            </div>
            <router-link class="link-text" :to="{path: '/blogDetail', query: {id:item.id }}">
            <h3 class="h3-text" >{{item.blogTitle}}</h3>
            </router-link>
            <div class="post-meta">
              <span class="post-span" style=" background-color: #3cdc82;"><i class="iconfont el-icon-view" style=" border-radius: 7px;">{{item.blogViews}}热度</i></span>
                <span class="post-span" :style="randomRgb(index)" v-for="(o,index) in item.blogTag.split(',')"  >
                  <router-link class="link-text" :to="{path: '/blogTagInfo', query: {tagName:o}}">
                  <i class="iconfont el-icon-collection-tag" >
                  {{o}}
                  </i>
                  </router-link>
                  </span>
            </div>
            <div class="float-content">
              <p>{{item.blogContent| ellipsis}}</p>
            </div>
          </div>
        </div>
      </article>

      </div>
      <div class="pagination">
      <el-button v-if="isdata" class="el-button"  size="large" plain @click="getMore()">更多文章</el-button>
      <span class="span" v-else>很高兴你翻到这里，但是真的没有了...</span>
      </div>
      </div>
  </div>
</template>

<script>
import ContentList from '../components/ContentList'
import { HttpManager } from '../api/index'
import mixin from "../mixins";
import moment from 'moment'
export default {
  name: 'home',
  mixin:[mixin],
  components: {
    ContentList
  },
  data () {
    return {
      blogList: [], // 歌单列表
      colorlists: [
        'rgb(226, 106, 198)',
        'rgb(84, 171, 226)',
        'rgb(153, 168, 235)',
        'rgb(103, 228, 235)',
        'rgb(203, 128, 105)',
      ],
      index:0,
      currentPage:10,
      pageSize:10,
      isdata:true,
      style:{},
      total:0,
      img:{}
    }
  },
  created () {
    // 获取歌单列表
    this.getAllBlog()
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
    getAllBlog () {
      HttpManager.getAllBlogs(this.currentPage)
        .then(res => {
          this.blogList = res.data.data
          this.total=res.data.total
        })
        .catch(err => {
          console.log(err)
        })
    },
    //颜色对象
    randomRgb (data) {
      console.log(data)
        return {
        background: this.colorlists[data]
        }
      },
    Mouseover(data) {
      console.log("进入 悬停区域");
      console.log(data);
       // 进入 悬停区域,更改文字颜色
      if (data===this.index) {
        this.style = {'background-color': 'rgba(255,235,235,9)','transition': 'all 1' ,}
      }else {
        this.style ={}
        this.index=data
      }
    },

// 鼠标"离开"触发此方法
    Mouseleave(index) {
      console.log("离开 悬停区域");
      this.style ={}; // 离开 悬停区域,文字颜色复原
      this.img = {'transition': 'all 2s' ,'transform':'scale(1.00)'}
    },
    dateFormat (data) {
      return moment(new Date(data)).utcOffset( 0).format('YYYY-MM-DD HH:mm')
    },
    getMore() {
      if (this.currentPage>=this.total) {
        this.isdata = false
      } else {
        this.currentPage+=this.pageSize
        this.isdata= true
        this.getAllBlog()

      }
    }
  }

}
</script>

<style lang="scss" scoped>
@import '../assets/css/home.scss';
</style>
