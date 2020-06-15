<template>
  <v-container>
    <v-data-table
        :loading="loading"
        :headers="headers"
        :items="noticeList"
        :items-per-page.sync="pageQuery.limit"
        :page.sync="pageQuery.page"
        :server-items-length="total"
        class="elevation-1"
    >
      <template v-slot:item.date="{ item }">
        {{ item.date | parseTime}}
      </template>
      <template v-slot:item.status="{ item }">
        <v-chip :color="item.status | statusColor" dark>{{ item.status | statusText }}</v-chip>
      </template>
      <template v-slot:item.actions="{ item }">
        <v-icon
            small
            class="mr-2"
            @click="editNotice(item)"
        >
          mdi-pencil
        </v-icon>
        <v-icon
            small
            class="mr-2"
            @click="viewNotice(item)"
        >
          mdi-eye
        </v-icon>
        <v-icon
            small
            @click="handleDelete(item)"
        >
          mdi-delete
        </v-icon>
      </template>
    </v-data-table>
    <v-dialog v-model="dialog" persistent max-width="400">
      <template v-slot:activator="{ on }">
        <v-btn
            color="primary"
            dark
            absolute
            bottom
            right
            fab
            id="fab"
            v-on="on"
        >
          <v-icon>add</v-icon>
        </v-btn>
      </template>
      <v-card>
        <v-card-title class="headline">{{ dialogTitle }}</v-card-title>
        <v-card-text>
          <v-text-field
              label="Title"
              outlined
              v-model="currNotice.title"
              :disabled="dialogMode === 2"
          ></v-text-field>
          <v-textarea
              label="Content"
              outlined
              v-model="currNotice.detail"
              :disabled="dialogMode === 2"
          ></v-textarea>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary darken-1" text @click="handleCancel">取消</v-btn>
          <v-btn v-if="dialogMode !== 2" color="primary darken-1" text @click="handleSave">发布</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
  import {getNoticeList, createNotice, updateNotice, deleteNotice} from "../api/notice";
  import { deepClone } from "../utils";

  export default {
    name: "Notice",
    data: () => ({
      headers: [
        { text: '编号', value: 'id' },
        {
          text: '标题',
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
      noticeList: [],
      currNotice: {
        id: undefined,
        title: '',
        detail: '',
        date: null,
        status: 'published'
      },
      total: 0,
      dialog: false,
      dialogMode: 0 // 0 for create, 1 for edit, 2 for view
    }),
    computed: {
      dialogTitle: function () {
        if (this.dialogMode === 0) return '新建公告'
        if (this.dialogMode === 1) return '编辑公告'
        return '公告详情'
      }
    },
    mounted() {
      this.fetch()
    },
    filters: {
      statusColor: function(status) {
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
        getNoticeList(this.pageQuery).then(resp => {
          this.noticeList = resp.data.items
          this.total = resp.data.total
          this.loading = false
        })
      },
      editNotice(notice) {
        this.dialogMode = 1
        this.currNotice = deepClone(notice)
        this.dialog = true
      },
      viewNotice(notice) {
        this.dialogMode = 2
        this.currNotice = deepClone(notice)
        this.dialog = true
      },
      handleSave() {
        if (this.currNotice.id === undefined) {
          createNotice(this.currNotice).then(resp => {
            this.resetCurrNotice()
            this.noticeList.unshift(resp.data.data)
            this.dialog = false
          })
        } else {
          updateNotice(this.currNotice).then(resp => {
            let idx = this.noticeList.indexOf(this.currNotice)
            this.noticeList.splice(idx, 0, resp.data.data)
            this.resetCurrNotice()
            this.dialog = false
          })
        }
      },
      handleDelete(data) {
        this.$dialog.confirm({
          title: '删除公告',
          text: `编号为 ${data.id} 的公告将会被删除`,
          actions: {
            No: '取消',
            Yes: {
              color: 'red',
              text: "确认",
              handle: () => {
                deleteNotice(data).then(resp => {
                  let idx = this.noticeList.indexOf(data)
                  if (idx >= 0) {
                    this.noticeList[idx].status = 'deleted'
                  }
                })
              }
            }
          }
        })
      },
      handleCancel() {
        this.dialog = false
        this.resetCurrNotice()
      },
      resetCurrNotice() {
        this.currNotice = {
          id: undefined,
          title: '',
          detail: '',
          date: null,
          status: 'published'
        }
        this.dialogMode = 0
      }
    }
  }
</script>

<style scoped>
  #fab {
    bottom: 20px;
  }
</style>