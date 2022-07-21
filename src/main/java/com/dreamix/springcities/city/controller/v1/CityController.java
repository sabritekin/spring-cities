/*
*
* Controller class for the City entity.
*
* */
package com.dreamix.springcities.city.controller.v1;

import com.dreamix.springcities.city.application.facade.CityFacade;
import com.dreamix.springcities.city.application.facade.dto.GetCityDTO;
import com.dreamix.springcities.city.application.facade.dto.GetCityListDTO;
import com.dreamix.springcities.city.application.facade.dto.UpdateCityDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/city")
public class CityController {

    @Autowired
    private final CityFacade cityFacade;

    @GetMapping("/{id}")
    public ResponseEntity<GetCityDTO> get(@PathVariable Long id) {
        log.info("Get request received for city with id: {}", id);

        return cityFacade.get(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Long id, @Valid @RequestBody UpdateCityDTO updateCityDTO) {
        log.info("Update request received for city: {}", updateCityDTO);

        if(cityFacade.update(id, updateCityDTO)) {
            return ResponseEntity.ok().body(
                    Boolean.TRUE
            );
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(params = { "page", "size" })
    public ResponseEntity<GetCityListDTO> getPaginated(@RequestParam("page") @Min(0) int page, @RequestParam("size") @Min(1) int size) {
        log.info("GetAll with paging request received with size: {} and page: {}", size, page);

        try {
            GetCityListDTO cityListDTO = cityFacade.getPaginated(page, size);
            if(cityListDTO != null) {
                return ResponseEntity.ok(cityListDTO);
            }
        } catch (Exception exc) {
            log.error(exc.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(params = { "page", "size", "searchText" })
    public ResponseEntity<GetCityListDTO> getPaginated(@RequestParam("page") @Min(0) int page, @RequestParam("size") @Min(1) int size, @RequestParam("searchText") @NotBlank String searchText) {
        log.info("GetAll with paging and search text request received with size: {}, page: {} and search text: {}", size, page, searchText);

        try {
            GetCityListDTO cityListDTO = cityFacade.getPaginatedSearchResults(page, size, searchText);
            if(cityListDTO != null) {
                return ResponseEntity.ok(cityListDTO);
            }
        } catch (Exception exc) {
            log.error(exc.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
