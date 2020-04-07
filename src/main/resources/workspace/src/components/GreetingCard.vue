<template>
  <div>
    <v-card
      class="mx-auto"
    >
      <v-img
        class="white--text"
        max-height="400"
        max-width="600"
        src="../assets/daily_pic.png"
      >
        <v-card-title class="align-end fill-height">Hi, nice to meet you</v-card-title>
      </v-img>

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
          </v-list-item-content>
        </v-list-item>
      </v-card-actions>
    </v-card>
  </div>
</template>

<script>
  import {mapGetters} from 'vuex'

  export default {
    name: 'GreetingCard',
    computed: {
      ...mapGetters(['name', 'avatar'])
    }
  }
</script>
