import React, { useEffect, useState } from 'react';
import Box from '@mui/material/Box';
import ImageList from '@mui/material/ImageList';
import Pagination from '@mui/material/Pagination';
import Stack from '@mui/material/Stack';
import Grid from '@mui/material/Grid';
import { Backdrop, Button, CircularProgress, TextField, Alert } from '@mui/material';

import cityServiceConfig from '../../config/api/endpoints/city-service.json';

import CityListItem from './CityListItem';

const CityList = () => {
    const getCityEndpoint = cityServiceConfig.API.PATH + cityServiceConfig.API.VERSION + cityServiceConfig.API.SERVICE + cityServiceConfig.API.ENDPOINTS.GET;
    const pageQueryString = cityServiceConfig.QUERY.PAGE;
    const sizeQueryString = cityServiceConfig.QUERY.SIZE;
    const searchTextQueryString = cityServiceConfig.QUERY.SEARCH;

    const itemCount = 20;

    const [page, setPage] = useState(1);
    const [loading, setLoading] = useState(false);
    const [pageCount, setPageCount] = useState(0);
    const [cities, setCities] = useState([]);
    const [searchText, setSearchText] = useState("");

    const [alert, setAlert] = useState(false);
    const [alertContent, setAlertContent] = useState("");

    const changePage = (event, value) => {
        if (page !== value) {
            setLoading(true);
            setPage(value);
        }
    };

    const changeSearchText = (event) => {
        event.preventDefault();
        setSearchText(event.target.value);
        setPage(1);
    }

    const searchCity = (event) => {
        event.preventDefault();
        setPage(1);
    };

    useEffect(() => {
        setLoading(true);
        fetch(getCityEndpoint + '?' + pageQueryString + '=' + (page - 1)
            + '&' + sizeQueryString + '=' + itemCount
            + (((searchText !== "" && searchText !== null) ? ('&' + searchTextQueryString + "=" + searchText) : (""))))
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else {
                    setAlertContent("Could not connect to server. Please try again.");
                    setAlert(true);
                    setLoading(false);
                }
            })
            .then(data => {
                setCities(data.cityList);
                setPageCount(Math.ceil(data.count / itemCount));
                setLoading(false);
            });
    }, [page, searchText, getCityEndpoint, pageQueryString, sizeQueryString, searchTextQueryString]);

    return (
        <div>
            <Backdrop sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }} open={loading}>
                <CircularProgress color="inherit" />
            </Backdrop>
            {alert
                ? <Alert severity='error'>{alertContent}</Alert>
                : <Box sx={{ flexGrow: 1 }}>
                    <Grid item xs={12} justifyContent="center">
                        <form onSubmit={searchCity}>
                            <TextField label="Search a city..." value={searchText} variant="outlined" onChange={e => changeSearchText(e)} style={{ marginTop: 10 + 'px', marginBottom: 10 + 'px', marginRight: 5 + 'px', marginLeft: 5 + 'px' }} />
                            <Button type="submit" variant="contained" color="info" style={{ marginTop: 20 + 'px' }}>Search</Button>
                        </form>
                    </Grid>
                    <Grid container spacing={2}>
                        <Grid item xs={60}>
                            <ImageList variant="masonry" cols={6} gap={4}>
                                {cities.map((city) => (
                                    <CityListItem key={city.id} cityId={city.id} cityName={city.name} cityPhoto={city.photo}></CityListItem>
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
            }
        </div>
    );
};

export default CityList;