<template>
  <v-container>
    <v-data-table
      :loading="loading"
      :headers="headers"
      :items="articleList"
      :items-per-page.sync="pageQuery.limit"
      :page.sync="pageQuery.page"
      :server-items-length="total"
      class="elevation-1"
    >
      <template v-slot:item.date="{ item }">
        {{ item.date | parseTime}}
      </template>
      <template v-slot:item.status="{ item }">
        <v-chip :color="item.status | stautsColor" dark>{{ item.status | statusText }}</v-chip>
      </template>
      <template v-slot:item.actions="{ item }">
        <v-icon
          small
          class="mr-2"
          @click="editArticle(item)"
        >
          mdi-pencil
        </v-icon>
        <v-icon
          small
          class="mr-2"
          @click="publishArticle(item)"
        >
          send
        </v-icon>
        <v-icon
          small
          @click="deleteArticle(item)"
        >
          mdi-delete
        </v-icon>
      </template>
    </v-data-table>
  </v-container>
</template>

<script>
import { getArticleList, deleteArticle, publishArticle } from '@/api/article'
import Notification from "@/components/Notification/Notification";

export default {
  data: () => ({
    headers: [
      { text: '编号', value: 'id' },
      {
        text: '文章标题',
        align: 'start',
        sortable: false,
        value: 'title',
      },
      {
        text: '时间',
        align: 'center',
        value: 'date'
      },
      {
        text: '状态',
        align: 'center',
        value: 'status'
      },
      {
        text: '操作',
        value: 'actions',
        align: 'center',
        sortable: false
      },
    ],
    pageQuery: {
      page: 1,
      limit: 10
    },
    loading: false,
    articleList: [],
    total: 0
  }),
  watch: {
    pageQuery: {
      handler: function() {
        this.fetch()
      },
      deep: true
    }
  },
  mounted() {
    this.fetch()
  },
  filters: {
    stautsColor: function(status) {
      if (status === 'published') return 'green'
      else if (status === 'deleted') return 'red'
      else if (status === 'draft') return 'orange'
      else return 'grey'
    },
    statusText: function(status) {
      if (status === 'published') return '已发布'
      else if (status === 'deleted') return '已删除'
      else if (status === 'draft') return '存为草稿'
      else return '未知'
    }
  },
  methods: {
    fetch() {
      this.loading = true
      getArticleList(this.pageQuery).then(resp => {
        this.articleList = resp.data.data.items
        this.total = resp.data.data.total
        this.loading = false
      })
    },
    editArticle(article) {
      this.$router.push({name: 'edit', params: { articleId: article.id }})
    },
    deleteArticle(article) {
      deleteArticle(article.id).then(resp => {
        if (resp.data.code === 20000) {
          article.status = 'deleted'
        } else {
          Notification.error('action failed')
        }
      })
    },
    publishArticle(article) {
      publishArticle(article.id).then(resp => {
        if (resp.data.code === 20000) {
          article.status = 'published'
        } else {
          Notification.error('action failed')
        }
      })
    }
  }
}
</script>