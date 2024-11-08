import { createApp } from 'vue'
import { createPinia } from 'pinia';
import App from './App.vue'
import router from './router';
import './assets/css/reset.css';
import {library} from '@fortawesome/fontawesome-svg-core';
import {faCalendar} from "@fortawesome/free-regular-svg-icons/faCalendar";

library.add(faCalendar);

const app = createApp(App);
const pinia = createPinia();

app.use(router);
app.use(pinia); 
app.mount('#app');
