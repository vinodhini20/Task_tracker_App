import axios from 'axios';

const API_URL = 'http://localhost:8080/api';

const apiClient = axios.create({
    baseURL: API_URL,
});

export const getTasks = (filters) => {
    return apiClient.get('/tasks', {
        params: {
            page: filters.page,
            size: filters.size,
            sort: filters.sort,
            status: filters.status || null,
            priority: filters.priority || null,
        },
    });
};

export const getTask = (id) => {
    return apiClient.get(`/tasks/${id}`);
};

export const createTask = (taskData) => {
    const params = {};

    if (taskData.projectId) {
        params.projectId = taskData.projectId;
    }

    return apiClient.post('/tasks', taskData, { params });
};

export const updateTask = (id, taskData) => {
    const params = {};

    if (taskData.projectId) {
        params.projectId = taskData.projectId;
    }

    return apiClient.put(`/tasks/${id}`, taskData, { params });
};

export const deleteTask = (id) => {
    return apiClient.delete(`/tasks/${id}`);
};

export const getProjects = () => {
    return apiClient.get('/projects');
};