<template>
  <div class="translation-wrapper">
    <div class="text-wrapper">
      <label for="text">Word or phrase in English</label>
      <input type="text" class="form-control" id="text" placeholder="sun">
    </div>
    <div class="transcription-wrapper">
      <label for="transcription">Transcription</label>
      <input type="text" class="form-control" id="transcription" placeholder="[sʌn]">
    </div>
    <div class="translation-wrapper">
      <label for="translation">Transcription</label>
      <textarea class="form-control" id="translation" placeholder="солнце"></textarea>
    </div>
    <div class="example-wrapper">
      <label for="example">Transcription</label>
      <textarea class="form-control" id="example" placeholder="Учить английский с примерами гораздо легче"></textarea>
    </div>
    <div class="type-wrapper">
      <label for="translationType">Translation Type</label>
      <multiselect
          v-model="value"
          id="translationType"
          :options="translationTypes"
          :preselect-first="true"
          label="name"
          track-by="name"
          placeholder="Pick a type">
      </multiselect>
    </div>
    <div class="tag-wrapper">
      <label for="tags">Tags</label>
      <multiselect
          v-model="tags"
          id="tags"
          :multiple="true"
          :options="availableTags"
          placeholder="Add some tags">
      </multiselect>
    </div>
  </div>
</template>

<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>

<script>
import Multiselect from 'vue-multiselect';

export default {
  name: 'translation',
  components: { Multiselect },
  props: {
    id: String,
  },
  data() {
    return {
      translation: null,
      errors: [],
      value: null,
      tags: null,
      translationTypes: [
        { name: 'Phrase', value: 'PHRASE' },
        { name: 'Phrasal Verb', value: 'PHRASAL_VERB' },
        { name: 'Word', value: 'WORD' },
      ],
      availableTags: [
          "IT",
          "Business",
          "Cooking"
      ]
    }
  },
  created() {
    // this.getData()
  },
  methods: {
    getData() {
      // TranslationService.getByType(this.requestType)
      //     .then(response => {
      //       console.log('Section got the data');
      //       console.log(response);
      //       if (response && response.data) {
      //         this.response = response.data
      //       }
      //     })
      //     .catch(e => {
      //       this.errors.push(e)
      //       console.log('error in section');
      //       console.log(this.errors);
      //     });
    }
  }
}
</script>
