<template>
  <div>
  <div>

    <el-form class="searchInfo-form"  method="get"  role="search" action="http://localhost:8080/blogSearch">
      <div class="search-form-inner ">
        <div class="micro">
          <p class="micro mb-">想要找点什么呢？</p>
          <svg class="icon-search" >
            <use :xlink:href="SOUSUO"></use>
          </svg>
          <input  class="text-input" type="search" name="filterName" placeholder="搜索" required="">
        </div>
      </div>
    </el-form>
    <h1 class="page-title">没有找到任何东西！</h1>
  </div>
    <div class="sorry">
      <p>没有找到你想要的，看看其他的吧。</p>
      <div class="sorry-inner">

        <ul class="search-no-reasults" >
          <li v-for="(item,i) in blogList"> <router-link class="link" :to="{path: '/blogDetail', query: {id:item.id }}">{{item.blogTitle}}</router-link></li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import {ICON} from "../assets/icon";
import mixin from "../mixins";
import {HttpManager} from "../api";

export default {
  name: "sorry",
  data () {
    return {
      SOUSUO: ICON.SOUSUO,
      currentPage:10,
      total:0,
      blogList:{}
    }
  },
  created() {
    this.getAllBlog()
  },
  methods : {
    getAllBlog () {
      HttpManager.getAllBlogs(this.currentPage)
        .then(res => {
          this.blogList = res.data.data
          this.total=res.data.total
          console.log(this.total)
        })
        .catch(err => {
          console.log(err)
        })
    },
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/sorry.scss';
</style>
