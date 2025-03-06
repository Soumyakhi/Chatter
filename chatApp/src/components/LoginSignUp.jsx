import React, { useState, useRef } from 'react';
import axiosInstance from './axiosConfig';
import { useNavigate } from 'react-router-dom';
import { Alert, Button } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

function CustomAlert({ message, variant, onClose }) {
    return (
        <Alert variant={variant} onClose={onClose} dismissible className="position-fixed top-0 end-0 m-3">
            {message}
        </Alert>
    );
}

function LoginSignUp() {
    const [pageName, setPageName] = useState("Login");
    const formRef = useRef(null);
    const navigate = useNavigate();
    const [alertMessage, setAlertMessage] = useState(null);
    const [alertVariant, setAlertVariant] = useState("success");
    const [errors, setErrors] = useState({});

    const showAlert = (message, variant) => {
        setAlertMessage(message);
        setAlertVariant(variant);
        setTimeout(() => setAlertMessage(null), 3000);
    };

    const validateForm = (data) => {
        let errors = {};
        if (!data.email) {
            errors.email = "Email is required";
        } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(data.email)) {
            errors.email = "Invalid email format";
        }

        if (!data.password) {
            errors.password = "Password is required";
        } else if (data.password.length < 6) {
            errors.password = "Password must be at least 6 characters long";
        }

        if (pageName === "Sign Up" && !data.userName) {
            errors.userName = "Username is required";
        }

        return errors;
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        const form = formRef.current;
        const formData = new FormData(form);
        const data = {
            email: formData.get('email'),
            password: formData.get('password'),
            userName: formData.get('username')
        };

        const validationErrors = validateForm(data);
        if (Object.keys(validationErrors).length > 0) {
            setErrors(validationErrors);
            return;
        }

        setErrors({});

        try {
            if (pageName === "Sign Up") {
                await axiosInstance.post('/signUp', data);
                showAlert("User registered successfully!", "success");
                setPageName("Login");
            } else {
                const response = await axiosInstance.post('/login', data);
                localStorage.setItem("uname", response.data.uname);
                localStorage.setItem("jwt", response.data.jwt);
                localStorage.setItem("uId", response.data.uId);
                showAlert("Logged in successfully!", "success");
                setTimeout(() => navigate('/Home'), 2000);
            }
        } catch (error) {
            console.error(error);
            showAlert("An error occurred! Please try again.", "danger");
        }
    };

    return (
        <div className="outerBox">
            {alertMessage && <CustomAlert message={alertMessage} variant={alertVariant} onClose={() => setAlertMessage(null)} />}
            <div className="innerBox">
                <form className="py-3 px-4 loginForm" ref={formRef} noValidate onSubmit={handleSubmit}>
                    <div className="login-title">{pageName}</div>
                    {pageName === "Sign Up" && (
                        <div className="mb-3">
                            <label className="form-label">Username</label>
                            <input type="text" className="form-control" name="username" required />
                            {errors.userName && <div className="text-danger">{errors.userName}</div>}
                        </div>
                    )}
                    <div className="mb-2">
                        <label className="form-label">Email address</label>
                        <input type="email" className="form-control" name="email" required />
                        {errors.email && <div className="text-danger">{errors.email}</div>}
                    </div>
                    <div className="mb-4">
                        <label className="form-label">Password</label>
                        <input type="password" className="form-control" name="password" required />
                        {errors.password && <div className="text-danger">{errors.password}</div>}
                    </div>
                    <Button type="submit" className="btn btn-primary w-100 mb-2">{pageName}</Button>
                    <div className="w-100 d-flex justify-content-center">
                        {pageName === "Sign Up" ? (
                            <div className="form-text signUpText">
                                Already have an Account?
                                <button type="button" onClick={() => setPageName("Login")} className="ms-1">Click here</button>
                            </div>
                        ) : (
                            <div className="form-text signUpText">
                                Don't have an Account?
                                <button type="button" onClick={() => setPageName("Sign Up")} className="ms-1">Click here</button>
                            </div>
                        )}
                    </div>
                </form>
            </div>
        </div>
    );
}

export default LoginSignUp;
