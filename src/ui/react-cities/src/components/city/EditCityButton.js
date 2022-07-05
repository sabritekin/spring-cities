import { IconButton } from "@mui/material";
import React from "react";
import { Link } from "react-router-dom";
import EditIcon from '@mui/icons-material/Edit';

const EditCityButton = (props) => {
    return (
        <IconButton sx={{ color: 'black' }} aria-label={`star ${props.cityName}`} component={Link} to={"/city/" + props.cityId}><EditIcon /></IconButton>
    );
};

export default EditCityButton;