<template>
  <v-card>
    <v-card-text class="subtitle-1 font-weight-bold">About</v-card-text>
    <v-divider></v-divider>
    <v-list-item-content class="content_box">
      <v-avatar size="120">
        <img :src="avatar" alt="avatar"/>
      </v-avatar>
      <div class="text-center font-weight-bold name">{{ name }}</div>
      <div class="align-center section"><v-icon>people</v-icon><span>Bio</span></div>
      <v-divider></v-divider>
      <v-list-item-content class="text--secondary">Keep Learning</v-list-item-content>
      <div class="align-center section"><v-icon>assignment</v-icon><span>Skills</span></div>
      <v-divider></v-divider>
      <template
        v-for="(skill, idx) in skills"
      >
        <SkillLevel :name="skill.name" :value="skill.level" :key="idx"/>
      </template>
    </v-list-item-content>
  </v-card>
</template>

<script>
  import SkillLevel from "./SkillLevel";
  import {mapGetters} from 'vuex'

  export default {
    name: "AboutMeCard",
    components: {
      SkillLevel
    },
    computed: {
      ...mapGetters(['name', 'avatar'])
    },
    data: () => ({
      skills: [
        { name: 'Java', level: 70 },
        { name: 'Spring', level: 50 },
        { name: 'Vue.js', level: 50 }
      ]
    })
  }
</script>

<style scoped>
  .content_box {
    padding: 12px 20px;
  }
  .name {
    margin-top: 12px;
  }
  .section {
    display: flex;
    align-self: center;
  }
  .section i {
    margin: 0 4px 0 0;
  }
</style>