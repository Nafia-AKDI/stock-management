import {Container, CssBaseline, Paper, Box, Typography} from '@mui/material';
import LoginForm from '../components/auth/LoginForm';

const LoginPage = () => {
    return (
        <Container component="main" maxWidth="xs">
            <CssBaseline />
            <Box
                sx={{
                    marginTop: 8,
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                }}
            >
                <Paper elevation={3} sx={{ p: 4, width: '100%' }}>
                    <Typography component="h1" variant="h5" align="center" sx={{ mb: 3 }}>
                        Login
                    </Typography>
                    <LoginForm />
                </Paper>
            </Box>
        </Container>
    );
};

export default LoginPage;