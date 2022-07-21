package com.dreamix.springcities.city.domain.service;

import com.dreamix.springcities.city.application.repository.CityRepository;
import com.dreamix.springcities.city.domain.entity.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveCityService {

    @Autowired
    private CityRepository cityRepository;

    public City save(City city) {
        return cityRepository.saveAndFlush(city);
    }

}
