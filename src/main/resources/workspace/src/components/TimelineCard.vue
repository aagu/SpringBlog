<template>
  <v-card v-resize="onResize">
    <v-card-text class="subtitle-1 font-weight-bold">Timeline</v-card-text>
    <v-divider></v-divider>
    <div class="content_box">
      <v-timeline
        :dense="window.x <= 600"
      >
        <v-timeline-item
          v-for="(item, index) in activities"
          :key="index"
          color="green lighten-2"
          small
        >
          <template v-slot:opposite>
            <span>{{ item.time }}</span>
          </template>
          <v-card class="elevation-2">
            <v-card-title class="headline">{{ item.title }}</v-card-title>
            <v-card-text>{{ item.text }}</v-card-text>
            <v-card-subtitle class="hidden-md-and-up">{{ item.time }}</v-card-subtitle>
          </v-card>
        </v-timeline-item>
      </v-timeline>
    </div>
  </v-card>
</template>

<script>
  export default {
    name: "TimelineCard",
    data: () => ({
      window: {
        x: 0,
        y: 0
      },
      activities: [
        {
          time: '2020-03-31',
          title: 'feature add',
          text: 'add timeline card'
        },
        {
          time: '2020-03-30',
          title: 'feature add',
          text: 'add profile page'
        },
        {
          time: '2020-03-29',
          title: 'bug fix',
          text: 'adaptive display for mobile platform'
        }
      ]
    }),
    mounted() {
      this.onResize()
    },
    methods: {
      onResize() {
        this.window = { x: window.innerWidth, y: window.innerHeight }
      }
    }
  }
</script>

<style scoped>
  .content_box {
    padding: 12px 20px;
  }
</style>