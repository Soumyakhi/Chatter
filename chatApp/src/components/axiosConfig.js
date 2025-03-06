// axiosConfig.js

import axios from 'axios';

// Create an Axios instance to handle API requests
const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080', // The base URL of your Spring Boot API
    headers: {
        'Content-Type': 'application/json', // Default header for JSON
    },
});

// Optionally, you can intercept requests or responses for things like authorization token
axiosInstance.interceptors.request.use((config) => {
    // Add JWT token from localStorage if available
    const token = localStorage.getItem('jwt');
    if (token) {
        config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});

export default axiosInstance;
