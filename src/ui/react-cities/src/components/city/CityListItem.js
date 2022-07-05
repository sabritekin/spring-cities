import React from "react";
import { ImageListItem, ImageListItemBar } from "@mui/material";
import EditCityButton from "./EditCityButton";

const CityListItem = (props) => {
    return (
        <ImageListItem key={props.cityId}>
            <img
                src={`${props.cityPhoto}?w=248&fit=crop&auto=format`}
                srcSet={`${props.cityPhoto}?w=248&fit=crop&auto=format&dpr=2 2x`}
                alt={props.cityName}
                loading="lazy"
            />
            {(sessionStorage.getItem("jwt") !== null)
                ? <ImageListItemBar position="below" title={props.cityName} actionIcon={<EditCityButton cityName={props.cityName} cityId={props.cityId} />} actionPosition="left">
                </ImageListItemBar>
                : <ImageListItemBar position="below" title={props.cityName}>
                </ImageListItemBar>}
        </ImageListItem>
    );
};

export default CityListItem;