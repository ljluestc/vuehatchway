<template>
  <div class="translation-wrapper">
    <form @submit.prevent="submit()">
      <div v-if="errors.length">
        <ul class="list-group list-group-flush">
          <li class="list-group-item list-group-item-danger" v-for="error in errors" :key="error" >{{ error }}</li>
        </ul>
      </div>
      <div class="text-wrapper form-group required">
        <label class="control-label" for="text">Word or phrase in English</label>
        <input v-model="text"  type="text" class="form-control" id="text" placeholder="sun">
      </div>
      <div class="transcription-wrapper form-group">
        <label class="control-label" for="transcription">Transcription</label>
        <input v-model="transcription" type="text" class="form-control" id="transcription" placeholder="[sʌn]">
      </div>
      <div class="translation-wrapper form-group required">
        <label class="control-label" for="translation">Translation</label>
        <textarea v-model="translation" class="form-control" id="translation" placeholder="солнце"></textarea>
      </div>
      <div class="type-wrapper form-group required">
        <label class="control-label" for="translationType">Translation Type</label>
        <multiselect
            v-model="type"
            id="translationType"
            :options="translationTypes"
            :preselect-first="true"
            label="name"
            track-by="name"
            placeholder="Pick a type">
        </multiselect>
      </div>
      <div class="example-wrapper form-group">
        <label class="control-label" for="example">Example</label>
        <textarea v-model="example" class="form-control" id="example" placeholder="Учить английский с примерами гораздо легче"></textarea>
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
      <div>
        <b-btn variant="success" type="submit">Save</b-btn>
      </div>
    </form>
  </div>
</template>

<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>

<script>
import multiselect from 'vue-multiselect';
import translationService from '../services/translations.service';
import transaltion from '../models/translation';

export default {
  name: 'translation',
  components: {
    multiselect
  },
  props: {
    id: String,
  },
  data() {
    return {
      translation: null,
      transcription: null,
      errors: [],
      text: null,
      type: null,
      tags: [],
      example: null,
      translationTypes: [
        { name: 'Phrasal Verb', value: 'PHRASAL_VERB' },
        { name: 'Phrase', value: 'PHRASE' },
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
    isValid() {
      this.errors = [];
      if (!this.text) {
        this.errors.push('Please fill Word or Phrase');
      }
      if (!this.translation) {
        this.errors.push('Please fill Translation');
      }
      if (!this.type) {
        this.errors.push('Please select a Type');
      }

      return this.errors.length === 0;
    },
    submit() {
      if (!this.isValid()) {
        return;
      }

      let translation = new transaltion(
          this.id,
          this.text,
          this.transcription,
          this.translation,
          this.type.value,
          this.example,
          this.tag
      );

      // TODO Need to write handling for translation already exist case
      if (!this.id) {
        translationService.createTranslation(translation);
      }
    },
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
