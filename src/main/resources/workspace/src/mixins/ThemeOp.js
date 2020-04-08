import {setTheme} from "../utils/helper";
import store from "@/store";

export default {
  methods: {
    toggleDark() {
      this.$vuetify.theme.dark = !this.$vuetify.theme.dark
      setTheme(this.$vuetify.theme.dark? 'dark': 'light')
      store.dispatch('setDark', this.$vuetify.theme.dark)
    }
  }
}