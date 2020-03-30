<template>
  <v-fab-transition>
    <v-btn
      v-show="fab"
      v-scroll="onScroll"
      :style="{
        bottom: snackbar ? '80px' : ''
      }"
      aria-label="Scroll to top"
      bottom
      color="red"
      dark
      fab
      small
      fixed
      right
      title="Scroll to top"
      @click="toTop"
    >
      <v-icon>keyboard_arrow_up</v-icon>
    </v-btn>
  </v-fab-transition>
</template>

<script>
  // Utilities
  import {
    mapState,
  } from 'vuex'
  export default {
    name: 'BackToTop',
    data: () => ({
      fab: false,
    }),
    computed: {
      ...mapState(['snackbar']),
    },
    methods: {
      onScroll () {
        if (typeof window === 'undefined') return
        const top = window.pageYOffset ||
          document.documentElement.offsetTop ||
          0
        this.fab = top > 200
      },
      toTop () {
        //this.$router.push({ hash: '' })
        this.$vuetify.goTo(0)
      },
    },
  }
</script>
