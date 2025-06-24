import { render, screen, fireEvent } from '@testing-library/react';
import ProductItem from './ProductItem';

describe('ProductItem Component', () => {
    const mockProduct = {
        id: '1',
        name: 'Smartphone',
        description: 'Latest model',
        price: 699.99,
        quantity: 15,
        categoryName: 'Electronics'
    };

    const mockOnEdit = jest.fn();
    const mockOnDelete = jest.fn();

    it('should display product information correctly', () => {
        render(
            <ProductItem
                product={mockProduct}
                onEdit={mockOnEdit}
                onDelete={mockOnDelete}
            />
        );

        expect(screen.getByText('Smartphone')).toBeInTheDocument();
        expect(screen.getByText('Latest model')).toBeInTheDocument();
        expect(screen.getByText('699.99')).toBeInTheDocument(); // Modifié ici
        expect(screen.getByText('15')).toBeInTheDocument();
        expect(screen.getByText('Electronics')).toBeInTheDocument();
    });

    it('should call onEdit when edit button clicked', () => {
        render(
            <ProductItem
                product={mockProduct}
                onEdit={mockOnEdit}
                onDelete={mockOnDelete}
            />
        );

        fireEvent.click(screen.getByTestId('EditIcon')); // Modifié pour utiliser data-testid
        expect(mockOnEdit).toHaveBeenCalledWith(mockProduct);
    });

    it('should call onDelete when delete button clicked', () => {
        render(
            <ProductItem
                product={mockProduct}
                onEdit={mockOnEdit}
                onDelete={mockOnDelete}
            />
        );

        fireEvent.click(screen.getByTestId('DeleteIcon')); // Modifié pour utiliser data-testid
        expect(mockOnDelete).toHaveBeenCalledWith('1');
    });
});