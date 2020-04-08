<template>
  <SettingCard title="个性化设置">
    <template v-slot:default>
      <v-list two-line subheader dense>
        <v-subheader>个人信息</v-subheader>
        <v-list-item class="d-flex">
          <v-list-item-content>
            <v-list-item-subtitle>头像</v-list-item-subtitle>
            <div class="d-flex">
              <v-avatar size="64">
                <img :src="avatar" alt="avatar" />
              </v-avatar>
              <v-spacer></v-spacer>
              <v-text-field
                label="URL"
                v-model="avatarUrl"
              ></v-text-field>
            </div>
          </v-list-item-content>
          <v-list-item-action>
            <v-btn
              @click="$emit('update:avatar', avatarUrl)"
              color="primary"
            >更新</v-btn>
          </v-list-item-action>
        </v-list-item>
        <v-list-item class="d-flex align-center justify-center">
          <v-list-item-icon>
            <v-icon>email</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-text-field
              label="邮箱"
              dense
              type="email"
              v-model="emailAddress"
            ></v-text-field>
          </v-list-item-content>
          <v-list-item-action>
            <v-btn
              @click="$emit('update:email', emailAddress)"
              color="primary"
            >更新</v-btn>
          </v-list-item-action>
        </v-list-item>
      </v-list>
    </template>
  </SettingCard>
</template>

<script>
  import SettingCard from "./SettingCard";

  export default {
    name: "personalizeCard",
    components: {
      SettingCard
    },
    props: ['avatar', 'email'],
    mounted() {
      this.avatarUrl = this.avatar
      this.emailAddress = this.email
    },
    data: () => ({
      avatarUrl: '',
      emailAddress: ''
    })
  }
</script>

<style scoped>

</style>