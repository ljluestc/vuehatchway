<template>
  <div class="home">
    <h1>English Helper - Vocabulary Learning</h1>
    <p>Welcome to your English vocabulary learning application!</p>
    
    <div class="vocabulary-sections">
      <Section type="Phrases" request-type="phrases"></Section>
      <Section type="Words" request-type="words"></Section>
      <Section type="Phrasal Verbs" request-type="phrasal-verbs"></Section>
    </div>
  </div>
</template>

<script>
import Section from '@/components/VocabularySection.vue'

export default {
  name: 'Home',
  components: {
    Section
  }
}
</script>

<style scoped>
.home {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.home h1 {
  color: #2c3e50;
  text-align: center;
  margin-bottom: 10px;
}

.home p {
  text-align: center;
  color: #666;
  margin-bottom: 30px;
  font-size: 18px;
}

.vocabulary-sections {
  display: flex;
  flex-direction: column;
  gap: 30px;
}
</style>
