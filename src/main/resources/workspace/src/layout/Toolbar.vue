<template>
  <v-app-bar
    app
    color="primary"
    dark
    hide-on-scroll
    elevation="0"
  >
    <v-app-bar-nav-icon 
      @click.stop="toggleDrawer"
    >
    </v-app-bar-nav-icon>
    <v-toolbar-title>{{title}}</v-toolbar-title>
    <v-spacer></v-spacer>
    <SearchBox />
  </v-app-bar>
</template>

<script>
  import SearchBox from '@/components/Search'

  export default {
    name: 'Toolbar',
    components: { SearchBox },
    computed: {
      title() {
        return this.$store.state.appBarTitle
      }
    },
    data: () => ({
      screenSize: 0,
    }),
    methods: {
      toggleDrawer() {
        this.$store.dispatch('toggleDrawer')
      }
    }
  }
</script>