import {
    Container,
    Typography,
    Grid,
    Card,
    CardContent,
    CardActionArea,
    Box
} from '@mui/material';
import { Link } from 'react-router-dom';

const DashboardPage = () => {
    return (
        <Container maxWidth="lg" sx={{ mt: 4 }}>
            <Typography variant="h4" gutterBottom sx={{ mb: 4 }}>
                Dashboard
            </Typography>

            <Grid container spacing={4}>
                {/* Categories Card */}
                <Grid item xs={12} sm={6} md={4}>
                    <Card elevation={3}>
                        <CardActionArea
                            component={Link}
                            to="/categories"
                            sx={{ height: '100%' }}
                        >
                            <CardContent sx={{ textAlign: 'center' }}>
                                <Box sx={{ fontSize: 72, mb: 2 }}>📋</Box>
                                <Typography variant="h5" component="div">
                                    Category Management
                                </Typography>
                                <Typography variant="body2" color="text.secondary" sx={{ mt: 2 }}>
                                    View and manage your product categories
                                </Typography>
                            </CardContent>
                        </CardActionArea>
                    </Card>
                </Grid>

                {/* Products Card */}
                <Grid item xs={12} sm={6} md={4}>
                    <Card elevation={3}>
                        <CardActionArea
                            component={Link}
                            to="/products"
                            sx={{ height: '100%' }}
                        >
                            <CardContent sx={{ textAlign: 'center' }}>
                                <Box sx={{ fontSize: 72, mb: 2 }}>📦</Box>
                                <Typography variant="h5" component="div">
                                    Product Management
                                </Typography>
                                <Typography variant="body2" color="text.secondary" sx={{ mt: 2 }}>
                                    View and manage your product inventory
                                </Typography>
                            </CardContent>
                        </CardActionArea>
                    </Card>
                </Grid>
            </Grid>

            {/* Summary Section */}
            <Box sx={{ mt: 6 }}>
                <Typography variant="h5" gutterBottom>
                    Overview
                </Typography>
                <Typography paragraph>
                    Welcome to your stock management application.
                    Use the cards above to quickly access different sections.
                </Typography>
            </Box>
        </Container>
    );
};

export default DashboardPage;