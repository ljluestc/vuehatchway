<template>
  <div class="error full-screen" @mousemove="move">
    <div class='container'>
      <span class="error-num">5</span>
      <div ref="eye" v-bind:style="{'-webkit-transform': rotate(), }" class='eye'></div>
      <div ref="eye-right" class='eye'></div>

      <p class="sub-text">Oh eyeballs! Something went wrong. We're <span class="italic">looking</span> to see what happened. Please come back later.</p>
    </div>
  </div>
</template>

<style >
@import '../assets/css/views/Error.css';
</style>

<script>
export default {
    name: 'error',
    methods: {
      move(event) {
        const eye = this.$refs.eye;
        console.log(eye);
        console.log(event);
        const x = (eye.left) + (eye.width / 2);
        const y = (eye.top) + (eye.height / 2);
        const rad = Math.atan2(event.pageX - x, event.pageY - y);
        const rot = (rad * (180 / Math.PI) * -1) + 180;
        eye.style
        eye.css({
          '-webkit-transform': 'rotate(' + rot + 'deg)',
          '-moz-transform': 'rotate(' + rot + 'deg)',
          '-ms-transform': 'rotate(' + rot + 'deg)',
          'transform': 'rotate(' + rot + 'deg)'
        });
      }
    },
}
</script>
