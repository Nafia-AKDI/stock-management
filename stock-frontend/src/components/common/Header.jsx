import { useState } from 'react';
import {
    AppBar,
    Toolbar,
    Typography,
    Button,
    IconButton,
    Menu,
    MenuItem,
    useMediaQuery,
    Box
} from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import { useAuth } from '../../context/AuthContext';
import { Link } from 'react-router-dom';

const Header = () => {
    const { isAuthenticated, logout } = useAuth();
    const [anchorEl, setAnchorEl] = useState(null);
    const isMobile = useMediaQuery('(max-width:600px)');

    const handleMenuOpen = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleMenuClose = () => {
        setAnchorEl(null);
    };

    return (
        <AppBar position="static">
            <Toolbar>
                <Typography
                    variant="h6"
                    component={Link}
                    to="/"
                    sx={{
                        flexGrow: isMobile ? 0 : 1,
                        color: 'inherit',
                        textDecoration: 'none',
                        mr: 2
                    }}
                >
                    Stock Management
                </Typography>

                {isAuthenticated && (
                    <>
                        {isMobile ? (
                            <>
                                <Box sx={{ flexGrow: 1 }} />
                                <IconButton
                                    color="inherit"
                                    onClick={handleMenuOpen}
                                    sx={{ ml: 'auto' }}
                                >
                                    <MenuIcon />
                                </IconButton>
                                <Menu
                                    anchorEl={anchorEl}
                                    open={Boolean(anchorEl)}
                                    onClose={handleMenuClose}
                                >
                                    <MenuItem
                                        component={Link}
                                        to="/categories"
                                        onClick={handleMenuClose}
                                    >
                                        Categories
                                    </MenuItem>
                                    <MenuItem
                                        component={Link}
                                        to="/products"
                                        onClick={handleMenuClose}
                                    >
                                        Products
                                    </MenuItem>
                                    <MenuItem onClick={() => {
                                        handleMenuClose();
                                        logout();
                                    }}>
                                        Logout
                                    </MenuItem>
                                </Menu>
                            </>
                        ) : (
                            <>
                                <Button
                                    color="inherit"
                                    component={Link}
                                    to="/categories"
                                    sx={{ ml: 'auto' }}
                                >
                                    Categories
                                </Button>
                                <Button
                                    color="inherit"
                                    component={Link}
                                    to="/products"
                                >
                                    Products
                                </Button>
                                <Button
                                    color="inherit"
                                    onClick={logout}
                                >
                                    Logout
                                </Button>
                            </>
                        )}
                    </>
                )}
            </Toolbar>
        </AppBar>
    );
};

export default Header;