<template>
  <v-container>
    <div>
      <v-row v-if="isHomepage" justify="center">
        <v-col
          md="4"
        >
          <GreetingCard />
        </v-col>
        <v-col
          md="4"
        >
          <v-date-picker
            full-width
            no-title
            readonly
            v-model="date"
            :first-day-of-week="1"
            locale="zh-cn"
          ></v-date-picker>
        </v-col>
      </v-row>
      <div>
        <div v-for="article in articles" :key="article.date">
          <ArticleCard
          :title=article.title
          :content=article.content
          :author=article.author
          :date=article.date
          :url=article.url
          :label=label(article.labelId) />
        </div>
      </div>
    </div>

    <v-row class="d-flex justify-center align-center">
      <v-col
        cols="2"
      >
        <div v-if="prePage > 0" class="text-center">
          <v-btn fab small text @click="goPrePage"><v-icon>arrow_back</v-icon></v-btn><div class="d-none d-sm-block">上一页</div>
        </div>
      </v-col>
      <v-col
        cols="6"
      >
        <p class="text-center">第 {{ query['page'] }} 页，共 {{ pages }} 页</p>
      </v-col>
      <v-col
        cols="2"
      >
        <div v-if="nextPage > 0 && nextPage <= pages" class="text-center">
          <v-btn fab small text @click="goNextPage"><v-icon>arrow_forward</v-icon></v-btn><div class="d-none d-sm-block">下一页</div>
        </div>
      </v-col>
    </v-row>

    <v-overlay :value="loading">
      <v-progress-circular indeterminate size="64"></v-progress-circular>
    </v-overlay>
  </v-container>
</template>

<script>
  import GreetingCard from '@/components/GreetingCard'
  import ArticleCard from '@/components/ArticleCard'
  import {getArticle} from '@/api/home'
  import {getLabelById} from '@/utils'

  export default {
    name: 'Home',
    components: { GreetingCard, ArticleCard },
    data: () => ({
      articles: [],
      labels: [],
      query: {
        page: 1,
        limit: 6,
        label: null,
        archive: null
      },
      page: 1,
      pages: 2,
      isHomepage: true,
      date: new Date().toISOString().substr(0, 10),
      loading: false
    }),
    mounted() {
      this.getPage()
    },
    computed: {
      nextPage() {
        return this.query['page'] + 1
      },
      prePage() {
        return this.query['page'] - 1
      },
      label() {
        return id => {
          return getLabelById(this.labels, id)
        }
      }
    },
    watch: {
      $route(to, from) {
        if (to.name === 'home') {
          const query = this.$route.query

          if (query.category) {
            this.query['label'] = query.category
          } else if (query.archive) {
            this.query['archive'] = query.archive
          }

          this.getPage()
        }
      }
    },
    methods: {
      getPage() {
        this.loading = true
        getArticle(this.query).then(response => {
          this.articles = response.data.articles
          this.page = response.data.currePage
          this.pages = response.data.pages
          this.labels = response.data.labels
          if (this.query['label'] === null && this.query['archive'] === null) {
            this.$store.dispatch('setLabelMap', this.labels)
          }

          this.loading = false

          // clear query
          this.query['label'] = null
          this.query['archive'] = null
        })
      },
      goNextPage() {
        this.query["page"] = this.nextPage
        this.getPage()
      },
      goPrePage() {
        this.query["page"] = this.prePage
        this.getPage()
      }
    }
  }
</script>
