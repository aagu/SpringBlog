<template>
  <div>
    <v-snackbar
      v-model="snackbar"
      :bottom="y === 'bottom'"
      :color="color"
      :left="x === 'left'"
      :multi-line="mode === 'multi-line'"
      :right="x === 'right'"
      :timeout="timeout"
      :top="y === 'top'"
      :vertical="mode === 'vertical'"
    >
      {{ text }}
      <v-btn
        text
        @click="snackbar = false"
      >
        Close
      </v-btn>
    </v-snackbar>
  </div>
</template>

<script>
  export default {
    data: () => ({
      color: '',
      mode: '',
      snackbar: false,
      text: '',
      timeout: 3000,
      x: null,
      y: 'top',
    }),
    methods: {
      show(props) {
        this.color = props.color
        this.mode = props.mode
        this.timeout = props.timeout
        this.x = props.x
        this.y = props.y
        this.text = props.text
        this.snackbar = true
      }
    }
  }
</script>