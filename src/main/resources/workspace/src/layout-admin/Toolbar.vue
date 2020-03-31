<template>
  <v-app-bar
    app
    color="primary"
    dark
    hide-on-scroll
    elevation="0"
  >
    <v-app-bar-nav-icon
      @click.stop="toggleDrawer"
      color="grey darken-4"
    ></v-app-bar-nav-icon>
    <v-toolbar-title>{{ title }}</v-toolbar-title>

    <v-spacer />
    
    <v-menu offset-y>
      <template v-slot:activator="{ on }">
        <v-btn
          icon
          v-on="on"
          large
          depressed
        >
          <v-avatar size="40px">
            <img src="../assets/avataaars.png" alt="avatar" />
          </v-avatar>
        </v-btn>
      </template>

      <v-list>
        <v-list-item
          v-for="action in userActions"
          :key="action.title"
          @click="action.click"
        >
          <v-list-item-action v-if="action.icon">
            <v-icon>{{ action.icon }}</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>{{ action.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-menu>
  </v-app-bar>
</template>

<script>
export default {
  data() {
    return {
      userActions: [
        {
          title: 'profile',
          icon: 'account_circle',
          click: this.goProfile
        }, {
          title: 'settings',
          icon: 'settings',
          click: this.goSettings
        },
        {
          title: 'logout',
          icon: 'exit_to_app',
          click: this.logout
        }
      ]
    }
  },
  computed: {
    title() {
      return this.$store.state.appBarTitle
    }
  },
  methods: {
    toggleDrawer() {
      this.$store.dispatch('toggleDrawer')
    },
    logout() {
      this.$store.dispatch('logout').then(() => {
        this.$router.push('/login')
      })
    },
    goSettings() {
      this.$router.push({name: 'settings'})
    },
    goProfile() {
      this.$router.push({name: 'profile'})
    }
  }
}
</script>