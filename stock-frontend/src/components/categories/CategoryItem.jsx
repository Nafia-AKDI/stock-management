import { TableRow, TableCell, IconButton } from '@mui/material';
import { Edit, Delete } from '@mui/icons-material';

const CategoryItem = ({ category, onEdit, onDelete }) => {
    return (
        <TableRow>
            <TableCell>{category.name}</TableCell>
            <TableCell>{category.description}</TableCell>
            <TableCell>{category.productCount || 0}</TableCell>
            <TableCell>
                <IconButton onClick={() => onEdit(category)}>
                    <Edit />
                </IconButton>
                <IconButton onClick={() => onDelete(category.id)}>
                    <Delete />
                </IconButton>
            </TableCell>
        </TableRow>
    );
};

export default CategoryItem;