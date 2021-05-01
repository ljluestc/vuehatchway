import axios from 'axios';

const axiosAuth = axios.create({
    baseURL: '/api/v1/auth',
    timeout: 1000,
    headers: {'Content-Type': 'application/json'},

});

class AuthService {
    login(user) {
        return axiosAuth
            .post('signin', {
                    username: user.username,
                    password: user.password
                },
                {withCredentials: true}
            )
            .then(response => {
                console.log(response);
                if (response.data.token) {
                    localStorage.setItem('user', JSON.stringify(response.data));
                }
                return response.data;
            });
    }

    refresh() {
        return axiosAuth
            .post('refresh', {}, {withCredentials: true})
            .then(response => {
                console.log(response);
                if (response.data && response.data.token) {
                    localStorage.setItem('user', JSON.stringify(response.data));
                }
                return response.data;
            });
    }

    logout() {
        localStorage.removeItem('user');
    }

    register(user) {
        return axios.post('signup', {
            username: user.username,
            email: user.email,
            password: user.password
        });
    }
}

export default new AuthService();