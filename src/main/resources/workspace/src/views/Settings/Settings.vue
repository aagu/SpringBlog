<template>
  <v-container>
    <v-row>
      <v-col md="6" sm="12">
        <SystemCard />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
  import SystemCard from "./components/SystemCard";
  export default {
    name: "Settings",
    components: {
      SystemCard
    },
    methods: {
      showPrompt() {
        const res = this.$dialog.confirm({
          text: 'Do you really want to exit?',
          title: 'Warning',
          showClose: false
        })
      },
      showMessage() {
        this.$dialog.message.success('ok', {
          position: 'top'
        })
      }
    }
  }
</script>

<style scoped>

</style>