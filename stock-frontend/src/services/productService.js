import api from './api';

export const getProducts = async () => {
    const response = await api.get('/products');
    return response.data;
};

export const getProductsByCategory = async (categoryId) => {
    const response = await api.get(`/products/category/${categoryId}`);
    return response.data;
};

export const getProduct = async (id) => {
    const response = await api.get(`/products/${id}`);
    return response.data;
};

export const createProduct = async (productData) => {
    const response = await api.post('/products', productData);
    return response.data;
};

export const updateProduct = async (id, productData) => {
    const response = await api.put(`/products/${id}`, productData);
    return response.data;
};

export const deleteProduct = async (id) => {
    await api.delete(`/products/${id}`);
};