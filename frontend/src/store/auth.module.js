import AuthService from '../services/auth.service';

const user = JSON.parse(localStorage.getItem('user'));
const initialState = user
    ? { status: {loggedIn: true, refreshAttempt: false}, user: user}
    : { status: {loggedIn: false, refreshAttempt: false}, user: null };

export default  {
    namespaced: true,
    state: initialState,
    actions: {
        login({ commit }, user) {
            return AuthService.login(user).then(
                user => {
                    commit('loginSuccess', user);
                    return Promise.resolve(user);
                },
                error => {
                    console.log("Error: " + error);
                    commit('loginFailure');
                    return Promise.reject(error);
                }
            );
        },
        logout({ commit }) {
            AuthService.logout();
            commit('logout');
        },
        register({ commit }, user) {
            return AuthService.register(user).then(
                response => {
                    commit('registerSuccess');
                    return Promise.resolve(response.data);
                },
                error => {
                    commit('registerFailure');
                    return Promise.reject(error);
                }
            );
        }
    },
    mutations: {
        loginSuccess(state, user) {
            state.status.loggedIn = true;
            state.user = user;
        },
        refreshSuccess(state, user) {
            state.status.loggedIn = true;
            state.status.refreshAttempt = true;
            state.user = user;
        },
        loginFailure(state) {
            state.status.loggedIn = false;
            state.user = null;
        },
        refreshFailure(state) {
            state.status.refreshAttempt = true;
            state.user = null;
        },
        logout(state) {
            state.status.loggedIn = false;
            state.user = null;
        }
        // registerSuccess(state) {
        // },
        // registerFailure(state) {
        // }
    },
    getters: {
        isLoggedIn: function (state) {
            if (state.status.loggedIn) {
                return true;
            }

            if (!state.status.refreshAttempt) {
                AuthService.refresh().then(user => {
                        commit('loginSuccess', user);
                        return Promise.resolve(user);
                    },
                    error => {
                        console.log("Error: " + error);
                        commit('loginFailure');
                        return Promise.reject(error);
                    }
                )
            }
        },
        user: state => state.user,
        hasLoginErrored: state => state.loginError
    }
};