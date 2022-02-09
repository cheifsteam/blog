import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const constantRoutes = [
  {
    path: '/Home',
    component: resolve => require(['../components/Home.vue'], resolve),
    meta: { title: '自述文件' },
    children: [
      {
        path: '/Info',
        component: resolve => require(['../pages/InfoPage.vue'], resolve),
        meta: { title: 'Info' }
      },
      {
        path: '/Blog',
        component: resolve => require(['../pages/BlogPage.vue'], resolve),
        meta: { title: 'blog' }
      },
      {
        path: '/Category',
        component: resolve => require(['../pages/CategoryPage.vue'], resolve),
        meta: { title: 'Category' }
      },
      {
        path: '/ListSong',
        component: resolve => require(['../pages/LinkPage.vue'], resolve),
        meta: { title: 'ListSong' }
      },
      {
        path: '/Comment',
        component: resolve => require(['../pages/CommentPage.vue'], resolve),
        meta: { title: 'Comment' }
      },
      {
        path: '/Tag',
        component: resolve => require(['../pages/TagPage.vue'], resolve),
        meta: { title: 'tag' }
      },
      {
        path: '/Link',
        component: resolve => require(['../pages/LinkPage.vue'], resolve),
        meta: { title: 'Link' }
      },
      {
        path: '/EditBlog',
        component: resolve => require(['../pages/EditBlog.vue'], resolve),
        meta: { title: 'EditBlog' }
      }
    ]
  },
  {
    path: '/',
    component: resolve => require(['../pages/Login.vue'], resolve)
  }
]

export default new Router({
  mode: 'history', // 去掉url中的#
  scrollBehavior: (o, from, savedPosition) => ({ x: 0, y: 0 }),
  routes: constantRoutes
})
