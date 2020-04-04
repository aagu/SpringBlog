<template>
  <v-container>
    <v-toolbar class="elevation-0 transparent media-toolbar">
      <v-btn outlined  color="primary" @click="showUpload = true">
        <v-icon color="primary">cloud_upload</v-icon>
        &nbsp;Upload
      </v-btn>
      <v-spacer></v-spacer>
      <v-btn-toggle v-model="view">
        <v-btn depressed value="list">
          <v-icon color="primary">view_headline</v-icon>
        </v-btn>
        <v-btn depressed value="grid">
          <v-icon color="primary">view_list</v-icon>
        </v-btn>
      </v-btn-toggle>

      <v-progress-linear
        :active="loading"
        :indeterminate="loading"
        absolute
        bottom
        color="primary"
      ></v-progress-linear>
    </v-toolbar>
    <div id="media" class="media">
      <v-divider></v-divider>
      <div class="layout row media-layout">
        <div class="media-content flex transparent">
          <v-container fluid v-if="view === 'grid'">
            <v-row dense>
              <v-col md="4" sm="12" class="pa-2" v-for="(item, index) in files" :key="index">
                <v-card>
                  <v-img :src="`http://img.aagu.ltd/${item.key}`" height="180px" v-if="isImage(item)"></v-img>
                  <v-icon size="180" v-else>insert_drive_file</v-icon>
                  <v-divider></v-divider>
                  <v-card-title>
                    {{ item.key }}
                  </v-card-title>
                </v-card>
              </v-col>
            </v-row>
          </v-container>
          <v-layout column v-else>
            <v-list dense class="transparent">
              <v-list-item v-for="(item, index) in files" :key="'list-file-' + index">
                <v-list-item-avatar>
                  <v-icon v-text="mimeIcons(item)"></v-icon>
                </v-list-item-avatar>
                <v-list-item-content>
                  <div class="d-flex">
                    <div class="flex">{{ item.key }}</div>
                    <v-spacer></v-spacer>
                    <div class="caption">
                      {{ Math.ceil(item.putTime / 10000) | parseTime }}
                    </div>
                  </div>
                </v-list-item-content>
              </v-list-item>
            </v-list>
          </v-layout>
        </div>
      </div>
    </div>

    <v-dialog
      v-model="showUpload"
      max-width="600px"
    >
      <v-card>
        <v-card-title>Upload Files</v-card-title>
        <v-card-text>
          <v-file-input
            v-model="uploadFiles"
            color="deep-purple accent-4"
            counter
            label="File input"
            multiple
            placeholder="Select your files"
            prepend-icon="mdi-paperclip"
            outlined
            :show-size="10"
            :success="success"
            success-messages="上传成功"
            :error="error"
            error-messages="上传失败"
          >
            <template v-slot:selection="{ index, text }">
              <v-chip
                v-if="index < 2"
                dark
                label
                small
              >
                {{ text }}
              </v-chip>

              <span
                v-else-if="index === 2"
                class="overline grey--text text--darken-3 mx-2"
                >
              +{{ uploadFiles.length - 2 }} File(s)
              </span>
            </template>
          </v-file-input>
          <v-row align-content="center" justify="center">
            <v-col cols="11" align-self="center">
              <v-progress-linear :value="uploadPercent" color="green"></v-progress-linear>
            </v-col>
            <v-col cols="1">
              <v-fab-transition>
                <v-icon color="green" v-if="uploadPercent === 100">check_circle</v-icon>
              </v-fab-transition>
            </v-col>
          </v-row>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="showUpload = false">Close</v-btn>
          <v-btn color="blue darken-1" text @click="upload">Upload</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-container>
</template>

<script>
  import { getList, upload } from "@/api/resource";

  export default {
    name: "Resource",
    data: () => ({
      files: [],
      uploadFiles: [],
      uploadPercent: 0,
      view: 'list',
      imageMime: ["image/jpeg", "image/png", "image/svg+xml"],
      showUpload: false,
      success: false,
      error: false,
      loading: false
    }),
    mounted() {
      this.fetch()
    },
    methods: {
      fetch() {
        this.loading = true
        getList().then(resp => {
          this.files = resp.data.data.items
          this.loading = false
        })
      },
      isImage(file) {
        return /image\/\w+/.test(file.mimeType)
      },
      mimeIcons(file) {
        return this.imageMime.includes(file.mimeType) ? "image" : "insert_drive_file"
      },
      upload() {
        if (this.uploadFiles.length > 0) {
          const outer = this
          let next = function(resp) {
            outer.uploadPercent = resp.total.percent
          }

          let error = function(resp) {
            outer.error = true
          }

          let complete = function(resp) {
            outer.success = true
          }
          upload(this.uploadFiles[0], next, error, complete)
        }
      }
    }
  }
</script>

<style scoped>

</style>