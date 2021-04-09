<template>
  <div class="vocabularies-section-wrapper">
    <p>{{type}} vocabulary ({{total}} items total)</p>
    <table>
      <thead>
      <tr>
        <th>{{type}}</th>
        <th>Transcription</th>
        <th>Translation</th>
      </tr>
      </thead>
    </table>
  </div>
</template>

<script>

export default {
  name: 'VocabularySection',
  props: {
    type: String,
    srcData: String,
    srcCount: String,
  },
  data() {
    return {
      transactions: null,
      total: 82,
      errors: null,
    }
  },
  created() {
    this.getData()
  },
  methods: {
    getData() {
      this.$http.get(this.srcData)
          .then(response => {
            console.log(response)
            this.response = response.data
          })
          .catch(e => {
            this.errors.push(e)
            console.log(this.errors);
          });
    }
  }
}
</script>
