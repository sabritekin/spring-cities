package com.dreamix.springcities.city.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CityListDTO {

    private long count;
    private List<CityDTO> cityList;

}