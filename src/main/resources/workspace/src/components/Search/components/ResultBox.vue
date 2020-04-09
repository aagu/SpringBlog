<template>
  <div>
    <v-card
      class="mx-auto"
      v-show="show"
      :light="!dark"
      width="260"
    >
      <v-card-text class="text-center" v-if="loading">
        <v-progress-circular
          indeterminate
        ></v-progress-circular>
      </v-card-text>
      <v-card-text class="text-center" v-else-if="items.length === 0">No Data</v-card-text>
      <transition-group name="list" v-else>
        <v-list-item
          v-for="(item, idx) in items"
          :key="`key-${idx}`"
          :to="`/detail/${item.id}`"
          @click="$emit('close')"
        >
          <v-list-item-content>
            <v-list-item-title class="text-center">{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </transition-group>
    </v-card>
    <v-row v-show="persistClose || show">
      <v-col cols="4" offset="4" class="d-flex justify-center">
        <v-btn fab small :light="!dark" @click="$emit('close')">
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
    },
    loading: {
      type: Boolean,
      default: false
    }
  }
}
</script>