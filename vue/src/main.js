import { createApp } from 'vue'
import { createPinia } from 'pinia'
import 'normalize.css/normalize.css'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'


import App from './App.vue'
import router from './router'
import instance from './axios'

const pinia = createPinia()

const app = createApp(App)

app.provide('$axios', instance)
app.use(pinia).use(ElementPlus).use(router).mount('#app')
