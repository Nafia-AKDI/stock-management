import { useState, useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { Button, TextField, Dialog, DialogActions, DialogContent, DialogTitle, MenuItem } from '@mui/material';
import { useCategories } from '../../hooks/useCategories';

const ProductForm = ({ open, onClose, onSubmit, initialData }) => {
    const { register, handleSubmit, reset, formState: { errors } } = useForm({
        defaultValues: initialData || {}
    });
    const { categories } = useCategories();
    const [selectedCategory, setSelectedCategory] = useState('');

    // Reset form and selected category when initialData changes
    useEffect(() => {
        if (initialData) {
            reset(initialData);
            setSelectedCategory(initialData.categoryId || '');
        } else {
            reset({});
            setSelectedCategory('');
        }
    }, [initialData, reset]);

    const handleClose = () => {
        reset();
        setSelectedCategory('');
        onClose();
    };

    const submitHandler = (data) => {
        onSubmit({ ...data, categoryId: selectedCategory });
        handleClose();
    };

    return (
        <Dialog open={open} onClose={handleClose}>
            <DialogTitle>{initialData ? 'Edit Product' : 'New Product'}</DialogTitle>
            <DialogContent>
                <TextField
                    autoFocus
                    margin="dense"
                    label="Name"
                    fullWidth
                    variant="standard"
                    {...register('name', { required: 'Name is required' })}
                    error={!!errors.name}
                    helperText={errors.name?.message}
                />
                <TextField
                    margin="dense"
                    label="Description"
                    fullWidth
                    variant="standard"
                    {...register('description')}
                />
                <TextField
                    margin="dense"
                    label="Price"
                    fullWidth
                    variant="standard"
                    type="number"
                    {...register('price', {
                        required: 'Price is required',
                        min: { value: 0, message: 'Price must be positive' }
                    })}
                    error={!!errors.price}
                    helperText={errors.price?.message}
                />
                <TextField
                    margin="dense"
                    label="Quantity"
                    fullWidth
                    variant="standard"
                    type="number"
                    {...register('quantity', {
                        required: 'Quantity is required',
                        min: { value: 0, message: 'Quantity must be positive' }
                    })}
                    error={!!errors.quantity}
                    helperText={errors.quantity?.message}
                />
                <TextField
                    select
                    margin="dense"
                    label="Category"
                    fullWidth
                    variant="standard"
                    value={selectedCategory}
                    onChange={(e) => setSelectedCategory(e.target.value)}
                    error={!selectedCategory && !!initialData}
                    helperText={!selectedCategory && !!initialData ? 'Category is required' : ''}
                >
                    {categories.map((category) => (
                        <MenuItem key={category.id} value={category.id}>
                            {category.name}
                        </MenuItem>
                    ))}
                </TextField>
            </DialogContent>
            <DialogActions>
                <Button onClick={handleClose}>Cancel</Button>
                <Button onClick={handleSubmit(submitHandler)}>Save</Button>
            </DialogActions>
        </Dialog>
    );
};

export default ProductForm;