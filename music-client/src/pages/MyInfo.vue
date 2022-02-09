<template>
  <div id="wrapper">
    <div id="main">
      <div class="avatar">
      </div>
      <div class="user-div"> <img class="user" src="https://blog-img-qrx.oss-cn-beijing.aliyuncs.com/img/header.jpg" alt="100"></div>
      <div class="panel-body">
        <form method="post">
          <footer id="footer">
            <ul class="copyright"></ul>
            <h2 class="h2-text"><span><a class="a-class"  title="这是很Cooooool的博客啦" href="" target="_blank">cheifsteam</a></span></h2>
            <h2 class="h2-text"><br><div style="font-size: 20px">
              life is a fucking movie
            </div></h2>
            <br>
            <p id="" >

            </p>
          </footer>
          <ul class="icons" v-for="(o,index) in items">
            <li >
              <span class="link" @click="hrefClick(o.link)">
              <span :title="o.message">
                   <i id="icon" :class=o.icon ></i>
            </span>
            </span>
            </li>
          </ul>
          <div style="font-weight: 300" align="center">©2022
            <a class="a-class"  title="这是很Cooooool的博客啦" href="" target="_blank">cheifsteam</a>
            <a class="a-class"  href="https://beian.miit.gov.cn" target="_blank">粤ICP备2022014462号</a>
          </div>
        </form>
      </div>
    </div>

    </div>
</template>
<script>
import {URL} from '../api/config'
export default {
  name: 'my-Info',
  data () {
    return {
      items: [
        {
          icon: 'el-icon-s-home',
          message: '首页',
          link: URL+'/home',
        },
        {
          icon: 'el-icon-reading',
          message: '文章',
          link:URL+'/home'
        },
        {
          icon: 'el-icon-collection-tag',
          message: '分类',
          link: URL+'/blogIndex'
        },
        {
          icon: 'el-icon-position',
          message: 'github',
          link: 'https://github.com/cheifsteam'
        },
        {
          icon: 'el-icon-star-off',
          message: '友链',
          link:URL+'/blogLink'
        }
      ]
    }
  },
  methods : {
    hrefClick(url){
      console.log(url)
      window.location.href=url;//当前标签页
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/myInfo.scss';
@import '../assets/css/blogDeatil.scss';
</style>
