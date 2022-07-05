import React from 'react';

import AppNavbar from '../components/common/AppNavbar';
import CityList from '../components/city/CityList';

const Home = () => {
    return (
        <div>
            <AppNavbar />
            <CityList />
        </div>
    );
};

export default Home;