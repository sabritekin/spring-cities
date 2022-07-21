package com.dreamix.springcities.city.domain.service;

import com.dreamix.springcities.city.domain.entity.City;
import com.dreamix.springcities.city.application.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveCityService {

    @Autowired
    private CityRepository cityRepository;

    public City save(City city) {
        return cityRepository.saveAndFlush(city);
    }

}
