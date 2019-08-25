// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
Vue.config.productionTip = false
import vueResource from 'vue-resource'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
Vue.use(vueResource)
Vue.use(Vuetify)
/* eslint-disable no-new */
new Vue({
  el: '#app',
  vuetify: new Vuetify(),
  router,
  components: { App },
  template: '<App/>'
})
export default new Vuetify({})