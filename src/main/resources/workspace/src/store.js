import Vue from 'vue'
import Vuex from 'vuex'
import { login, logout, getInfo } from '@/api/user'
import { getToken, setToken, removeToken } from '@/utils/auth'
import router from './router'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    appBarTitle: 'Home',
    drawer: null,
    snackbar: false,
    labelMap: [],
    token: getToken(),
    name: ''
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
        login({ username: username.trim(), password: password}).then(resp => {
          const { data } = resp.data
          commit('SET_TOKEN', data.token)
          setToken(data.token)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // get user info
    getInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getInfo(state.token).then(response => {
          const { data } = response
  
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
