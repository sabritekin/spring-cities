import React, { useEffect, useState } from 'react';
import Box from '@mui/material/Box';
import ImageList from '@mui/material/ImageList';
import ImageListItem from '@mui/material/ImageListItem';
import ImageListItemBar from '@mui/material/ImageListItemBar';
import Pagination from '@mui/material/Pagination';
import Stack from '@mui/material/Stack';
import Grid from '@mui/material/Grid';

import AppNavbar from '../components/AppNavbar';
import { Backdrop, CircularProgress } from '@mui/material';
import { Button } from 'reactstrap';

const Home = () => {

    const [page, setPage] = useState(1);
    const [loading, setLoading] = useState(true);
    const [count, setCount] = useState([]);
    const [cities, setCities] = useState([]);

    const changePage = (event, value) => {
        setLoading(true);
        setPage(value);
        fetch('api/cities/get-page/10/' + (value - 1))
            .then(response => response.json())
            .then(data => {
                setCities(data);
                setLoading(false);
            })
    };

    useEffect(() => {
        setLoading(true);

        fetch('api/cities/get-page/10/0')
            .then(response => response.json())
            .then(data => {
                setCities(data);
                setLoading(false);
            })

        fetch('api/cities/count')
            .then(response => response.json())
            .then(data => {
                setCount(data);
                setLoading(false);
            })
    }, []);

    return (
        <div>
            <Backdrop sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }} open={loading}>
                <CircularProgress color="inherit" />
            </Backdrop>
            <AppNavbar isAuthenticated={false} />
            <Box sx={{ flexGrow: 1 }} bgcolor="#F6F6F6">
                <Grid container spacing={2}>
                    <Grid item xs={12}>
                        <ImageList variant="masonry" cols={4} gap={4}>
                            {cities.map((city) => (
                                <ImageListItem key={city.id}>
                                    <img
                                        src={`${city.photo}?w=248&fit=crop&auto=format`}
                                        srcSet={`${city.photo}?w=248&fit=crop&auto=format&dpr=2 2x`}
                                        alt={city.name}
                                        loading="lazy"
                                    />
                                    <ImageListItemBar position="below" title={city.name}>
                                        if(jwtToken != null) {
                                            <Button>Edit</Button>
                                        }
                                    </ImageListItemBar>
                                </ImageListItem>
                            ))}
                        </ImageList>
                    </Grid>
                    <Grid item xs={12}>
                        <Stack spacing={2} alignItems="center">
                            <Pagination count={count / 10} page={page} showFirstButton showLastButton onChange={changePage} size="large" />
                        </Stack>
                    </Grid>
                </Grid>
            </Box>
        </div>
    );
}

export default Home;