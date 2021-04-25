import axios from 'axios'

const axiosApi = axios.create({
    baseURL: '/api/v1/translation',
    timeout: 1000,
    headers: {'Content-Type': 'application/json'}
});

export default {
    getSecured(user, password) {
        return axiosApi.get('/secured/', {
            auth: {
                username: user,
                password: password
            }
        });
    }
}