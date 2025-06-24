import { createContext, useContext, useState, useEffect } from 'react';
import { login as authLogin, logout as authLogout } from '../services/authService';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        const token = localStorage.getItem('authToken');
        const expiration = localStorage.getItem('tokenExpiration');

        if (token && expiration && new Date(expiration) > new Date()) {
            setIsAuthenticated(true);
        } else {
            authLogout();
        }
        setIsLoading(false);
    }, []);

    const login = async (credentials) => {
        try {
            const { authToken, expiresIn } = await authLogin(credentials);
            localStorage.setItem('authToken', authToken);
            localStorage.setItem('tokenExpiration', expiresIn);
            setIsAuthenticated(true);
            return true;
        } catch (error) {
            console.error('Login failed:', error);
            return false;
        }
    };

    const logout = () => {
        authLogout();
        setIsAuthenticated(false);
    };

    return (
        <AuthContext.Provider value={{ isAuthenticated, isLoading, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);