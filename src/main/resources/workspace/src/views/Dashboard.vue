<template>
  <v-container>
    <v-card-title>基本信息</v-card-title>
    <v-row>
      <v-col
        lg="3"
        md="6"
        v-for="info in infoCards"
        :key="info.title"
      >
        <InfoCard
          :color=info.color
          :icon=info.icon
          :title=info.title
          :to="info.to"
          :subTitle=infoValues[info.value]
        ></InfoCard>
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="12" md="6" sm="12"><CommentList /></v-col>
      <v-col cols="12" md="6" sm="12"><LogList /></v-col>
    </v-row>
  </v-container>
</template>

<script>
  import InfoCard from '@/components/InfoCard'
  import CommentList from '@/components/CommentList'
  import LogList from '@/components/LogList'

  import {statistic} from '@/api/admin'

  export default {
  components: {
    InfoCard,
    CommentList,
    LogList
  },
  mounted() {
    this.fetch()
  },
  data: () => ({
    infoCards: [
      { color: 'blue lighten-3', icon: 'person', title: '访客', value: 'visitor' },
      { color: 'deep-purple lighten-3', icon: 'forum', title: '评论', value: 'comment', to: '/admin/comment' },
      { color: 'green lighten-1', icon: 'description', title: '文章', value: 'article', to: '/admin/article' },
      { color: 'indigo lighten-3', icon: 'image', title: '资源', value: 'resource', to: '/admin/resource' }
    ],
    infoValues: []
  }),
  methods: {
    fetch() {
      statistic().then(resp => {
        this.infoValues = resp.data
      })
    }
  }
}
</script>