import {
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    IconButton,
    Typography,
    useMediaQuery,
    Box,
    TablePagination
} from '@mui/material';
import { Edit, Delete } from '@mui/icons-material';
import ProductItem from './ProductItem';
import { useState } from 'react';

const ProductList = ({ products, onEdit, onDelete }) => {
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(5);
    const isMobile = useMediaQuery('(max-width:600px)');

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    if (products.length === 0) {
        return (
            <Box textAlign="center" p={4}>
                <Typography variant="subtitle1" color="textSecondary">
                    No products found
                </Typography>
            </Box>
        );
    }

    // Slice products for pagination
    const displayedProducts = products.slice(
        page * rowsPerPage,
        page * rowsPerPage + rowsPerPage
    );

    return (
        <Paper elevation={2} sx={{ overflow: 'hidden' }}>
            <TableContainer>
                <Table size={isMobile ? 'small' : 'medium'}>
                    <TableHead>
                        <TableRow sx={{ bgcolor: 'background.default' }}>
                            <TableCell>Name</TableCell>
                            {!isMobile && (
                                <>
                                    <TableCell>Description</TableCell>
                                    <TableCell align="right">Price</TableCell>
                                    <TableCell align="right">Qty</TableCell>
                                    <TableCell>Category</TableCell>
                                </>
                            )}
                            <TableCell align="center">Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {displayedProducts.map((product) => (
                            <ProductItem
                                key={product.id}
                                product={product}
                                onEdit={onEdit}
                                onDelete={onDelete}
                                isMobile={isMobile}
                            />
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

            {/* Pagination */}
            <TablePagination
                rowsPerPageOptions={[5, 10, 25]}
                component="div"
                count={products.length}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
            />
        </Paper>
    );
};

export default ProductList;