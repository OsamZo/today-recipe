import { createApp } from 'vue'
import App from './App.vue'
import router from './router';
import './assets/css/reset.css';
import {library} from '@fortawesome/fontawesome-svg-core';
import {faCalendar} from "@fortawesome/free-regular-svg-icons/faCalendar";

library.add(faCalendar);

const app = createApp(App);
app.use(router); 
app.mount('#app');
