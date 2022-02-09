<template>
<div>
  <div class="div-content"  v-for="(blog,index) in blogs ">
    <div class="feature">
      <img :src="blog.blogImg">
    </div>

    <h1 class="h1-title">{{blog.blogTitle|ellipsis}}

    </h1>
    <div class="p-time">
      发布于{{dateFormat(blog.createTime)}}
    </div>
    <div class="content">
      {{blog.blogContent|ellipsis}}
    </div>
    <div style="height: 100px">
    </div>
  </div>
</div>
</template>

<script>
import moment from 'moment'

export default {
  name: "blog-list",
  props:{
    blogs:Array
  },
  methods: {
    dateFormat(data) {
      return moment(new Date(data).getTime()).format('YYYY-MM-DD HH:mm')
    }
  },
  filters:{
    ellipsis(value){
      if (!value) return '';
      if (value.length > 50) {
        return value.slice(0,50) + '...'
      }
      return value
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/blogIndex.scss';
@import '../assets/css/blogInfo.scss';
</style>
