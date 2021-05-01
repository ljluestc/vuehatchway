import axios from 'axios';
import authHeader from './auth-header';

const axios = axios.create({
    baseURL: '/api/v1/translation',
    timeout: 1000,
    headers: {'Content-Type': 'application/json'}
});

class TranslationsService {
    getPhrases() {
        return axios.get("phrases", {headers: authHeader()});
    }

    getPhrasalVerbs() {
        return axios.get("phrasal-verbs", {headers: authHeader()});
    }

    getWords() {
        return axios.get("words", {headers: authHeader()});
    }
}

export default new TranslationsService();