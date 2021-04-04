<template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <Section type="Main vocabulary" src-data="/api/v1/translations/phrases" src-count="src-data"></Section>
<!--    <Section type="Phrasal verbs" src-data="/test" src-count="src-data"></Section>-->
<!--    <Section type="Useful phrases" src-data="/test" src-count="src-data"></Section>-->
    <HelloWorld msg="Welcome to Your Vue.js App"/>
  </div>
</template>

<script>
import HelloWorld from '@/components/HelloWorld.vue'
import Section from '@/components/VocabularySection.vue'

export default {
  name: 'Home',
  components: {
    Section,
    HelloWorld
  }
}
</script>
