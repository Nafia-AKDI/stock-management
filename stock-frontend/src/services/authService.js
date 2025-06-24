import api from './api';

export const login = async (credentials) => {
    const response = await api.post('/login', credentials);
    return response.data;
};

export const logout = () => {
    localStorage.removeItem('authToken');
    localStorage.removeItem('tokenExpiration');
};