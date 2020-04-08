<template>
  <div>
    <v-row>
      <v-col
        md="10"
        offset-md="1"
        sm="12"
      >
        <v-card
          :loading="loading"
        >
          <v-img
            class="white--text"
            height="200px"
            :src=randomPic.img
          >
            <v-card-title
              :class="{ 'white--text': randomPic.color === 'white', 'grey--text text--darken-4': randomPic.color === 'black' }"
              class="align-end fill-height display-1"
            >{{article.title}}</v-card-title>
          </v-img>

          <v-card-actions>
            <v-list-item class="grow" justify-start>
              <v-list-item-avatar color="grey darken-3">
                <v-img
                  class="elevation-6"
                  src="../assets/avataaars.png"
                ></v-img>
              </v-list-item-avatar>

              <v-list-item-content>
                <v-list-item-title>{{article.author}}</v-list-item-title>
                <v-list-item-subtitle>{{ article.date | parseTime('{y}-{m}-{d} {h}:{i}') }}</v-list-item-subtitle>
              </v-list-item-content>
              <v-spacer></v-spacer>
              <v-chip style="marginRight: 5px;">{{ label }}</v-chip>
              <v-icon>bookmark</v-icon>
              <v-icon>share</v-icon>
            </v-list-item>
          </v-card-actions>

           <Markdown class="text-wrap" style="padding: 16px;" :source="article.content"></Markdown>
<!--          <HtmlPanel :html="article.content" style="overflow-x: scroll"></HtmlPanel>-->
        </v-card>
      </v-col>
    </v-row>

    <v-row>
      <v-col
        offset-md="1"
        cols="2"
      >
        <div class="text-center" v-if="prev > 0">
          <v-btn fab small text @click="getPre">
            <v-icon>arrow_back</v-icon>
          </v-btn>
          <div class="d-none d-sm-block">上一篇</div>
        </div>
      </v-col>
      <v-col md="6" sm="8"></v-col>
      <v-col
        cols="2"
      >
        <div class="text-center" v-if="next > 0 && next !== article.id">
          <v-btn fab small text @click="getNext">
            <v-icon>arrow_forward</v-icon>
          </v-btn
          ><div class="d-none d-sm-block">下一篇</div>
        </div>
      </v-col>
    </v-row>

    <v-row>
      <v-col
        md="10"
        offset-md="1"
        sm="12"
      >
        <Comment
          :comments="comments"
          :articleId="article.id"
          :currentPage="commentPagination.current"
          :totalPage="commentPagination.total"
          v-on:addComment="addComment"
          v-on:next="nextPageComment"
          v-on:prev="prevPageComment"
        />
      </v-col>
    </v-row>
  </div>
</template>

<script>
  import Markdown from '@/components/Markdown'
  // import 'markdown-it-vue/dist/markdown-it-vue.css'
  import Comment from '@/components/CommentPanel'
  import {getArticle} from '@/api/article'
  import {getArticleComment} from "@/api/comment";
  import allBgImages from 'randomBg'
  import getPageTitle from "@/utils/get-page-title";

  export default {
    name: 'ArticleDetail',
    components: {
      Comment,
      Markdown
    },
    data: () => ({
      loading: false,
      actions: true,
      article: {
        id: -1,
        content: '',
        author: '',
        date: '',
        url: ''
      },
      label: '',
      prev: -1,
      next: -1,
      comments: {},
      commentPagination: {
        current: 1,
        total: 1
      },
      allBgImages
    }),
    computed: {
      randomPic() {
        const randIndex = Math.floor(Math.random() * this.allBgImages.length)
        return this.allBgImages[randIndex]
      }
    },
    mounted() {
      this.fetchData()
    },
    watch: {
      '$route': 'fetchData'
    },
    methods: {
      getPre() {
        this.$router.push('/detail/'+this.prev)
      },
      getNext() {
        this.$router.push('/detail/'+this.next)
      },
      fetchData() {
        const id = this.$route.params && this.$route.params.id
        this.loading = true
        getArticle(id).then(response => {
          this.article = response.data.article
          this.prev = response.data.prev
          this.next = response.data.next
          this.label = response.data.label.name
          this.loading = false
          this.$store.dispatch('setToolbar', this.article.title)
          document.title = getPageTitle(this.article.title)
        })
        getArticleComment({ articleId: id, page: 1, limit: 5}).then(resp => {
          this.comments = resp.data.comments
          this.commentPagination.current = resp.data.currePage
          this.commentPagination.total = resp.data.totalPage
        })
      },
      addComment(comment) {
        this.comments.push(comment)
      },
      nextPageComment() {
        getArticleComment({
          articleId: this.article.id,
          page: this.commentPagination.current + 1,
          limit: 5
        }).then(resp => {
          this.comments = resp.data.comments
          this.commentPagination.current = resp.data.currePage
          this.commentPagination.total = resp.data.totalPage
        })
      },
      prevPageComment() {
        getArticleComment({
          articleId: this.article.id,
          page: this.commentPagination.current - 1,
          limit: 5
        }).then(resp => {
          this.comments = resp.data.comments
          this.commentPagination.current = resp.data.currePage
          this.commentPagination.total = resp.data.totalPage
        })
      }
    }
  }
</script>

<style>
  .v-application code {
    box-shadow: none;
    background-color: var(--v-background-base);
  }
</style>