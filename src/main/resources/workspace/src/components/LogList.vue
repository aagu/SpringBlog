<template>
  <v-card>
    <v-toolbar flat dense color="transparent">
      <v-toolbar-title>系统日志</v-toolbar-title>
    </v-toolbar>
    <v-divider></v-divider>
    <v-card-text class="pa-0">
      <v-simple-table>
        <template v-slot:default>
          <tbody>
            <tr v-for="item in comments" :key="item.id">
              <td>{{ item.detail }}</td>
              <td>{{ item.email }}</td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
    </v-card-text>
  </v-card>
</template>

<script>
import { getCommentList } from '@/api/comment'

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
    }
  }
}
</script>