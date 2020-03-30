import 'material-design-icons-iconfont/dist/material-design-icons.css' // Ensure you are using css-loader
import '@fortawesome/fontawesome-free/css/all.css' // Ensure you are using css-loader
import Vue from 'vue';
import Vuetify from 'vuetify/lib';

Vue.use(Vuetify);

import zhHans from 'vuetify/es5/locale/zh-Hans'

export default new Vuetify({
  icons: {
    iconfont: 'md',
  },
  lang: {
    locales: { zhHans },
    current: 'zhHans',
  }
});
