/*
 *
 * The City repository used to persist city data.
 *
 */

package com.dreamix.springcities.infrastructure.persistance.repository;

import com.dreamix.springcities.common.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CityRepository extends JpaRepository<City, Long> {
    Collection<City> findByNameContainingIgnoreCase(String name);
}