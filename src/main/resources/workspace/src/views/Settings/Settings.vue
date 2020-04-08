<template>
  <v-container>
    <v-row>
      <v-col cols="12" md="6" sm="12">
        <SystemCard :name="name"/>
      </v-col>
      <v-col cols="12" md="6" sm="12">
        <PersonalizeCard
          :avatar="avatar"
          :email="email"
        />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
  import SystemCard from "./components/SystemCard";
  import PersonalizeCard from "./components/PersonalizeCard";
  import {mapGetters} from 'vuex'

  export default {
    name: "Settings",
    components: {
      SystemCard,
      PersonalizeCard
    },
    computed: {
      ...mapGetters(['name', 'email', 'avatar'])
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
      },
    }
  }
</script>

<style scoped>

</style>