<template>
  <div style="padding: 20px 25px 20px 25px">
    <v-progress-circular indeterminate v-if="loading"/>
    <div v-html="webPage"></div>
  </div>
</template>
<style>

</style>
<script>
  import Vue from 'vue';
  import VueKatex from 'vue-katex'
  import 'katex/dist/katex.min.css';

  Vue.use(VueKatex)

  export default{
    // 使用时请使用 :url.sync=""传值
    name: 'HtmlPanel',
    props: {
      html: {
        required: true
      }
    },
    data () {
      return {
        loading: false,
      }
    },
    filters: {
      parseHtml: (html) => {
        console.log(html)
      }
    },
    computed: {
      webPage() {
        const tmp1 = this.html.replace('<p>$$', '<p v-katex="\'');
        const tmp2 = tmp1.replace('$$</p>', '\'></p>');
        return tmp2
      }
    },
    methods: {
      load (url) {
        if (url && url.length > 0) {
          // 加载中
          this.loading = true
          let param = {
            accept: 'text/html, text/plain'
          }
          this.$http.get(url, param).then((response) => {
            this.loading = false
            // 处理HTML显示
            this.html = response.data
          }).catch(() => {
            this.loading = false
            this.html = '加载失败'
          })
        }
      },
    }
  }
  </script>