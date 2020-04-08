<template>
  <div v-resize="onResize">
    <div v-if="window.x > 600">
      <v-text-field
        id="search"
        key="search"
        ref="search"
        v-model="search"
        label="搜索"
        append-icon="search"
        clearable
        hide-details
        single-line
        solo
        light
        :dark="dark"
        @click:append="doSearch"
        @click:clear="clear"
        @keyup.enter.native="doSearch"
      />
      <ResultBox
        :dark="dark"
        style="position: absolute; right: 20px; top: 64px"
        :show="searchResult"
        :items="result"
        v-on:close="clear"
      ></ResultBox>
    </div>
    <div v-else>
      <v-btn icon @click="showSearch = true">
        <v-icon>search</v-icon>
      </v-btn>

      <Overlay
        :value="showSearch"
        :height="`${window.y}px`"
      >
        <v-text-field
          key="search"
          ref="search"
          v-model="search"
          label="搜索"
          append-icon="search"
          clearable
          hide-details
          single-line
          solo
          light
          :dark="dark"
          @click:append="doSearch"
          @click:clear="clear"
          @keyup.enter.native="doSearch"
          style="padding-bottom: 12px;"
        />
        <ResultBox
          :dark="dark"
          :show="searchResult"
          :items="result"
          :persistClose="true"
          v-on:close="clear"
        ></ResultBox>
      </Overlay>
    </div>
  </div>
</template>

<script>
  import {getArticleList} from '@/api/article'
  import ResultBox from './components/ResultBox'
  import Overlay from './components/Overlay'
  import {mapGetters} from 'vuex'

  export default {
  name: 'SearchBox',
  components: {
    ResultBox,
    Overlay
  },
  data: () => ({
    isSearching: false,
    search: '',
    window: {
      y: 0,
      x: 0
    },
    result: [],
    searchResult: false,
    showSearch: false
  }),
  computed: {
    ...mapGetters(['dark'])
  },
  beforeDestroy() {
    this.clear()
  },
  methods: {
    onResize () {
      this.window = { x: window.innerWidth, y: window.innerHeight }
    },
    doSearch() {
      if (!this.search || this.search === '') return
      this.searchResult = true
      getArticleList({page: 1, limit: 10, search: this.search}).then(resp => {
        this.result = resp.data.data.items
      })
    },
    clear() {
      this.search = ''
      this.result = []
      this.searchResult = false
      this.showSearch = false
    }
  }
}
</script>

<style scoped>
  .v-overlay__content {
    position: absolute;
    top: 120px;
  }
</style>