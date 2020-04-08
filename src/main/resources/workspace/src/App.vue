<template>
  <router-view></router-view>
</template>

<script>
  import {getTheme} from "@/utils/helper";

  export default {
  name: 'App',
  created() {
    if (getTheme() === 'dark') {
      this.$vuetify.theme.dark = true
    }
  }
};
</script>
