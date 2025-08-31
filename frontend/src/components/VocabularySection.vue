<template>
  <div class="vocabularies-section-wrapper">
    <h3>{{type}} vocabulary ({{total}} items total)</h3>
    <div v-if="loading" class="loading">Loading...</div>
    <div v-else-if="error" class="error">{{error}}</div>
    <table v-else-if="translations && translations.length > 0" class="vocabulary-table">
      <thead>
        <tr>
          <th>English</th>
          <th>Transcription</th>
          <th>Russian</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="translation in translations" :key="translation.id">
          <td>{{translation.text}}</td>
          <td>{{translation.transcription}}</td>
          <td>{{translation.translation}}</td>
        </tr>
      </tbody>
    </table>
    <div v-else class="no-data">No translations found</div>
  </div>
</template>

<script>
import TranslationService from '../services/translations.service';

export default {
  name: 'VocabularySection',
  props: {
    type: String,
    requestType: String,
  },
  data() {
    return {
      translations: [],
      total: 0,
      loading: false,
      error: null,
    }
  },
  created() {
    this.getData()
  },
  methods: {
    getData() {
      this.loading = true;
      this.error = null;
      
      TranslationService.getByType(this.requestType)
          .then(response => {
            console.log('Section got the data:', response);
            if (response && response.data) {
              this.translations = response.data;
              this.total = response.data.length;
            }
          })
          .catch(e => {
            console.error('Error in section:', e);
            this.error = 'Failed to load translations';
          })
          .finally(() => {
            this.loading = false;
          });
    }
  }
}
</script>

<style scoped>
.vocabulary-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.vocabulary-table th,
.vocabulary-table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

.vocabulary-table th {
  background-color: #f2f2f2;
  font-weight: bold;
}

.loading, .error, .no-data {
  text-align: center;
  padding: 20px;
  color: #666;
}

.error {
  color: #d32f2f;
}
</style>
