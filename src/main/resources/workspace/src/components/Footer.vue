<template>
  <v-footer
    class="text-center"
  >
    <v-flex
      text-center
      xs12
    >
      <div>
        <v-btn
          v-for="(icon, idx) in icons"
          :key="idx"
          class="mx-4"
          icon
          :href="icon.to"
        >
          <v-icon size="20px">{{ icon.ico }}</v-icon>
        </v-btn>
      </div>
      
      <v-divider></v-divider>

      <v-card-text>
        <strong>Copyright ©</strong> — {{ new Date().getFullYear() }} — <strong>aagu</strong>
        <span class="caption" style="color:#939393;">浙ICP备20010527号-1</span>
        <div style="margin:0 auto;">
          <a target="_blank" href="http://www.beian.gov.cn/portal/registerSystemInfo?recordcode=33011802001968" style="display:flex;text-decoration:none;height:20px;line-height:20px;">
            <img src="http://www.beian.gov.cn/img/ghs.png" style="width: 16px; height: 16px"/>
            <p class="caption" style="float:left;margin: 0 0 0 5px; color:#939393;">浙公网安备 33011802001968号</p>
          </a>
        </div>
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
