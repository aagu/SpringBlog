<template>
  <v-app id="inspire">
    <Drawer></Drawer>
    <Toolbar></Toolbar>
    <v-content
      style="background-color: #fafafa"
    >
      <keep-alive>
        <router-view></router-view>
      </keep-alive>
    </v-content>
  </v-app>
</template>

<script>
import Toolbar from './Toolbar'
import Drawer from './Drawer'

export default {
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