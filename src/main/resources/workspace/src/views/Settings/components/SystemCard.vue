<template>
  <SettingCard title="系统设置">
    <template v-slot:default>
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
    </template>

    <template v-slot:action>
      <v-spacer></v-spacer>
      <v-btn depressed dark color="indigo">
        更新
      </v-btn>
    </template>
  </SettingCard>
</template>

<script>
  import SettingCard from "./SettingCard";

  export default {
    name: "SystemCard",
    components: { SettingCard },
    props: ['name'],
    data: () => ({
      passVisible: false,
      person: {
        name: '',
        password: ''
      }
    }),
    mounted() {
      this.person.name = this.name
    },
  }
</script>

<style scoped>

</style>