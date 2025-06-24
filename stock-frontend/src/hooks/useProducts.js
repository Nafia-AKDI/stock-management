import { useState, useEffect } from 'react';
import {
    getProducts,
    createProduct,
    updateProduct,
    deleteProduct,
    getProductsByCategory } from '../services/productService';

export const useProducts = () => {
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const fetchProducts = async () => {
        setLoading(true);
        try {
            const data = await getProducts();
            setProducts(data);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchProducts();
    }, []);

    const addProduct = async (productData) => {
        try {
            const newProduct = await createProduct(productData);
            setProducts([...products, newProduct]);
            return newProduct;
        } catch (err) {
            throw err;
        }
    };

    const editProduct = async (id, productData) => {
        try {
            const updatedProduct = await updateProduct(id, productData);
            setProducts(products.map(prod =>
                prod.id === id ? updatedProduct : prod
            ));
            return updatedProduct;
        } catch (err) {
            throw err;
        }
    };

    const removeProduct = async (id) => {
        try {
            await deleteProduct(id);
            setProducts(products.filter(prod => prod.id !== id));
        } catch (err) {
            throw err;
        }
    };

    const fetchByCategory = async (categoryId) => {
        setLoading(true);
        try {
            const data = await getProductsByCategory(categoryId);
            return data;
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    return {
        products,
        loading,
        error,
        addProduct,
        editProduct,
        removeProduct,
        fetchProducts,
        fetchByCategory
    };
};