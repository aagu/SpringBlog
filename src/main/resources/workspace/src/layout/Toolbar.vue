<template>
  <v-app-bar
    app
    dark
    color="primary"
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
    <v-btn icon @click="toggleDark">
      <v-icon>brightness_medium</v-icon>
    </v-btn>
  </v-app-bar>
</template>

<script>
  import SearchBox from '@/components/Search'
  import ThemeOp from "@/mixins/ThemeOp";

  export default {
    name: 'Toolbar',
    components: { SearchBox },
    mixins: [ThemeOp],
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