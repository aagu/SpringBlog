<template>
  <v-container>
    <v-row>
      <v-col md="3" sm="12"><AboutMeCard></AboutMeCard></v-col>
      <v-col md="9" sm="12"><TimelineCard></TimelineCard></v-col>
    </v-row>
  </v-container>
</template>

<script>
  import AboutMeCard from "@/components/AboutMeCard";
  import TimelineCard from "@/components/TimelineCard";

  export default {
    name: "Profile",
    components: {
      AboutMeCard,
      TimelineCard
    }
  }
</script>

<style scoped>

</style>