import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'
import routes from './routes';
import Vuex from 'vuex';
import Chat from 'vue-beautiful-chat'
Vue.use(Chat)
Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    user: {},
    role: "NOT_LOGGED"
  },
  mutations: {
    login: function (state, data) {
      state.user = data.user;
      state.role = data.role;
    }
  },
  getters: {
  }
});

Vue.use(VueRouter)
const router = new VueRouter({routes});

new Vue({
  router,
  store,
  el: '#app',
  render: h => h(App),
}).$mount("#app")
