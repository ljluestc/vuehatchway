import Vue from 'vue'
import Vuex from 'vuex'
import api from '../api/backend-api'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        loginSuccess: false,
        loginError: false,
        userName: null
    },
    mutations: {
        login_success(state, name){
            state.loginSuccess = true
            state.userName = name
        },
        login_error(state){
            state.loginError = true
            state.userName = name
        }
    },
    actions: {
        async login({commit}, payload) {
            api.getSecured(payload.user, payload.password)
                .then(response => {
                    console.log("Response: '" + response.data + "' with Status code " + response.status);
                    console.log(response);
                    if (response.status == 200) {
                        return commit('login_success', name);
                    }
                }).catch(error => {
                console.log("Error: " + error);
                // place the loginError state into our vuex store
                commit('login_error', name);
                return Promise.reject("Invalid credentials!")
            })
        }
    },
    getters: {
        isLoggedIn: state => state.loginSuccess,
        hasLoginErrored: state => state.loginError
    }
})
