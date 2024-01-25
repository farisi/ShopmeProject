import Vue from 'vue';
import PrimeVue from 'primevue/config';

Vue.use(PrimeVue);

export default {
    name: 'Home',
    template: `
        <p>Home</p>
        <primevue-datatable :data="data">
            <p-column field="name" header="Name"></p-column>
            <p-column field="email" header="Email"></p-column>
        </primevue-datatable>
    `,
    data() {
        return {
            data: [
                {
                    name: 'John Doe',
                    email: 'johndoe@example.com'
                },
                {
                    name: 'Jane Doe',
                    email: 'janedoe@example.com'
                }
            ]
        };
    }
};