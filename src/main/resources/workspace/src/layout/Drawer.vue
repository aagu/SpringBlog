<template>
  <v-navigation-drawer
    v-model="drawer"
    app
  >
    <v-list
      dense
      class="py-0"
    >
      <v-img src="../assets/daily_pic.png">
        <v-layout pa-3 column fill-height class="lightbox white--text" text-center>
          <v-spacer></v-spacer>
          <v-flex shrink>
            <v-avatar size="80">
              <img
                :src="avatar"
                alt="avatar"
              >
            </v-avatar>
            <div class="headline">{{ name }}</div>
            <v-card-actions class="white--text">
              <span>{{ email }}</span>
              <v-spacer></v-spacer>
              <v-btn
                icon
                @click="show = !show"
              >
                <v-icon class="white--text">{{ show ? 'keyboard_arrow_up' : 'keyboard_arrow_down' }}</v-icon>
              </v-btn>
            </v-card-actions>
            <v-expand-transition>
              <div v-show="show">
                <v-list-item class="emailBtn" href="mailto:aagu@outlook.com">
                  <v-list-item-icon><v-icon>email</v-icon></v-list-item-icon>
                  <v-list-item-title>email me</v-list-item-title>
                </v-list-item>
              </div>
            </v-expand-transition>
          </v-flex>
        </v-layout>
      </v-img>
      <v-divider></v-divider>
      <v-list-item to="/" link>
        <v-list-item-action>
          <v-icon>home</v-icon>
        </v-list-item-action>
        <v-list-item-content>主页</v-list-item-content>
      </v-list-item>
      <template v-for="(item, i) in items">
        <ExpandGroup
          :key="`group-${i}`"
          :item="item"
        />
      </template>
    </v-list>

    <template v-slot:append>
      <Footer />
    </template>
  </v-navigation-drawer>
</template>

<script>
  import Footer from '@/components/Footer'
  import ExpandGroup from '@/components/ExpandGroup'
  import kebabCase from 'lodash/kebabCase'
  import {archiveItems} from '@/api/drawer'
  import {mapGetters} from 'vuex'

  export default {
  name: 'Drawer',
  components: { ExpandGroup, Footer },
  data: () => ({
    show: false,
    drawerItems: [],
    icons: [
      'fab fa-weibo',
      'fab fa-github',
    ],
  }),
  computed: {
    ...mapGetters(['name','avatar', 'email']),
    children () {
      return this.item.children.map(item => ({
        ...item,
        to: `${this.item.group}/${item.to}`,
      }))
    },
    group () {
      return this.item.children.map(item => {
        return `${this.item.group}/${kebabCase(item.name)}`
      }).join('|')
    },
    items () {
      return this.drawerItems
    },
    drawer: {
      get() {
        return this.$store.state.drawer
      },
      set(val) {
        this.$store.dispatch('setDrawer', val)
      }
    }
  },
  mounted() {
    this.getData()
  },
  methods: {
    getData() {
      archiveItems().then(responese => {
        this.drawerItems = responese.data.items
      })
    },
    toggleDrawer(val) {
      const old = this.drawer
      if (val != old) {
        this.$store.dispatch('toggleDrawer')
      }
    },
  }
}
</script>

<style>
  .emailBtn{
    background-color: white;
    opacity:0.65;
  }
</style>

