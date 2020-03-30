<template>
  <v-navigation-drawer
    v-model="drawer"
    @input="toggleDrawer"
    app
  >
    <v-list
      dense
      class="py-0"
    >
      <v-img src="../assets/sidebar_header.png">
        <v-layout pa-2 column fill-height class="lightbox white--text" text-center>
          <v-spacer></v-spacer>
          <v-flex shrink>
            <v-avatar size="80">
              <img
                src="../assets/avataaars.png"
                alt="avatar"
              >
            </v-avatar>
            <div class="headline">aagu</div>
            <v-card-actions class="white--text">
              <span>aagu@outlook.com</span>
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
                <v-list-item class="emailBtn" href="mailto:aagu@outloo.com">
                  <v-list-item-icon>mail</v-list-item-icon>
                  <v-list-item-title>email me</v-list-item-title>
                </v-list-item>
              </div>
            </v-expand-transition>
          </v-flex>
        </v-layout>
      </v-img>
      <v-divider></v-divider>
      <v-list-item to="/">
        <v-list-item-action>
          <v-icon>home</v-icon>
        </v-list-item-action>
        <v-list-item-content>
          <v-list-item-title>主页</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
      <template v-for="(item, i) in items">
        <!-- <v-subheader
          v-if="item.header"
          :key="`subheader-${i}`"
          v-text="item.header"
        />
        <v-divider
          v-else-if="item.divider"
          :key="`divider-${i}`"
        /> -->
        <ExpandGroup
          :key="`group-${i}`"
          :item="item"
        />
        <!-- <ExpandItem
          v-else
          :key="`item-${i}`"
          :icon="item.icon"
          :subtext="item.subtext"
          :text="item.text"
          :to="item.to"
        /> -->
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
import { archiveItems } from '@/api/drawer'

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
        // this.$store.dispatch('toggleDrawer')
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

