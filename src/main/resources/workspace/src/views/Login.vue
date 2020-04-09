<template>
  <v-app id="inspire">
    <v-content>
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
    </v-content>
  </v-app>
</template>

<script>
  export default {
    name: 'Login',
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

<style scoped>
  #inspire {
    height: 50%;
    width: 100%;
    position: absolute;
    top: 0;
    left: 0;
    z-index: 0;
    background-color: #536DFE;
  }
</style>