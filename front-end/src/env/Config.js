import axios from "axios";

export const baseUrl = {
    host: 'http://localhost:',
    port: 8888,
    path:'/api/v1'
};
export const finalUrl = baseUrl.host + baseUrl.port + baseUrl.path
export const api = axios.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem("Authorization");
        if (token) {
            config.headers["Authorization"] = JSON.parse(token);
        }
        config.headers["Content-Type"] = "application/json";
        config.headers["Accept"] = "application/json";
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);