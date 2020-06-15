<template>
  <div>
    <v-card
        class="mx-auto"
    >
      <v-card-title class="primary">
        <v-icon dark>new_releases</v-icon>
        <span style="margin-left: 8px; color: #ffffff">公告</span>
      </v-card-title>
      <v-card-text>
        <v-list-item v-if="noticeList.length === 0">
          <v-list-item-content>暂无公告</v-list-item-content>
        </v-list-item>
        <v-list v-else>
          <v-list-item
              v-for="item in noticeList"
              :key="item.id"
              @click="handleView(item)"
          >
            <v-list-item-content>{{ item.title }}</v-list-item-content>
            <v-list-item-icon>{{ item.date | parseTime }}</v-list-item-icon>
          </v-list-item>
        </v-list>
      </v-card-text>
    </v-card>
    <v-dialog v-model="dialog" max-width="320">
      <v-card>
        <v-card-title class="headline">{{ currNotice.title }}</v-card-title>
        <v-card-text class="body-1" style="min-height: 180px">{{ currNotice.detail }}</v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary darken-1" text @click="handleCancel">Got it</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
  import InfoCard from "./InfoCard";
  import { getNoticeList } from "../api/notice";
  import {deepClone} from "../utils";

  export default {
    name: "BulletBoard",
    components: {InfoCard},
    data: () => ({
      noticeList: [],
      currNotice: {
        id: undefined,
        title: '',
        detail: '',
        date: null,
        status: 'published'
      },
      dialog: false
    }),
    mounted() {
      this.fetch()
    },
    methods: {
      fetch() {
        getNoticeList({page: 1, limit: 4, status: 'published'}).then(resp => {
          this.noticeList = resp.data.items
        })
      },
      handleCancel() {
        this.dialog = false
        this.resetCurrNotice()
      },
      handleView(notice) {
        this.currNotice = deepClone(notice)
        this.dialog = true
      }
    }
  }
</script>

<style scoped>

</style>