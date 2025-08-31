import axios from 'axios';

class TranslationsService {
    getPhrases() {
        return axios.get("/api/translation/phrases");
    }

    getPhrasalVerbs() {
        return axios.get("/api/translation/phrasal-verbs");
    }

    getWords() {
        return axios.get("/api/translation/words");
    }

    getByType(type) {
        return axios.get('/api/translation/' + type);
    }

    getAllTranslations() {
        return axios.get('/api/translation/all');
    }
}

export default new TranslationsService();