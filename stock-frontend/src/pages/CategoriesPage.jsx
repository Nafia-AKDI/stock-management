import { useState } from 'react';
import {
    Container,
    Button,
    Box,
    Typography,
    CircularProgress,
    Alert,
    Stack
} from '@mui/material';
import CategoryList from '../components/categories/CategoryList';
import CategoryForm from '../components/categories/CategoryForm';
import { useCategories } from '../hooks/useCategories';

const CategoriesPage = () => {
    const {
        categories,
        loading,
        error,
        addCategory,
        editCategory,
        removeCategory
    } = useCategories();

    const [openForm, setOpenForm] = useState(false);
    const [currentCategory, setCurrentCategory] = useState(null);

    const handleAdd = () => {
        setCurrentCategory(null);
        setOpenForm(true);
    };

    const handleEdit = (category) => {
        setCurrentCategory(category);
        setOpenForm(true);
    };

    const handleSubmit = async (categoryData) => {
        try {
            if (currentCategory) {
                await editCategory(currentCategory.id, categoryData);
            } else {
                await addCategory(categoryData);
            }
        } catch (err) {
            console.error("Error during operation:", err);
        }
    };

    return (
        <Container maxWidth="lg" sx={{ py: 4 }}>
            <Stack spacing={3}>
                {/* Header with button */}
                <Box
                    sx={{
                        display: 'flex',
                        justifyContent: 'space-between',
                        alignItems: 'center',
                        flexWrap: 'wrap',
                        gap: 2
                    }}
                >
                    <Typography variant="h4" component="h1">
                        Category Management
                    </Typography>
                    <Button
                        variant="contained"
                        onClick={handleAdd}
                        sx={{ minWidth: 180 }}
                    >
                        + Add Category
                    </Button>
                </Box>

                {/* Status messages */}
                {loading && (
                    <Box display="flex" justifyContent="center">
                        <CircularProgress />
                    </Box>
                )}

                {error && (
                    <Alert severity="error" sx={{ mb: 2 }}>
                        {error}
                    </Alert>
                )}

                {/* Categories list */}
                <Box sx={{
                    boxShadow: 3,
                    borderRadius: 2,
                    overflow: 'hidden'
                }}>
                    <CategoryList
                        categories={categories}
                        onEdit={handleEdit}
                        onDelete={removeCategory}
                    />
                </Box>
            </Stack>

            {/* Modal form */}
            <CategoryForm
                open={openForm}
                onClose={() => setOpenForm(false)}
                onSubmit={handleSubmit}
                initialData={currentCategory}
            />
        </Container>
    );
};

export default CategoriesPage;