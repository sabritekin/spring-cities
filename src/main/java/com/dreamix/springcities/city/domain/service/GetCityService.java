package com.dreamix.springcities.city.domain.service;

import com.dreamix.springcities.city.domain.entity.City;
import com.dreamix.springcities.city.application.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetCityService {

    @Autowired
    private CityRepository cityRepository;

    public Optional<City> get(long id) {
        return cityRepository.findById(id);
    }

    public long getCount() {
        return cityRepository.count();
    }

    public long getCountOfSearchResults(String searchText) {
        return cityRepository.countByNameContainingIgnoreCase(searchText);
    }

    public List<City> getAll(int page, int size) {
        return cityRepository.findAll(PageRequest.of(page, size))
                .stream()
                .toList();
    }

    public List<City> getAll(int page, int size, String searchText) {
        return cityRepository.findByNameContainingIgnoreCase(PageRequest.of(page, size), searchText)
                .stream()
                .toList();
    }

}
