package com.dreamix.springcities.city.application.facade;

import com.dreamix.springcities.city.application.facade.dto.GetCityDTO;
import com.dreamix.springcities.city.application.facade.dto.GetCityListDTO;
import com.dreamix.springcities.city.application.facade.dto.UpdateCityDTO;
import com.dreamix.springcities.city.domain.entity.City;
import com.dreamix.springcities.city.domain.service.GetCityService;
import com.dreamix.springcities.city.domain.service.SaveCityService;
import com.dreamix.springcities.common.Facade;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import java.util.Optional;

@Facade
@RequiredArgsConstructor
public class CityFacade {

    @Autowired
    GetCityService getCityService;
    @Autowired
    SaveCityService saveCityService;

    public Optional<GetCityDTO> get(Long id) {
        if(id != null && id > 0) {
            return getCityService.get(id)
                    .map(this::mapCityToGetCityDTO);
        }
        return Optional.empty();
    }

    public boolean update(Long id, @Valid UpdateCityDTO updateCityDTO) {
        if(id != null && id > 0) {
            return (getCityService.get(id)
                    .map(city -> saveCityService.save(getUpdatedCity(city.getId(), updateCityDTO)))
                    .orElse(null) != null);
        }
        return false;
    }

    public GetCityListDTO getPaginated(int page, int size) {
        if(page >=0 && size > 0) {
            return new GetCityListDTO(getCityService.getCount(),
            getCityService.getAll(page, size)
                    .stream()
                    .map(this::mapCityToGetCityDTO)
                    .toList()
            );
        }
        return null;
    }

    public GetCityListDTO getPaginatedSearchResults(int page, int size, String searchText) {
        if(page >=0 && size > 0 && searchText != null && !searchText.isBlank()) {
            return new GetCityListDTO(getCityService.getCountOfSearchResults(searchText),
                    getCityService.getAll(page, size, searchText)
                            .stream()
                            .map(this::mapCityToGetCityDTO)
                            .toList()
            );
        }
        return null;
    }

    private GetCityDTO mapCityToGetCityDTO(City city) {
        return new GetCityDTO(city.getId(), city.getName(), city.getPhoto());
    }

    private City getUpdatedCity(Long id, UpdateCityDTO updateCityDTO) {
        return new City(id, updateCityDTO.getName(), updateCityDTO.getPhoto());
    }

}
