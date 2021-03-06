import React, { useState, useEffect } from 'react';
import { Alert, Backdrop, Box, Button, CircularProgress, FormGroup, Grid, TextField } from '@mui/material';
import { useNavigate, useParams } from 'react-router-dom';
import AppNavbar from '../components/common/AppNavbar';

import cityServiceConfig from '../config/api/endpoints/city-service.json';

const City = () => {
    const getCityEndpoint = cityServiceConfig.API.PATH + cityServiceConfig.API.VERSION + cityServiceConfig.API.SERVICE + cityServiceConfig.API.ENDPOINTS.GET;
    const updateCityEndpoint = cityServiceConfig.API.PATH + cityServiceConfig.API.VERSION + cityServiceConfig.API.SERVICE + cityServiceConfig.API.ENDPOINTS.UPDATE;

    const initialCity = {
        name: "",
        photo: ""
    }

    const { id } = useParams();
    const [city, setCity] = useState(initialCity);
    const [alert, setAlert] = useState(false);
    const [alertContent, setAlertContent] = useState("");
    const [loading, setLoading] = useState(false);

    const navigate = useNavigate();

    const handleChange = (event) => {
        const { name, value } = event.target
        setCity({ ...city, [name]: value })
    }

    const handleSubmit = e => {
        e.preventDefault();
        setLoading(true);
        fetch(updateCityEndpoint + id, {
            method: 'PUT',
            headers: {
                "Authorization": sessionStorage.getItem("jwt"),
                "Content-Type": "application/json"
            },
            body: JSON.stringify(city)
        })
            .then(res => {
                setLoading(false);
                if (res.ok) {
                    navigate('/');
                } else {
                    if (res.status === 403) {
                        setAlertContent("You are not allowed to edit. Please log in first.");
                        setAlert(true);
                    } else {
                        setAlertContent("City could not be updated. Please try again.");
                        setAlert(true);
                    }
                }
            })
            .catch(err => {
                console.error(err);
                setAlertContent("City could not be updated. Please try again.");
                setAlert(true);
                setLoading(false);
            })
    };

    useEffect(() => {
        if (sessionStorage.getItem("jwt") === null || sessionStorage.getItem("jwt") === "") {
            navigate('/');
        } else {
            setLoading(true);
            fetch(getCityEndpoint + id, {
                method: 'GET',
                headers: {
                    "Authorization": sessionStorage.getItem("jwt"),
                    "Content-Type": "application/json"
                }
            })
                .then(response => response.json())
                .then(data => {
                    setCity(data);
                    setLoading(false);
                })
                .catch(err => {
                    console.error(err);

                    setCity("");
                    setAlertContent(err);
                    setAlert(true);
                    setLoading(false);
                })
        }
    }, [id, setCity, navigate, getCityEndpoint]);

    return (
        <div>
            <Backdrop sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }} open={loading}>
                <CircularProgress color="inherit" />
            </Backdrop>
            <AppNavbar />
            <Grid container spacing={0} direction="column" alignItems="center" justifyContent="center" style={{ minHeight: '50vh' }}>
                <Box sx={{ width: 1 / 4 }}>
                    <form onSubmit={handleSubmit}>
                        <FormGroup>
                            <TextField label="Name" name="name" required value={city.name || ""} onChange={handleChange} style={{ marginBottom: 10 + 'px' }} inputProps={{ maxLength: 255 }} />
                            <TextField label="Photo" name="photo" required value={city.photo || ""} onChange={handleChange} style={{ marginBottom: 10 + 'px' }} inputProps={{ maxLength: 2048 }} />
                            <Button type="submit" variant="contained" color="primary" style={{ marginBottom: 10 + 'px' }}>UPDATE</Button>
                        </FormGroup>
                    </form>
                    {alert ? <Alert severity='error'>{alertContent}</Alert> : <></>}
                </Box>
            </Grid>
        </div>
    );

};

export default City;