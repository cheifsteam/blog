import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const constantRoutes = [
  {
    path: '*',
    redirect: '/404'
  },
  {
    path: '/404',
    component: () => import('@/pages/404.vue')
  },
  {
    path: '/my-Info',
    name: 'my-Info',
    component: () => import('@/pages/MyInfo')
  },
  {
    path: '/',
    name: 'main',
    component: () => import('@/pages/Main'),
    children: [
      {
        path: '/blogInfo',
        name: 'blogInfo',
        component: () => import('@/pages/BlogInfo')
      },
      {
        path: '/blogLink',
        name: 'blogLink',
        component: () => import('@/pages/BlogLink')
      },
      {
        path: '/home',
        name: 'home',
        component: () => import('@/pages/Home')
      },
      {
        path: '/',
        name: 'home',
        component: () => import('@/pages/Home')
      },
      {
        path: '/blogTagInfo',
        name: 'blogTagInfo',
        component: () => import('@/pages/BlogTagInfo')
      },
      {
        path: '/blogIndex',
        name: 'blogIndex',
        component: () => import('@/pages/BlogIndex')
      },
      {
        path: '/blogDetail',
        name: 'blogDetail',
        component: () => import('@/pages/BlogDetail')
      },
      {
        path: '/blogSearch',
        name: 'blogSearch',
        component: () => import('@/pages/BlogSearchInfo')
      }
    ]
  }
]

export default new Router({
  mode: 'history', // 去掉url中的#
  scrollBehavior: (o, from, savedPosition) => ({ x: 0, y: 0 }),
  routes: constantRoutes
})
