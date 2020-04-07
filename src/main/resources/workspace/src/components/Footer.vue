<template>
  <v-footer
    class="text-center"
  >
    <v-flex
      text-center
      xs12
    >
      <v-card-text>
        <v-btn
          v-for="(icon, idx) in icons"
          :key="idx"
          class="mx-4"
          icon
          :href="icon.to"
        >
          <v-icon size="20px">{{ icon.ico }}</v-icon>
        </v-btn>
      </v-card-text>
      
      <v-divider></v-divider>

      <v-card-text>
        <strong>Copyright ©</strong> — {{ new Date().getFullYear() }} — <strong>aagu</strong>
        <span class="caption">浙ICP备20010527号-1</span>
      </v-card-text>
    </v-flex>
  </v-footer>
</template>

<script>
  export default {
    name: 'Footer',
    data: () => ({
      icons: [
        {ico: 'fab fa-github', to: 'https://github.com/aagu'},
      ],
    }),
  }
</script>
