<template>
  <v-card>
    <v-toolbar flat dense color="transparent">
      <v-toolbar-title>未读评论</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-menu bottom left>
        <template v-slot:activator="{ on }">
          <v-btn icon small v-on="on">
            <v-icon>more_vert</v-icon>
          </v-btn>
        </template>

        <v-list>
          <v-list-item>
            <v-list-item-title>Read All</v-list-item-title>
          </v-list-item>
          <v-list-item>
            <v-list-item-title>Delete All</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-toolbar>
    <v-divider></v-divider>
    <v-card-text class="pa-0">
      <template>
        <v-data-table
          :headers="headers"
          :items="comments"
          hide-default-footer
          class="elevation-0"
        >
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
      </template>
      <v-divider></v-divider>
    </v-card-text>
  </v-card>
</template>

<script>
import { getCommentList, markAsRead, markAsDelete } from '@/api/comment'
import Notification from "./Notification/Notification";

export default {
  data() {
    return {
      headers: [
        {
          text: "内容",
          align: "center",
          sortable: false,
          value: "detail"
        },
        {
          text: "作者",
          align: "center",
          sortable: false,
          value: "email"
        },
        {
          text: "操作",
          value: "actions",
          sortable: false,
          align: "right"
        }
      ],
      comments: []
    }
  },
  mounted() {
    this.fetch()
  },
  methods: {
    fetch() {
      getCommentList({page: 1, limit: 10, status: 'unread'}).then(resp => {
        this.comments = resp.data.data.items
      })
    },
    readComment(comment) {
      markAsRead(comment.id).then(resp => {
        if (resp.data.code === 20000) {
          let idx = this.comments.indexOf(comment);
          this.comments.splice(idx, 1);
          Notification.success()
        } else {
          Notification.error('action failed')
        }
      })
    },
    deleteComment(comment) {
      markAsDelete(comment.id).then(resp => {
        if (resp.data.code === 20000) {
          let idx = this.comments.indexOf(comment);
          this.comments.splice(idx, 1);
          Notification.success()
        } else {
          Notification.error('action failed')
        }
      })
    }
  }
}
</script>