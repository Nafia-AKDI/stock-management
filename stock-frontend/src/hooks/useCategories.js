import { useState, useEffect } from 'react';
import {
    getCategories,
    createCategory,
    updateCategory,
    deleteCategory } from '../services/categoryService';

export const useCategories = () => {
    const [categories, setCategories] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const fetchCategories = async () => {
        setLoading(true);
        try {
            const data = await getCategories();
            setCategories(data);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchCategories();
    }, []);

    const addCategory = async (categoryData) => {
        try {
            const newCategory = await createCategory(categoryData);
            setCategories([...categories, newCategory]);
            return newCategory;
        } catch (err) {
            throw err;
        }
    };

    const editCategory = async (id, categoryData) => {
        try {
            const updatedCategory = await updateCategory(id, categoryData);
            setCategories(categories.map(cat =>
                cat.id === id ? updatedCategory : cat
            ));
            return updatedCategory;
        } catch (err) {
            throw err;
        }
    };

    const removeCategory = async (id) => {
        try {
            await deleteCategory(id);
            setCategories(categories.filter(cat => cat.id !== id));
        } catch (err) {
            throw err;
        }
    };

    return {
        categories,
        loading,
        error,
        addCategory,
        editCategory,
        removeCategory,
        fetchCategories
    };
};