<template>
  <div id="edit">
    <v-container
      fluid
    >
      <v-progress-linear
        :active="loading"
        indeterminate
      ></v-progress-linear>
      <v-form
        lazy-validation
      >
        <v-row>
          <v-col md="6" sm="12">
            <v-text-field
              v-model="article.title"
              outlined
              label="标题"
              requried
            ></v-text-field>
          </v-col>
          <v-col md="3" sm="6">
            <v-select
              outlined
              :items="labels"
              v-model="article.labelId"
              item-text="name"
              item-value="id"
              menu-props="auto, overflowY"
              label="标签"
            >
            </v-select>
          </v-col>
          <v-col>
            <v-select
              outlined
              :items="status"
              v-model="article.status"
              label="状态"
            ></v-select>
          </v-col>
        </v-row>

        <div id="editor" v-resize="onResize">
          <mavon-editor
            v-model="article.content"
            v-bind:style="{ height: editorHeight, zIndex: 1}"
            :boxShadow="Boolean(false)"
            :subfield="subField"
          />
        </div>

        <v-row>
          <v-col cols="3" md="3" sm="6">
            <v-list-item>
              <v-list-item-action><v-icon>access_time</v-icon></v-list-item-action>
              <v-list-item-content>
                <v-list-item-subtitle>上次修改时间</v-list-item-subtitle>
                <v-list-item-title>{{ article.date | parseTime }}</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-col>

          <v-col cols="2">
            <v-switch
              v-model="comment"
              label="评论"
              :messages="commentState"
            ></v-switch>
          </v-col>
        </v-row>
      </v-form>
    </v-container>

    <v-speed-dial
      v-model="fab"
      bottom
      right
      direction="top"
      transition="slide-y-reverse-transition"
    >
      <template v-slot:activator>
        <v-btn
          v-model="fab"
          color="blue darken-2"
          dark
          fab
        >
          <v-icon v-if="fab">close</v-icon>
          <v-icon v-else>keyboard_arrow_up</v-icon>
        </v-btn>
      </template>

      <v-tooltip left>
        <template v-slot:activator="{ on }">
          <v-btn
            fab
            dark
            small
            color="green"
            v-on="on"
            @click="handleSave"
          >
            <v-icon>save</v-icon>
          </v-btn>
        </template>
        <span>保存文章</span>
      </v-tooltip>
      <v-tooltip left>
        <template v-slot:activator="{ on }">
          <v-btn
            fab
            dark
            small
            color="indigo"
            v-on="on"
            @click="handlePublish"
          >
            <v-icon>send</v-icon>
          </v-btn>
        </template>
        <span>发布文章</span>
      </v-tooltip>
      <v-tooltip left>
        <template v-slot:activator="{ on }">
          <v-btn
            fab
            dark
            small
            color="red"
            v-on="on"
            @click="handleDelete"
          >
            <v-icon>delete</v-icon>
          </v-btn>
        </template>
        <span>删除文章</span>
      </v-tooltip>
    </v-speed-dial>
  </div>
</template>

<script>
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import Notification from '@/components/Notification/Notification'
import { getArticleDetail, createArticle, updateArticle, publishArticle, deleteArticle } from '@/api/article'
import { getLabels } from '@/api/label'

export default {
  name: 'Editor',
  components: {
    mavonEditor
  },
  mounted() {
    this.setArticle(this.$route.params.articleId)
    this.setLabel()
    this.onResize()
  },
  computed: {
    commentState() {
      return this.comment ? '评论已开启' : '评论已关闭'
    },
    subField() {
      return this.windowSize.x > 600
    },
    editorHeight() {
      return Math.floor(this.windowSize.y * 0.6) + 'px'
    }
  },
  data: () => {
    return {
      labels: [],
      status: [
        {
          text: '已发布',
          value: 'published'
        },
        {
          text: '草稿',
          value: 'draft'
        },
        {
          text: '已删除',
          value: 'deleted'
        }
      ],
      fab: false,
      comment: true,
      article: {
        title: '',
        content: '',
        date: null,
        labelId: 1,
        status: 'draft'
      },
      windowSize: {
        x: 0,
        y: 0,
      },
      loading: false,
    }
  },
  methods: {
    handleSave() {
      this.article.date = Number(Date());
      if (this.article.id) {
        updateArticle(this.article).then(() => {
          Notification.notify({color:'success', text: '修改成功！'})
        })
      } else {
        createArticle(this.article).then(() => {
          Notification.notify({color:'success', text: '保存成功！'})
        })
      }
    },
    handlePublish() {

    },
    handleDelete() {
      if (this.article.id) {
        deleteArticle(this.id).then(() => {
          Notification.notify({color:'success', text: '删除成功！'})
        })
      }
    },
    setArticle(id) {
      if (id) {
        this.loading = true
        getArticleDetail(id).then(resp => {
          this.article = resp.data.data
          this.loading = false
        })
      }
    },
    setLabel() {
      getLabels().then(resp => {
        this.labels = resp.data.data.items
      })
    },
    onResize() {
      this.windowSize = { x: window.innerWidth, y: window.innerHeight }
    }
  }
}
</script>

<style scoped>
  #edit .v-speed-dial {
    position: absolute;
  }

  #edit .v-btn--floating {
    position: relative;
  }

  #editor {
    width: 100%;
    height: 100%;
  }
</style>