<template>
  <v-col>
    <v-data-table
      :loading="loading"
      :headers="headers"
      :items="comments"
      :items-per-page.sync="pageQuery.limit"
      :page.sync="pageQuery.page"
      :server-items-length="total"
      class="elevation-1"
    >
      <template v-slot:item.status="{ item }">
        <v-chip :color="item.status | stautsColor" dark>{{ item.status | statusText }}</v-chip>
      </template>
      <template v-slot:item.actions="{ item }">
        <v-icon
          small
          class="mr-2"
          @click="readComment(item)"
        >
          check
        </v-icon>
        <v-icon
          small
          @click="deleteComment(item)"
        >
          delete
        </v-icon>
      </template>
    </v-data-table>
  </v-col>
</template>

<script>
  import { getCommentList, markAsRead, markAsDelete } from '@/api/comment'
  import Notification from "@/components/Notification/Notification";

  export default {
    data () {
      return {
        headers: [
          {
            text: '编号',
            align: 'start',
            sortable: false,
            value: 'id',
          },
          {
            text: '内容',
            sortable: false,
            value: 'detail'
          },
          {
            text: '所属文章',
            value: 'articleTitle'
          },
          {
            text: '状态',
            align: 'center',
            value: 'status'
          },
          { text: '日期', value: 'date' },
          { text: '作者', value: 'email' },
          {
            text: '操作',
            sortable: false,
            value: 'actions'
          },
        ],
        comments: [],
        pageQuery: {
          page: 1,
          limit: 10,
          visibility: 'ALL'
        },
        loading: false,
        total: 0
      }
    },
    filters: {
      stautsColor: function(status) {
        if (status === 'read') return 'light-blue lighten-3'
        else if (status === 'deleted') return 'red lighten-1'
        else if (status === 'unread') return 'light-green lighten-1'
        else return 'grey'
      },
      statusText: function(status) {
        if (status === 'read') return '已读'
        else if (status === 'deleted') return '已删除'
        else if (status === 'unread') return '未读'
        else return '未知'
      }
    },
    mounted() {
      this.fetch()
    },
    watch: {
      pageQuery: {
        handler: function() {
          this.fetch()
        },
        deep: true
      }
    },
    methods: {
      fetch() {
        this.loading = true
        getCommentList(this.pageQuery).then(resp => {
          this.comments = resp.data.data.items
          this.total = resp.data.data.total
          this.loading = false
        })
      },
      readComment(comment) {
        markAsRead(comment.id).then(resp => {
          if (resp.data.code === 20000) {
            comment.status = 'read'
            Notification.success()
          }
        })
      },
      deleteComment(comment) {
        markAsDelete(comment.id).then(resp => {
          if (resp.data.code === 20000) {
            comment.status = 'deleted'
            Notification.success()
          } else {
            Notification.error('action failed')
          }
        })
      }
    }
  }
</script>