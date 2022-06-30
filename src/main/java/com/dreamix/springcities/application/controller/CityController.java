/*
*
* Controller class for the City entity.
*
* */
package com.dreamix.springcities.application.controller;

import com.dreamix.springcities.application.dto.CityDTO;
import com.dreamix.springcities.common.model.City;
import com.dreamix.springcities.infrastructure.persistance.repository.CityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/cities")
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @RequestMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        log.info("Get request received for city with id: {}", id);

        Optional<City> city = cityRepository.findById(id);
        return city.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("get/{size}/{page}")
    CityDTO getAll(@PathVariable int size, @PathVariable int page) {
        log.info("GetAll with paging request received with size: {} and page: {}", size, page);

        return new CityDTO(cityRepository.count(), cityRepository.findAll(PageRequest.of(page, size)).stream().toList());
    }

    @GetMapping("get/{size}/{page}/{searchText}")
    CityDTO getAll(@PathVariable int size, @PathVariable int page, @PathVariable String searchText) {
        log.info("GetAll with paging and search text request received with size: {}, page: {} and search text: {}", size, page, searchText);

        return new CityDTO(cityRepository.countByNameContainingIgnoreCase(searchText), cityRepository.findByNameContainingIgnoreCase(PageRequest.of(page, size), searchText).stream().toList());
    }

    @PutMapping("/update/{id}")
    ResponseEntity<City> update(@Valid @RequestBody City city) {
        log.info("Update request received for city: {}", city);

        City result = cityRepository.saveAndFlush(city);
        return ResponseEntity.ok().body(result);
    }

}
