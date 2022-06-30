package com.dreamix.springcities.application.dto;

import com.dreamix.springcities.common.model.City;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CityDTO {

    private long count;
    private List<City> cityList;

}
