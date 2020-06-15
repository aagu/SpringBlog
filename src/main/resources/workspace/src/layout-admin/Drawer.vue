<template>
  <v-navigation-drawer
    v-model="drawer"
    app
  >
    <v-list-item>
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

    <template v-slot:append>
      <v-footer>
        <v-row justify="center">
          <v-col>
            <v-btn icon @click="toggleDark">
              <v-icon>brightness_medium</v-icon>
            </v-btn>
          </v-col>
          <v-col>
            <v-btn icon @click="$router.push('/')" >
              <v-icon>home</v-icon>
            </v-btn>
          </v-col>
        </v-row>
      </v-footer>
    </template>
  </v-navigation-drawer>
</template>

<script>
  import ThemeOp from "../mixins/ThemeOp";

  export default {
  mixins: [ThemeOp],
  data: () => ({
    items: [
      { title: '仪表盘', icon: 'dashboard', path: 'dashboard'},
      { title: '写文章', icon: 'edit', path: 'edit'},
      { title: '文章管理', icon: 'view_list', path: 'article'},
      { title: '评论管理', icon: 'comment', path: 'comment'},
      { title: '公告管理', icon: 'notifications', path: 'notice'},
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
        this.$store.dispatch('setDrawer', val)
      }
    }
  },
  methods: {
  }
}
</script>