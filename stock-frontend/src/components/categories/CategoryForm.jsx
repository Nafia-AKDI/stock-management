import { useEffect } from 'react';
import { useForm } from 'react-hook-form';
import { Button, TextField, Dialog, DialogActions, DialogContent, DialogTitle } from '@mui/material';

const CategoryForm = ({ open, onClose, onSubmit, initialData }) => {
    const { register, handleSubmit, reset, formState: { errors } } = useForm({
        defaultValues: initialData || {}
    });

    // Reset form when initialData changes
    useEffect(() => {
        reset(initialData || {});
    }, [initialData, reset]);

    const handleClose = () => {
        reset();
        onClose();
    };

    const submitHandler = (data) => {
        onSubmit(data);
        handleClose();
    };

    return (
        <Dialog open={open} onClose={handleClose}>
            <DialogTitle>{initialData ? 'Edit Category' : 'New Category'}</DialogTitle>
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
            </DialogContent>
            <DialogActions>
                <Button onClick={handleClose}>Cancel</Button>
                <Button onClick={handleSubmit(submitHandler)}>Save</Button>
            </DialogActions>
        </Dialog>
    );
};

export default CategoryForm;