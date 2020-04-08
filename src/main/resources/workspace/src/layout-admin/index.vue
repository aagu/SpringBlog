<template>
  <v-app id="inspire">
    <Drawer></Drawer>

    <Toolbar></Toolbar>

    <v-content
      class="base"
    >
      <router-view></router-view>
    </v-content>
  </v-app>
</template>

<script>
  import Toolbar from './Toolbar'
  import Drawer from './Drawer'

  export default {
  name: 'AdminLayout',
  components: {
    Toolbar,
    Drawer
  }
  // computed: {
  //   key() {
  //     return this.$route.path
  //   }
  // }
}
</script>

<style scoped>
  .base {
    color: var(--v-background);
  }
</style>