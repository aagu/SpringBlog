<template>
  <div>
    <v-card
      transition="fade-transition"
      light
      class="mx-auto"
      v-show="show"
      :dark="dark"
    >
      <transition-group>
        <v-list-item
          v-for="(item, idx) in items"
          :key="`key-${idx}`"
          :to="`/detail/${item.id}`"
          @click="$emit('close')"
        >
          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </transition-group>
    </v-card>
    <v-row v-show="persistClose || show">
      <v-col cols="4" offset="4" class="d-flex justify-center">
        <v-btn fab small :dark="dark" @click="$emit('close')">
          <v-icon>close</v-icon>
        </v-btn>
      </v-col>
    </v-row>
  </div>
</template>

<script>
export default {
  name: 'SearchBox',
  props: {
    items: Array,
    show: Boolean,
    persistClose: {
      type: Boolean,
      default: false
    },
    dark: {
      type: Boolean,
      default: false
    }
  }
}
</script>