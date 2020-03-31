<template>
  <div>
    <span class="name">{{ name }}</span>
    <v-row dense class="d-flex align-center justify-center">
      <v-col cols="10">
        <v-progress-linear
          :color="color"
          rounded
          :value="value"
        ></v-progress-linear>
      </v-col>
      <v-col cols="2">
        <span v-if="value < 100" class="body-2">{{ value }}%</span>
        <v-icon v-else color="success">check_circle</v-icon>
      </v-col>
    </v-row>
  </div>
</template>

<script>
  export default {
    name: "SkillLevel",
    props: ['name', 'value'],
    computed: {
      color: function () {
        return this.value === 100? 'green': 'light-blue'
      }
    }
  }
</script>

<style scoped>
  .name {
    padding-top: 8px;
  }
</style>