import Home from './src/views/Home.vue';
import Login from './src/views/Login.vue';
import Logout from './src/views/Logout.vue';
import Registration from  "./src/views/Registration.vue";
import UserDetail from "./src/views/UserDetail.vue";
import Profile from "./src/views/Profile.vue";
import UserList from "./src/views/UserList.vue";

const routes = [
    { path: '/', component: Home },
    { path: '/login', component: Login },
    { path: '/logout', component: Logout },
    { path: '/registration', component: Registration },
    { path: '/profile', component: Profile},
    { path: '/users/:id', component: UserDetail, props: true},
    { path: '/users/', component: UserList},
];


export default routes;
