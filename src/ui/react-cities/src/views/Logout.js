import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import authConfig from '../config/api/endpoints/auth-service.json';

const Logout = () => {
    const logoutEndpoint = authConfig.ENDPOINTS.LOGOUT;

    const navigate = useNavigate();

    useEffect(() => {
        fetch(logoutEndpoint, {
            method: 'POST'
        })
            .then(response => {
                sessionStorage.clear();
                navigate('/');
            })
    });

    return (
        <div></div>
    )
}

export default Logout;