<template>
  <v-navigation-drawer
    v-model="drawer"
    @input="toggleDrawer"
    app
  >
    <v-list-item
      color="blue"
    >
      <v-list-item-content>
        <v-list-item-title class="title">
          aagu blog
        </v-list-item-title>
        <v-list-item-subtitle>
          admin
        </v-list-item-subtitle>
      </v-list-item-content>
    </v-list-item>
    <v-divider></v-divider>

    <v-list-item
      v-for="item in items"
      :key="item.title"
      :to="{name: item.path}"
    >
      <v-list-item-icon>
        <v-icon>{{ item.icon }}</v-icon>
      </v-list-item-icon>

      <v-list-item-content>
        <v-list-item-title>{{ item.title }}</v-list-item-title>
      </v-list-item-content>
    </v-list-item>
  </v-navigation-drawer>
</template>

<script>
export default {
  data: () => ({
    items: [
      { title: '仪表盘', icon: 'dashboard', path: 'dashboard'},
      { title: '写文章', icon: 'edit', path: 'edit'},
      { title: '文章管理', icon: 'view_list', path: 'article'},
      { title: '评论管理', icon: 'comment', path: 'comment'},
      { title: '标签管理', icon: 'label', path: 'label'},
      { title: '资源管理', icon: 'view_compact', path: 'resource'},
      { title: '系统设置', icon: 'settings', path: 'settings'}
    ]
  }),
  computed: {
    drawer: {
      get() {
        return this.$store.state.drawer
      },
      set(val) {
        // this.$store.dispatch('toggleDrawer')
      }
    }
  },
  methods: {
    toggleDrawer(val) {
      const old = this.drawer
      if (val !== old) {
        this.$store.dispatch('toggleDrawer')
      }
    },
  }
}
</script>