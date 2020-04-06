<template>
  <v-container>
    <v-layout align-center justify-center>
      <div class="text-md-center">
        <h1>404</h1>
        <h2 class="my-3 headline">Sorry, page not found</h2>
        <div>
          <v-btn color="primary" @click="home">Go Home</v-btn>
        </div>
      </div>
    </v-layout>
  </v-container>
</template>

<script>
  export default {
    name: "NotFound",
    methods: {
      home() {
        this.$router.push({ path: '/' })
      }
    }
  }
</script>

<style scoped>

</style>