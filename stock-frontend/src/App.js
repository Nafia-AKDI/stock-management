import './App.css';
import {AuthProvider} from "./context/AuthContext";
import {BrowserRouter} from "react-router-dom";
import AppRoutes from './AppRoutes';
import Header from "./components/common/Header";

function App() {
    return (
        <BrowserRouter>
            <AuthProvider>
                <Header />
                <AppRoutes />
            </AuthProvider>
        </BrowserRouter>
    );
}

export default App;