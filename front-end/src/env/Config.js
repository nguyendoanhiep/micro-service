import axios from "axios";
import {toast} from "react-toastify";

export const baseUrl = {
    host: 'http://localhost:',
    port: 8888,
    path: '/api/v1'
};

const axiosInstance = axios.create({
    baseURL: baseUrl.host + baseUrl.port + baseUrl.path,
    headers: {
        "Content-Type": "application/json",
        "Accept": "application/json",
    },
});
axiosInstance.interceptors.request.use(
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
        toast.error('Lỗi Hệ Thống!', {
            className: 'my-toast',
            position: "top-center",
            autoClose: 2000,
        });
        return Promise.reject(error);
    }
);
axiosInstance.interceptors.response.use(
    (response) => {
        if (response.data.code === 403) {
            return  toast.error('Bạn không có quyền truy cập!', {
                className: 'my-toast',
                position: "top-center",
                autoClose: 2000,
            });
        }
        if (response.data.code === 401) {
            return toast.error('Chưa xác thực!', {
                className: 'my-toast',
                position: "top-center",
                autoClose: 2000,
            });
        }else {
            return response;
        }
    },
    (error) => {
        toast.error("Lỗi Hệ Thống!", {
            className: "my-toast",
            position: "top-center",
            autoClose: 2000,
        });
        return Promise.reject(error);
    }
);
export default axiosInstance;
