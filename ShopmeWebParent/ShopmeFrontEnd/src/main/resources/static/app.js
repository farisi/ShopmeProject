import Vue from "vue";
import Router from "vue-router";
import PrimeVue from "primevue/config";

Vue.use(Router);
PrimeVue.install(Vue);

const routes = [
    {
        path: "/",
        name: "Home",
        component: () => import("./components/Home.vue")
    },
    {
        path: "/about",
        name: "About",
        component: () => import("./components/About.vue")
    }
];

const router = new Router({
    routes
});

const app = new Vue({
    el: "#app",
    router
});