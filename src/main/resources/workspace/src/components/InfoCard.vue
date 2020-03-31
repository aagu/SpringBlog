<template>
  <v-card
    :color="color"
    :to="to"
    :disabled="to === null"
  >
    <div class="d-flex flex-no-wrap justify-space-between">
      <v-avatar
        color="rgba(255, 255, 255, 0.2)"
        class="ma-4"
      >
        <v-icon large>{{ icon }}</v-icon>
      </v-avatar>

      <div>
        <v-card-title
          class="headline"
          v-text="title"
        ></v-card-title>

        <v-card-subtitle v-text="subTitle" class="text-right"></v-card-subtitle>
      </div>
    </div>
  </v-card>
</template>

<script>
export default {
  name: 'infoCard',
  props: ['color', 'icon', 'title', 'subTitle', 'to']
}
</script>

<style scoped>
  .icon-wrapper {
    opacity: 0.4;
    background-color:white;
    border-radius: 25px;
    padding: 10px;
  }
</style>