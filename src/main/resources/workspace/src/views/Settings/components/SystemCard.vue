<template>
  <v-card>
    <v-card-title>系统设置</v-card-title>
    <v-divider></v-divider>
    <v-list two-line subheader dense>
      <v-subheader>登录与安全</v-subheader>
      <v-list-item>
        <v-list-item-icon>
          <v-icon>account_circle</v-icon>
        </v-list-item-icon>
        <v-list-item-content>
          <v-text-field
            label="用户名"
            outlined
            dense
            v-model="person.name"
          >
          </v-text-field>
        </v-list-item-content>
      </v-list-item>

      <v-list-item>
        <v-list-item-icon>
          <v-icon>lock</v-icon>
        </v-list-item-icon>
        <v-list-item-content>
          <v-text-field
            label="密码"
            outlined
            dense
            :type="passVisible? 'text': 'password'"
            :append-icon="passVisible? 'visibility' : 'visibility_off'"
            @click:append="passVisible = !passVisible"
            v-model="person.password"
          ></v-text-field>
        </v-list-item-content>
      </v-list-item>
    </v-list>

    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn depressed dark color="indigo">
        更新
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
  import { sec } from "@/api/user";

  export default {
    name: "SystemCard",
    data: () => ({
      passVisible: false,
      person: {
        name: '',
        password: ''
      }
    }),
    computed: {

    },
    mounted() {
      sec(this.$store.getters.name).then(resp => {
        this.person = { name: resp.data.name, password: resp.data.password }
      })
    }
  }
</script>

<style scoped>

</style>