package com.dreamix.springcities.city.application.facade.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetCityListDTO {

    @Min(0)
    private long count;
    @NotNull
    private List<GetCityDTO> cityList;

}
