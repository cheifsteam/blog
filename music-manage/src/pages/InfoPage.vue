<template>
  <div>
    <div class="card-hear">
    <div  ><h3 class="card-title">系统首页</h3></div>
      <div style=" box-sizing: border-box " ></div>
    </div>
    <div style="margin-top: 40px;border-top:0px;">
    <el-row :gutter="20" class="mgb20">
      <el-col :span="6">
        <el-card shadow="hover" :body-style="{padding: '0px'}">
          <div class="grid-content grid-con-4 bg-edit">
            <div class="grid-cont-right">
              <div class="grid-num">新建博客</div>
              <div><p style="font-size: 25px">记录你的生活</p></div>
              <div class="icon"><i class="el-icon-edit"></i></div>
              <div class="small-box"><router-link class="small-box " to="/song">More info </router-link>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" :body-style="{padding: '0px'}">
          <div class="grid-content grid-con-1 bg-blog">
            <div class="grid-cont-right ">
              <div class="grid-num" >{{blogCount}}</div>
              <div ><p>博客总数</p>
              </div>
              <div class="icon"><i class="el-icon-reading" ></i></div>
              <div class="small-box"><router-link class="small-box " to="/song">More info </router-link>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" :body-style="{padding: '0px'}">
          <div class="grid-content grid-con-2 bg-category" >
            <div class="grid-cont-right">
              <div class="grid-num">{{categoryCount}}</div>
              <div>分类总数</div>
              <div class="icon"><i class="el-icon-collection-tag"></i> </div>
              <div class="small-box"><router-link class="small-box " to="/song">More info </router-link>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20" class="mgb20">
      <el-col :span="6">
        <el-card shadow="hover" :body-style="{padding: '0px'}">
          <div class="grid-content grid-con-3 bg-tag">
            <div class="grid-cont-right">
              <div class="grid-num">{{tagCount}}</div>
              <div>标签数量</div>
              <div class="icon"><svg xmlns="http://www.w3.org/2000/svg" width="90" height="90" fill="currentColor" class="bi bi-tags" viewBox="0 0 16 16">
                <path d="M3 2v4.586l7 7L14.586 9l-7-7H3zM2 2a1 1 0 0 1 1-1h4.586a1 1 0 0 1 .707.293l7 7a1 1 0 0 1 0 1.414l-4.586 4.586a1 1 0 0 1-1.414 0l-7-7A1 1 0 0 1 2 6.586V2z"/>
                <path d="M5.5 5a.5.5 0 1 1 0-1 .5.5 0 0 1 0 1zm0 1a1.5 1.5 0 1 0 0-3 1.5 1.5 0 0 0 0 3zM1 7.086a1 1 0 0 0 .293.707L8.75 15.25l-.043.043a1 1 0 0 1-1.414 0l-7-7A1 1 0 0 1 0 7.586V3a1 1 0 0 1 1-1v5.086z"/>
              </svg></div>
              <div class="small-box"><router-link class="small-box " to="/song">More info </router-link>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" :body-style="{padding: '0px'}">
          <div class="grid-content grid-con-4 bg-link">
            <div class="grid-cont-right">
              <div class="grid-num">{{linkCount}}</div>
              <div>友链数量</div>
              <div class="icon"><svg xmlns="http://www.w3.org/2000/svg" width="90" height="90" fill="currentColor" class="bi bi-heart-fill" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314z"/>
              </svg></div>
              <div class="small-box"><router-link class="small-box " to="/song">More info </router-link>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
  </div>
</template>

<script>
import { mixin } from '../mixins'
import { HttpManager } from '../api/index'

export default {
  mixins: [mixin],
  data () {
    return {
      blogCount: 0,
      categoryCount: 0,
      tagCount: 0,
      linkCount: 0
    }
  },
  mounted () {
    this.getBlog()
    this.getAllTags()
    this.getCategory()
    this.getAllLinks()
  },
  methods: {
    getBlog () {
      HttpManager.getAllBlogs().then(res => {
        this.blogCount = res.data.total
      })
    },
    setSex (sex, arr) {
      let count = 0
      for (let item of arr) {
        if (sex === item.sex) {
          count++
        }
      }
      return count
    },
    getCountry (val) {
      for (let item of this.country.rows) {
        if (val.includes(item['国家'])) {
          item['总数']++
          break
        }
      }
    },
    getStyle (val) {
      for (let item of this.songStyle.rows) {
        if (val.includes(item['分格'])) {
          item['总数']++
        }
      }
    },
    getAllTags () {
      HttpManager.getAllTags().then(res => {
        this.tagCount = res.data.total
      }).catch(err => {
        console.log(err)
      })
    },
    getCategory () {
      HttpManager.getAllCategory().then(res => {
        this.categoryCount = res.data.length
      }).catch(err => {
        console.log(err)
      })
    },
    getAllLinks () {
      HttpManager.getAllLinks().then(res => {
        this.linkCount = res.data.total
        for (let item of res) {
          this.getStyle(item.style)
        }
      }).catch(err => {
        console.log(err)
      })
    }
  }
}
</script>

<style>
  .grid-content {
    display: flex;
    align-items: center;
    height: 150px;
   position:relative
  }

  .grid-cont-right {
    flex: 1;
    font-size: 30px;
    color: #fff!important;
    position:absolute;
    bottom:0;

  }
  .bg-blog {
    background-color: #FD8A61 !important;
  }
  .bg-category{
    background-color: #FEED78 !important ;
  }
  .bg-tag{
    background-color: #FFC0CB !important ;
  }
  .bg-link{
    background-color: #87CEFA !important;
  }
  .bg-edit{
    background-color: #17a2b8 !important;
  }
  .grid-num {
    font-size: 35px;
    font-weight: bold;
  }
.small-box{
  text-align: center;
  width: 360px;
  font-size: 20px;
  color: #fff;
  color: rgba(255,255,255,.8);
  display: block;
  z-index: 10;
  background: rgba(0,0,0,.1);
  text-decoration: none;
}
.icon{
  transition: all .3s linear;
  position: absolute;
  top: -10px;
  right: 10px;
  z-index: 0;
  font-size: 90px;
  color: rgba(0,0,0,.15);
  height: 50%;
}
.card-title{
  font-size: 1.25rem;
  font-weight: 400;
  height: 40px;
  margin: 0;

}
.card-hear{
  position: relative;
  background-color: transparent;
  border-bottom: 1px solid rgba(0,0,0,.125);
  border-top-left-radius: .25rem;
  border-top-right-radius: .25rem;
}
  .cav-info {
    border-radius: 6px;
    overflow: hidden;
  }
</style>
