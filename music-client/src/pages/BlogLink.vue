<template>
  <div class="home" >

    <!--图片-->
    <div class="swiper-container" >
      <div class="focusinfo">
        <h1 class="cat-title">友链</h1>
      </div>
    </div>

    <div class="links" v-for="(item,index) in arr">
      <h3 class="link-title">
      <span class="link-fix">{{optionKV (TYPE,item.linkType)}}</span>
      </h3>
      <ul  class="" v-for="(link,index) in item.chlids">
        <li class="link-item">
          <span class="link-item-inner" @click="hrefClick(link.linkUrl)">
          <img class="lazyload" :src=link.linkImg>
          <span class="sitename">{{link.linkName}}</span>
          <div class="linkdes">{{link.linkDescription}}</div>
          </span>
        </li>
      </ul>
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
  name: 'blog-link',
  mixin:[mixin],
  components: {
    ContentList
  },

  data () {
    return {
      index:0,
      currentPage:10,
      style:{},
      img:{},
      name:'',
      type:0,
      linkList:[],
      arr: [],
      TYPE: {FriendLink: {key: 0, value: '友链'}, Recommend: {key: 1, value: '推荐'}, PersonalWebsites: {key: 2, value: '个人网站'}}
    }
  },
  created () {
    // 获取歌单列表
    this.getAllLinks()
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
    hrefClick(url){
      console.log(url)
      window.location.href=url;//当前标签页
    },
    getArr() {
      this.linkList.forEach(item => {
        const parent = this.arr.find(cur => cur.linkType === item.linkType)
        if (parent) {
          parent.chlids.push(item)
        } else {
          const obj = {
            linkType: item.linkType,
            chlids: [item]

          }
          this.arr.push(obj)
        }
      })
      console.log('arr', this.arr)
    },
    optionKV(object, key) {

      let result = "";
      for (let enums in object) {
        if (key === object[enums]["key"]) {
          result = object[enums]["value"];
        }
      }
      return result;
    },

    getAllLinks() {
      HttpManager.getAllLinks()
        .then(res => {
          console.log(res.data)
          this.linkList = res.data.data
          console.log(this.linkList)
          this.getArr()
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
@import '../assets/css/blogInfo.scss';
@import '../assets/css/link.scss';
</style>
