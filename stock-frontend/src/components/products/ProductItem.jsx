import {
    TableRow,
    TableCell,
    IconButton,
    useMediaQuery,
    Box,
    Typography
} from '@mui/material';
import { Edit, Delete } from '@mui/icons-material';

const ProductItem = ({ product, onEdit, onDelete, isMobile }) => {
    return (
        <TableRow hover>
            <TableCell>
                <Box fontWeight="medium">
                    {product.name}
                </Box>
                {isMobile && (
                    <Box sx={{ color: 'text.secondary', fontSize: '0.8rem', mt: 0.5 }}>
                        <div>{product.description}</div>
                        <div>Price: {product.price}</div>
                        <div>Qty: {product.quantity}</div>
                        <div>Category: {product.categoryName}</div>
                    </Box>
                )}
            </TableCell>

            {!isMobile && (
                <>
                    <TableCell>{product.description}</TableCell>
                    <TableCell align="right">{product.price}</TableCell>
                    <TableCell align="right">{product.quantity}</TableCell>
                    <TableCell>{product.categoryName}</TableCell>
                </>
            )}

            <TableCell align="center">
                <IconButton
                    onClick={() => onEdit(product)}
                    size="small"
                    color="primary"
                >
                    <Edit fontSize={isMobile ? 'small' : 'medium'} />
                </IconButton>
                <IconButton
                    onClick={() => onDelete(product.id)}
                    size="small"
                    color="error"
                >
                    <Delete fontSize={isMobile ? 'small' : 'medium'} />
                </IconButton>
            </TableCell>
        </TableRow>
    );
};

export default ProductItem;