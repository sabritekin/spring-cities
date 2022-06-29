package com.dreamix.springcities.application.controller;

import com.dreamix.springcities.common.model.City;
import com.dreamix.springcities.infrastructure.persistance.repository.CityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/cities")
public class CityController {

    private CityRepository cityRepository;

    @GetMapping("/get-all")
    Collection<City> getAll() {
        log.info("GetAll request received");

        return cityRepository.findAll();
    }

    @GetMapping("/count")
    long count() {
        log.info("Count request received");

        return cityRepository.count();
    }

    @GetMapping("get-page/{size}/{page}")
    Collection<City> getAll(@PathVariable int size, @PathVariable int page) {
        log.info("GetAll with paging request received");

        return cityRepository.findAll(PageRequest.of(page, size)).stream().toList();
    }

    @RequestMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        log.info("Get request received for city with id: {}", id);

        Optional<City> city = cityRepository.findById(id);
        return city.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping("/find/{name}")
    public Collection<City> search(@PathVariable String name) {
        log.info("Search request received for city with name containing: {}", name);

        return cityRepository.findByNameContainingIgnoreCase(name);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<City> update(@Valid @RequestBody City city) {
        log.info("Update request received for city: {}", city);

        City result = cityRepository.saveAndFlush(city);
        return ResponseEntity.ok().body(result);
    }

}
