package com.dreamix.springcities.city.application.facade.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCityDTO {

    @NonNull
    @NotBlank
    @Size(max = 255)
    private String name;
    @NonNull
    @NotBlank
    @Size(max = 2048)
    private String photo;

}
