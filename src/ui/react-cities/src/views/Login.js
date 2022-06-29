import React, { useState, useEffect } from 'react';
import { Alert, Backdrop, Box, Button, CircularProgress, FormGroup, Grid, TextField } from '@mui/material';
import { useNavigate } from 'react-router-dom';

const Login = () => {

    const[userName, setUserName] = useState("");
    const[password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);
    const [alert, setAlert] = useState(false);
    const [alertContent, setAlertContent] = useState('');

    const navigate = useNavigate();

    const handleSubmit = e => {
        e.preventDefault();
        setLoading(true);
        const user = {userName: userName, password: password};    
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
            .catch(err =>{
                console.error(err);
                setAlertContent(err);
                setAlert(true);
            })
    };
    
    useEffect(() => {
        if(sessionStorage.getItem("jwt") !== null) {
            navigate('/');
        }
      });

    return (
        <div>
            <Backdrop sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }} open={loading}>
                <CircularProgress color="inherit" />
            </Backdrop>            
            <Grid container justifyContent="center">
            <Box sx={{ width: 1/4 }}>
                    <form onSubmit={handleSubmit}>
                    <FormGroup onSubmit={handleSubmit}>
                    <TextField label="First Name" required value={userName} onChange={e => setUserName(e.target.value)} />
                    <TextField label="Password" type="password" required value={password} onChange={e => setPassword(e.target.value)} />
                    <Button type="submit" variant="contained" color="primary">Login</Button>
                    </FormGroup>
                    </form>
                    { alert ? <Alert severity='error'>{alertContent}</Alert> : <></> }
            </Box>
            </Grid>
        </div>
    )

}

export default Login;