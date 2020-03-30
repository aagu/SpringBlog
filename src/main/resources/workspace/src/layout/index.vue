<template>
  <v-app id="inspire">
    <Drawer v-bind:drawer=drawer />

    <Toolbar v-bind:drawer=drawer />

    <v-content
      style="background-color: #fafafa"
    >
      <v-container
        fluid
      >
        <router-view></router-view>
      </v-container>
    </v-content>

    <BackToTop :visibilityHeight="100" />
  </v-app>
</template>

<script>
  import Drawer from './Drawer'
  import BackToTop from '@/components/BackToTop'
  import Toolbar from './Toolbar'

  export default {
    name: 'Layout',
    components: { Drawer, BackToTop, Toolbar },
    props: {
      source: String,
    },
    computed: {
      title() {
        return this.$store.state.appBarTitle
      }
    },
    data: () => ({
      drawer: true,
    }),
  }
</script>