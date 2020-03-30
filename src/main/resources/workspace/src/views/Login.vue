<template>
  <v-app id="inspire">
    <v-content>
      <v-img src="../assets/bg.png">
        <v-container
          class="fill-height"
          fluid
        >
          <v-row
            align="center"
            justify="center"
          >
            <v-col
              cols="12"
              sm="8"
              md="4"
            >
              <v-card class="elevation-10">
                <v-toolbar
                  color="primary"
                  dark
                  flat
                >
                  <v-toolbar-title>后台登录</v-toolbar-title>
                  <!-- <v-spacer />
                  <v-tooltip bottom>
                    <template v-slot:activator="{ on }">
                      <v-btn
                        :href="source"
                        icon
                        large
                        target="_blank"
                        v-on="on"
                      >
                        <v-icon>mdi-code-tags</v-icon>
                      </v-btn>
                    </template>
                    <span>Source</span>
                  </v-tooltip>
                  <v-tooltip right>
                    <template v-slot:activator="{ on }">
                      <v-btn
                        icon
                        large
                        href="https://codepen.io/johnjleider/pen/pMvGQO"
                        target="_blank"
                        v-on="on"
                      >
                        <v-icon>mdi-codepen</v-icon>
                      </v-btn>
                    </template>
                    <span>Codepen</span>
                  </v-tooltip> -->
                </v-toolbar>
                <v-card-text>
                  <v-form
                    ref="loginForm"
                    v-model="valid"
                    lazy-validation
                  >
                    <v-text-field
                      label="用户名"
                      name="login"
                      v-model="token.username"
                      :rules="[v => !!v || '用户名不能为空']"
                      prepend-icon="person"
                      type="text"
                    />

                    <v-text-field
                      id="password"
                      label="密码"
                      name="password"
                      v-model="token.password"
                      prepend-icon="lock"
                      type="password"
                      @keyup.enter.native="login"
                    />
                  </v-form>
                </v-card-text>
                <v-card-actions>
                  <v-spacer />
                  <v-btn color="primary" @click="login">登录</v-btn>
                </v-card-actions>
              </v-card>
            </v-col>
          </v-row>
        </v-container>
      </v-img>
    </v-content>
  </v-app>
</template>

<script>
  import { login } from '@/api/user'

  export default {
    props: {
      source: String,
    },
    data: () => ({
      valid: true,
      token: {
        username: '',
        password: ''
      }
    }),
    methods: {
      login() {
        if (this.$refs.loginForm.validate()) {
          this.$store.dispatch('login', this.token).then(() => {
            this.$router.push({ path: this.redirect || '/admin/dashboard'})
          })
        }
      }
    }
  }
</script>