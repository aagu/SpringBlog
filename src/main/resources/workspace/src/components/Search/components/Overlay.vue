<template>
  <div class="overlay" >
    <p :style="cover" v-show="value" @touchmove.prevent @scroll.prevent></p>
    <div class="content" :style="{ zIndex: Number(zIndex) + 1 }" v-show="value">
      <slot></slot>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    zIndex: {
      type: String,
      default: '5'
    }, 
    color: {
      type: String,
      default: 'rgb(33,33,33)'
    }, 
    opacity: {
      type: String,
      default: '0.46'
    },
    value: {
      type: Boolean,
      default: false
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '100%'
    }
  },
  computed: {
    cover: function() {
      return {
        position: 'absolute',
        top: '0px',
        left: '0px',
        zIndex: this.zIndex,
        backgroundColor: this.color,
        opacity: this.opacity,
        width: this.width,
        height: this.height
      }
    }
  },
}
</script>

<style scoped>
  .overlay {
    align-items: center;
    border-radius: inherit;
    display: flex;
    justify-content: center;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
  }
  .content  {
    z-index: 6;
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    display: flex;
    overflow-y: scroll;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 12px;
  }
</style>