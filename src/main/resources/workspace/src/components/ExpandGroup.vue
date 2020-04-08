<template>
  <v-list-group
    :prepend-icon="item.icon"
    no-action
    color="anchor"
  >
    <template v-slot:activator>
      <v-list-item-content class="v-list-group__activator">
        <div class="d-flex justify-space-between align-center">
          <div>
            {{ item.text }}
          </div>
          <v-avatar size="28" color="chip">
            <span>{{ children.length }}</span>
          </v-avatar>
        </div>
      </v-list-item-content>
    </template>
    <template v-for="(child, i) in children">
      <v-list-item
        :to="child.to"
        link
      >
        <v-list-item-content>
          <v-list-item-title>{{ child.text }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </template>
  </v-list-group>
</template>

<script>
  // Utilities
  import ExpandItem from './ExpandItem'

  export default {
    name: 'ExpandGroup',
    components: { ExpandItem },
    inheritAttrs: false,
    props: {
      item: {
        type: Object,
        default: () => ({
          text: '',
          group: '',
          children: [],
        }),
      },
    },
    computed: {
      children () {
        return this.item.children.map(item => ({
          text: item.text,
          to: `?${this.item.group}=${item.to}`,
        }))
      },
    },
    methods: {
      goTo(to) {
        this.$router.push(to)
      }
    },
  }
</script>

<style>
.v-list-group__activator p {
  margin-bottom: 0;
}
</style>
