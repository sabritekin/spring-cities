/*
*
* Controller class for the City entity.
*
* */
package com.dreamix.springcities.city.controller.v1;

import com.dreamix.springcities.city.dto.CityDTO;
import com.dreamix.springcities.city.dto.CityListDTO;
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
    public ResponseEntity<CityDTO> get(@PathVariable Long id) {
        log.info("Get request received for city with id: {}", id);

        return cityRepository.findById(id)
                .map(city -> new CityDTO(city.getId(), city.getName(), city.getPhoto()))
                .map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityDTO> update(@PathVariable Long id, @Valid @RequestBody CityDTO cityDTO) {
        log.info("Update request received for city: {}", cityDTO);

        return cityRepository.findById(id)
                .map(city -> {
                    city.setName(cityDTO.getName());
                    city.setPhoto(city.getPhoto());
                    return cityRepository.saveAndFlush(city);
                })
                .map(response -> ResponseEntity.ok().body(
                        new CityDTO(response.getId(), response.getName(), response.getPhoto())
                ))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping(params = { "page", "size" })
    public ResponseEntity<CityListDTO> getPaginated(@RequestParam("page") @Min(0) int page, @RequestParam("size") @Min(1) int size) {
        log.info("GetAll with paging request received with size: {} and page: {}", size, page);

        try {
            return ResponseEntity.ok().body(
                    new CityListDTO(cityRepository.count(),
                    cityRepository.findAll(PageRequest.of(page, size))
                            .stream()
                            .map(city -> new CityDTO(city.getId(), city.getName(), city.getPhoto()))
                            .toList())
            );
        } catch (Exception exc) {
            log.error(exc.getMessage());

            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(params = { "page", "size", "searchText" })
    public ResponseEntity<CityListDTO> getPaginated(@RequestParam("page") @Min(0) int page, @RequestParam("size") @Min(1) int size, @RequestParam("searchText") @NotBlank String searchText) {
        log.info("GetAll with paging and search text request received with size: {}, page: {} and search text: {}", size, page, searchText);

        try {
            return ResponseEntity.ok().body(
                    new CityListDTO(cityRepository.countByNameContainingIgnoreCase(searchText),
                    cityRepository.findByNameContainingIgnoreCase(PageRequest.of(page, size), searchText)
                            .stream()
                            .map(city -> new CityDTO(city.getId(), city.getName(), city.getPhoto()))
                            .toList())
            );
        } catch (Exception exc) {
            log.error(exc.getMessage());

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
