import Home from './src/views/Home.vue';
import Running from './src/views/Running.vue';
import Message from './src/views/Message.vue';

const routes = [
    { path: '/', component: Home },
    { path: '/running', component: Running },
    { path: '/msg', component: Message },

];


export default routes;
