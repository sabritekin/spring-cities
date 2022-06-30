import React, { useState, useEffect } from 'react';
import { Alert, Backdrop, Box, Button, CircularProgress, FormGroup, Grid, TextField } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import AppNavbar from '../components/AppNavbar';

const Login = () => {

    const [userName, setUserName] = useState("");
    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);
    const [alert, setAlert] = useState(false);
    const [alertContent, setAlertContent] = useState("");

    const navigate = useNavigate();

    const handleSubmit = e => {
        e.preventDefault();
        setLoading(true);
        const user = { userName: userName, password: password };
        fetch("login", {
            method: 'POST',
            body: JSON.stringify(user)
        })
            .then(res => {
                const jwtToken = res.headers.get('Authorization');
                if (jwtToken !== null) {
                    sessionStorage.setItem("jwt", jwtToken);
                    sessionStorage.setItem("isAuthenticated", "true");

                    navigate('/');
                }
                else {
                    setAlertContent("Failed to log in. Please try again.");
                    setAlert(true);
                    setLoading(false);
                }
            })
            .catch(err => {
                console.error(err);
                setAlertContent(err);
                setAlert(true);
                setLoading(false);
            })
    };

    useEffect(() => {
        if (sessionStorage.getItem("jwt") !== null) {
            navigate('/');
        }
    });

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
                            <TextField label="User Name" required value={userName} onChange={e => setUserName(e.target.value)} style={{ marginBottom: 10 + 'px' }} inputProps={{ maxLength: 255 }} />
                            <TextField label="Password" type="password" required value={password} onChange={e => setPassword(e.target.value)} style={{ marginBottom: 10 + 'px' }} inputProps={{ maxLength: 255 }} />
                            <Button type="submit" variant="contained" color="primary">LOGIN</Button>
                        </FormGroup>
                    </form>
                    {alert ? <Alert severity='error'>{alertContent}</Alert> : <></>}
                </Box>
            </Grid>
        </div>
    )

}

export default Login;