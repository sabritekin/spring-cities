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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/city")
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping("/{id}")
    public ResponseEntity<CityDTO> get(@PathVariable Long id) {
        log.info("Get request received for city with id: {}", id);

        return cityRepository.findById(id)
                .map(city -> modelMapper.map(city, CityDTO.class))
                .map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityDTO> update(@PathVariable Long id, @Valid @RequestBody CityDTO cityDTO) {
        log.info("Update request received for city: {}", cityDTO);

        return cityRepository.findById(id)
                .map(city -> {
                    modelMapper.map(cityDTO, city);
                    return cityRepository.saveAndFlush(city);
                })
                .map(response -> ResponseEntity.ok().body(modelMapper.map(response, CityDTO.class)))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping(params = { "page", "size" })
    public CityListDTO getPaginated(@RequestParam("page") int page, @RequestParam("size") int size) {
        log.info("GetAll with paging request received with size: {} and page: {}", size, page);

        return new CityListDTO(cityRepository.count(),
                cityRepository.findAll(PageRequest.of((page > 0 ? page : 0), size))
                        .stream()
                        .map(city -> modelMapper.map(city, CityDTO.class))
                        .toList()
        );
    }

    @GetMapping(params = { "page", "size", "searchText" })
    public CityListDTO getPaginated(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("searchText") String searchText) {
        log.info("GetAll with paging and search text request received with size: {}, page: {} and search text: {}", size, page, searchText);

        return new CityListDTO(cityRepository.countByNameContainingIgnoreCase(searchText),
                cityRepository.findByNameContainingIgnoreCase(PageRequest.of((page > 0 ? page : 0), size),searchText)
                        .stream()
                        .map(city -> modelMapper.map(city, CityDTO.class))
                        .toList()
        );
    }

}
