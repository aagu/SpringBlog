<template>
  <v-row>
    <v-col
      md="8"
      offset-md="2"
    >
      <v-card
        class="mx-auto"
      >
        <v-img
          class="white--text"
          max-height="200"
          :src=randomPic.img
        >
          <router-link :to="url">
            <v-card-title
              :class="{ 'white--text': randomPic.color === 'white', 'grey--text text--darken-4': randomPic.color === 'black' }"
              class="align-end fill-height headline"
            >{{title}}</v-card-title>
          </router-link>
        </v-img>

        <v-card-text class="subtitle-1">{{ content }}</v-card-text>
        <v-divider></v-divider>
        <v-card-actions>
          <v-list-item class="grow" justify-start>
            <v-list-item-avatar color="grey darken-3">
              <v-img
                class="elevation-6"
                :src="avatar"
              ></v-img>
            </v-list-item-avatar>

            <v-list-item-content>
              <v-list-item-title>{{ name }}</v-list-item-title>
              <v-list-item-subtitle>
                {{ date | parseTime('{y}-{m}-{d} {h}:{i}') }}
              </v-list-item-subtitle>
            </v-list-item-content>
            <v-spacer class="d-none d-sm-block"></v-spacer>
            <v-chip>{{label}}</v-chip>
            <router-link :to="url">
              <v-icon>open_in_new</v-icon>
            </router-link>
          </v-list-item>
        </v-card-actions>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
  import allBgImages from 'randomBg'
  import {mapGetters} from 'vuex'

  export default {
    name: 'ArticleCard',
    props: {
      title: String,
      content: String,
      author: String,
      date: [String, Number],
      url: String,
      label: String,
    },
    data: () => ({
      width: 800,
      height: 340,
      allBgImages
    }),
    computed: {
      ...mapGetters(['name', 'avatar']),
      randomPic() {
        const randIndex = Math.floor(Math.random() * this.allBgImages.length)
        return this.allBgImages[randIndex]
      }
    }
  }
</script>

<style>
  a{text-decoration: none}
  a:hover{text-decoration: none}
</style>
