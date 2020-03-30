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
          path: '/category/:label(\\d+)',
          name: 'category',
          component: () => import('@/views/Home')
        },
        {
          path: '/archive/:label(\\d{4}-\\d{2})',
          name: 'archive',
          component: () => import('@/views/Home')
        }
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
          component: () => import('@/views/Dashboard')
        },
      ]
    },
    {
      path: '/login',
      name: 'login',
      meta: {
        title: '登录'
      },
      component: () => import('@/views/Login')
    }
  ]
})
