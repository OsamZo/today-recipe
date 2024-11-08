import { createApp } from 'vue'
import { createPinia } from 'pinia';
import App from './App.vue'
import router from './router';
import './assets/css/reset.css';
import {library} from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import {faCalendar} from "@fortawesome/free-regular-svg-icons/faCalendar";
import { faBookmark } from '@fortawesome/free-solid-svg-icons';

library.add(faCalendar);
library.add(faBookmark);

const app = createApp(App);
const pinia = createPinia();

app.use(router);
app.component('font-awesome-icon', FontAwesomeIcon);
app.use(pinia); 
app.mount('#app');
