import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const Logout = () => {
    const navigate = useNavigate();

    useEffect(() => {
        fetch("logout", {
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