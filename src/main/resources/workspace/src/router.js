import Vue from 'vue'
import Router from 'vue-router'

import BlogLayout from '@/layout/index'
import AdminLayout from '@/layout-admin/index'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component: BlogLayout,
      redirect: 'home',
      children: [
        {
          path: '/about',
          name: 'about',
          meta: {
            title: 'about'
          },
          component: () => import('@/views/About')
        },
        {
          path: '/',
          name: 'home',
          meta: {
            title: 'aagu\'s blog'
          },
          component: () => import('@/views/Home')
        },
        {
          path: '/detail/:id(\\d+)',
          name: 'detail',
          meta: {
            title: '文章详情'
          },
          component: () => import('@/views/Detail')
        },
        {
          path: '/404',
          name: '404',
          meta: {
            title: '找不到页面'
          },
          component: () => import('@/views/NotFound')
        },
      ]
    },
    {
      path: '/admin',
      component: AdminLayout,
      redirect: { name: 'dashboard'},
      children: [
        {
          path: 'dashboard',
          name: 'dashboard',
          meta: {
            title: '仪表盘'
          },
          component: () => import('@/views/Dashboard')
        },
        {
          path: 'edit',
          name: 'edit',
          meta: {
            title: '写文章'
          },
          component: () => import('@/views/Editor')
        },
        {
          path: 'article',
          name: 'article',
          meta: {
            title: '文章管理'
          },
          component: () => import('@/views/Article')
        },
        {
          path: 'comment',
          name: 'comment',
          meta: {
            title: '评论管理'
          },
          component: () => import('@/views/Comment')
        },
        {
          path: 'label',
          name: 'label',
          meta: {
            title: '标签管理'
          },
          component: () => import('@/views/Label')
        },
        {
          path: 'resource',
          name: 'resource',
          meta: {
            title: '资源管理'
          },
          component: () => import('@/views/Resource')
        },
        {
          path: 'settings',
          name: 'settings',
          meta: {
            title: '系统设置'
          },
          component: () => import('@/views/Settings/Settings')
        },
        {
          path: 'profile',
          name: 'profile',
          meta: {
            title: '个人信息'
          },
          component: () => import('@/views/Profile')
        }
      ]
    },
    {
      path: '/login',
      name: 'login',
      meta: {
        title: '登录'
      },
      component: () => import('@/views/Login')
    },
    {
      path: '*',
      meta: {
        title: '找不到页面'
      },
      component: () => import('@/views/NotFound')
    }
  ],
  scrollBehavior (to, from, savedPosition) {
    return { x: 0, y: 0 }
  }
})
