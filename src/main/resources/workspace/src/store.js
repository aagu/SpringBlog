import Vue from 'vue'
import Vuex from 'vuex'
import md5 from 'js-md5'
import {getInfo, getUser, login, logout} from '@/api/user'
import {getToken, removeToken, setToken} from '@/utils/auth'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    appBarTitle: 'Home',
    drawer: null,
    snackbar: false,
    labelMap: [],
    token: getToken(),
    name: null,
    avatar: ''
  },
  getters: {
    name: state => {
      return state.name
    },
    token: state => {
      return state.token || undefined
    },
    avatar: state => {
      return state.avatar
    }
  },
  mutations: {
    SET_TOOLBAR: (state, title) => {
      state.appBarTitle = title
    },
    SET_DRAWER: (state) => {
      state.drawer = !state.drawer
    },
    SET_SNACKBAR: (state) => {
      state.snackbar = !state.snackbar
    },
    SET_LABEL_MAP: (state, map) => {
      state.labelMap = map
    },
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
}
  },
  actions: {
    setToolbar({ commit }, title) {
      commit('SET_TOOLBAR', title)
    },
    toggleDrawer({ commit }) {
      commit('SET_DRAWER')
    },
    toggleSnackbar({ commit }) {
      commit('SET_SNACKBAR')
    },
    setLabelMap({ commit }, map) {
      commit('SET_LABEL_MAP', map)
    },

    // user login
    login( { commit }, userInfo) {
      const { username, password } = userInfo
      return new Promise((resolve, reject) => {
        login({ username: username.trim(), password: md5(password) }).then(resp => {
          const { data } = resp.data
          commit('SET_TOKEN', data.token)
          setToken(data.token)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    getUser({ commit }) {
      return new Promise((resolve, reject) => {
        getUser().then(resp => {
          const data = resp.data.data

          const { name, avatar } = data

          commit('SET_NAME', name)
          commit('SET_AVATAR', avatar)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // get user info
    getInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getInfo(state.token).then(response => {
          const { code, data } = response.data
  
          if (!data) {
            reject('Verification failed, please Login again.')
          }
  
          const { name } = data
  
          commit('SET_NAME', name)
          resolve(data)
        }).catch(error => {
          reject(error)
        })
      })
    },
  
    // user logout
    logout({ commit, state, dispatch }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('SET_TOKEN', '')
          removeToken()
  
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
  
    // remove token
    resetToken({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken()
        resolve()
      })
    },
  }
})
