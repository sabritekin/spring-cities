/*
*
* Controller class for the City entity.
*
* */
package com.dreamix.springcities.city.controller.v1;

import com.dreamix.springcities.city.dto.GetCityDTO;
import com.dreamix.springcities.city.dto.GetCityListDTO;
import com.dreamix.springcities.city.dto.UpdateCityDTO;
import com.dreamix.springcities.city.repository.CityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/city")
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @GetMapping("/{id}")
    public ResponseEntity<GetCityDTO> get(@PathVariable Long id) {
        log.info("Get request received for city with id: {}", id);

        return cityRepository.findById(id)
                .map(city -> new GetCityDTO(city.getId(), city.getName(), city.getPhoto()))
                .map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Long id, @Valid @RequestBody UpdateCityDTO updateCityDTO) {
        log.info("Update request received for city: {}", updateCityDTO);

        return cityRepository.findById(id)
                .map(city -> {
                    city.setName(updateCityDTO.getName());
                    city.setPhoto(updateCityDTO.getPhoto());
                    return cityRepository.saveAndFlush(city);
                })
                .map(response -> ResponseEntity.ok().body(
                        Boolean.TRUE
                ))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping(params = { "page", "size" })
    public ResponseEntity<GetCityListDTO> getPaginated(@RequestParam("page") @Min(0) int page, @RequestParam("size") @Min(1) int size) {
        log.info("GetAll with paging request received with size: {} and page: {}", size, page);

        try {
            return ResponseEntity.ok().body(
                    new GetCityListDTO(cityRepository.count(),
                    cityRepository.findAll(PageRequest.of(page, size))
                            .stream()
                            .map(city -> new GetCityDTO(city.getId(), city.getName(), city.getPhoto()))
                            .toList())
            );
        } catch (Exception exc) {
            log.error(exc.getMessage());

            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(params = { "page", "size", "searchText" })
    public ResponseEntity<GetCityListDTO> getPaginated(@RequestParam("page") @Min(0) int page, @RequestParam("size") @Min(1) int size, @RequestParam("searchText") @NotBlank String searchText) {
        log.info("GetAll with paging and search text request received with size: {}, page: {} and search text: {}", size, page, searchText);

        try {
            return ResponseEntity.ok().body(
                    new GetCityListDTO(cityRepository.countByNameContainingIgnoreCase(searchText),
                    cityRepository.findByNameContainingIgnoreCase(PageRequest.of(page, size), searchText)
                            .stream()
                            .map(city -> new GetCityDTO(city.getId(), city.getName(), city.getPhoto()))
                            .toList())
            );
        } catch (Exception exc) {
            log.error(exc.getMessage());

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
