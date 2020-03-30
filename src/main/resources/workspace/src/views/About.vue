<template>
<div id="app">
  <v-app id="inspire">
    <v-container fluid>
      <v-layout wrap>
        <div class="text-center ma-2">
          <v-btn dark @click="showSnacker">Open Snackbar</v-btn>
        </div>
      </v-layout>
    </v-container>
  </v-app>
</div>
</template>

<script>
import Notification from '@/components/Notification/Notification.js'
  export default {
    methods: {
      showSnacker() {
        Notification.notify({y:'bottom', text: 'on the bottom'})
      }
    }
  }
</script>
