import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import Box from '@mui/material/Box';
import ImageList from '@mui/material/ImageList';
import ImageListItem from '@mui/material/ImageListItem';
import ImageListItemBar from '@mui/material/ImageListItemBar';
import Pagination from '@mui/material/Pagination';
import Stack from '@mui/material/Stack';
import Grid from '@mui/material/Grid';
import { Backdrop, Button, CircularProgress, TextField } from '@mui/material';
import IconButton from '@mui/material/IconButton';
import EditIcon from '@mui/icons-material/Edit';

import AppNavbar from '../components/AppNavbar';

const Home = () => {

    const [page, setPage] = useState(1);
    const [loading, setLoading] = useState(false);
    const [pageCount, setPageCount] = useState(0);
    const [cities, setCities] = useState([]);
    const [searchText, setSearchText] = useState("");

    const itemCount = 20;

    const changePage = (event, value) => {
        if (page !== value) {
            setLoading(true);
            setPage(value);
        }
    };

    const searchCity = (event) => {
        event.preventDefault();
        setPage(1);
    };

    useEffect(() => {
        setLoading(true);
        fetch('api/cities/get/' + itemCount + '/' + (page - 1) + (((searchText !== "" && searchText !== null) ? ('/' + searchText) : (""))))
            .then(response => response.json())
            .then(data => {
                setCities(data.cityList);
                setPageCount(Math.ceil(data.count / itemCount));
                setLoading(false);
            });
    }, [page, searchText]);

    return (
        <div>
            <Backdrop sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }} open={loading}>
                <CircularProgress color="inherit" />
            </Backdrop>
            <AppNavbar />
            <Box sx={{ flexGrow: 1 }}>
                <Grid item xs={12} justifyContent="center">
                    <form onSubmit={searchCity}>
                        <TextField label="Search a city..." value={searchText} variant="outlined" onChange={e => setSearchText(e.target.value)} style={{ marginTop: 10 + 'px', marginBottom: 10 + 'px', marginRight: 5 + 'px', marginLeft: 5 + 'px' }} />
                        <Button type="submit" variant="contained" color="info" style={{ marginTop: 20 + 'px' }}>Search</Button>
                    </form>
                </Grid>
                <Grid container spacing={2}>
                    <Grid item xs={60}>
                        <ImageList variant="masonry" cols={6} gap={4}>
                            {cities.map((city) => (
                                <ImageListItem key={city.id}>
                                    <img
                                        src={`${city.photo}?w=248&fit=crop&auto=format`}
                                        srcSet={`${city.photo}?w=248&fit=crop&auto=format&dpr=2 2x`}
                                        alt={city.name}
                                        loading="lazy"
                                    />
                                    {(sessionStorage.getItem("jwt") !== null)
                                        ? <ImageListItemBar position="below" title={city.name} actionIcon={<IconButton sx={{ color: 'black' }} aria-label={`star ${city.name}`} component={Link} to={"/city/" + city.id}><EditIcon /></IconButton>} actionPosition="left">
                                        </ImageListItemBar>
                                        : <ImageListItemBar position="below" title={city.name}>
                                        </ImageListItemBar>}
                                </ImageListItem>
                            ))}
                        </ImageList>
                    </Grid>
                    <Grid item xs={12}>
                        <Stack spacing={2} alignItems="center">
                            <Pagination count={pageCount} page={page} showFirstButton showLastButton onChange={changePage} variant="outlined" shape="rounded" size="large" />
                        </Stack>
                    </Grid>
                </Grid>
            </Box>
        </div>
    );
};

export default Home;