import { useState } from 'react';
import {
    Container,
    Button,
    Box,
    Typography,
    CircularProgress,
    Alert
} from '@mui/material';
import ProductList from '../components/products/ProductList';
import ProductForm from '../components/products/ProductForm';
import { useProducts } from '../hooks/useProducts';

const ProductsPage = () => {
    const { products, loading, error, addProduct, editProduct, removeProduct } = useProducts();
    const [openForm, setOpenForm] = useState(false);
    const [currentProduct, setCurrentProduct] = useState(null);

    const handleAdd = () => {
        setCurrentProduct(null);
        setOpenForm(true);
    };

    const handleEdit = (product) => {
        setCurrentProduct(product);
        setOpenForm(true);
    };

    const handleSubmit = async (productData) => {
        if (currentProduct) {
            await editProduct(currentProduct.id, productData);
        } else {
            await addProduct(productData);
        }
    };

    return (
        <Container maxWidth="lg" sx={{ py: 3 }}>
            {/* Enhanced header */}
            <Box sx={{
                display: 'flex',
                justifyContent: 'space-between',
                alignItems: 'center',
                mb: 3,
                gap: 2
            }}>
                <Typography variant="h4" component="h1">
                    Products
                </Typography>
                <Button
                    variant="contained"
                    onClick={handleAdd}
                    size="large"
                >
                    + Add Product
                </Button>
            </Box>

            {/* Enhanced loading and error states */}
            {loading && (
                <Box display="flex" justifyContent="center" py={4}>
                    <CircularProgress />
                </Box>
            )}

            {error && (
                <Alert severity="error" sx={{ mb: 3 }}>
                    {error}
                </Alert>
            )}

            {/* Product list with subtle shadow */}
            <Box sx={{ boxShadow: 1, borderRadius: 1 }}>
                <ProductList
                    products={products}
                    onEdit={handleEdit}
                    onDelete={removeProduct}
                />
            </Box>

            {/* Modal form (unchanged) */}
            <ProductForm
                open={openForm}
                onClose={() => setOpenForm(false)}
                onSubmit={handleSubmit}
                initialData={currentProduct}
            />
        </Container>
    );
};

export default ProductsPage;