<template>
  <v-container>
    <v-tabs>
      <v-tabs-slider></v-tabs-slider>
      <v-tab>系统项</v-tab>
      <v-tab>个性化</v-tab>
      <v-tab-item>
        <v-card
          flat
          tile
        >
          <v-subheader>登录</v-subheader>

          <v-row>
            <v-col md="6" sm="12">
              <v-text-field
                label="用户名"
                outlined
              ></v-text-field>
            </v-col>
            <v-col md="6" sm="12">
              <v-text-field
                label="密码"
                outlined
              ></v-text-field>
            </v-col>
          </v-row>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <v-card
          flat
          tile
        >
          Content
        </v-card>
      </v-tab-item>
    </v-tabs>
  </v-container>
</template>

<script>
  export default {
    name: "Settings",
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