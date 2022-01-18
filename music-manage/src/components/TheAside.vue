<template>
  <div class="sidebar">
    <el-menu
      class="sidebar-el-menu"
      :default-active="onRoutes"
      :collapse="collapse"
      background-color="#242526"
      text-color="#B0B3B2"
      active-text-color="#ffffff"
      unique-opened
      router
    >
      <template v-for="item in items">
          <template>
            <el-menu-item :index="item.index" :key="item.index">
              <i :class="item.icon"></i>
              <span slot="title">{{ item.title }}</span>
            </el-menu-item>
        </template>
      </template>
    </el-menu>
  </div>
</template>

<script>
import _ctrEvent from '../assets/js/ctr-event'

export default {
  data () {
    return {
      collapse: false,
      items: [
        {
          icon: 'el-icon-s-data',
          index: 'info',
          title: '系统首页'
        },
        {
          icon: 'el-icon-price-tag',
          index: 'tag',
          title: '标签管理'
        },
        {
          icon: 'el-icon-reading',
          index: 'blog',
          title: '博客管理'
        },
        {
          icon: 'el-icon-collection-tag',
          index: 'category',
          title: '分类管理'
        },
        {
          icon: 'el-icon-edit',
          index: 'editBlog',
          title: '发布博客'
        },
        {
          icon: 'el-icon-star-off',
          index: 'link',
          title: '友链管理'
        }
      ]
    }
  },
  computed: {
    onRoutes () {
      return this.$route.path.replace('/', '')
    }
  },
  created () {
    _ctrEvent.$on('collapse', msg => {
      this.collapse = msg
    })
  }
}
</script>

<style scoped>
.sidebar {
  display: block;
  position: absolute;
  background-color: #334256;
  left: 0;
  top: 70px;
  bottom: 0;
  overflow-y: scroll;
}
.sidebar::-webkit-scrollbar {
  width: 0;
}
.sidebar-el-menu:not(.el-menu--collapse) {
  width: 150px;
}
.sidebar > ul {
  height: 100%;
}
</style>
