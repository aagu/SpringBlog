<template>
  <v-card>
    <v-card-title>{{ title }}</v-card-title>
    <v-divider></v-divider>
    <div style="padding: 0 16px">
      <slot name="default"></slot>
    </div>
    <v-card-actions>
      <slot name="action"></slot>
    </v-card-actions>
  </v-card>
</template>

<script>
  export default {
    name: "SettingCard",
    props: ['title']
  }
</script>

<style scoped>

</style>