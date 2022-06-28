/*
 *
 * The City repository used to persist city data.
 *
 */

package com.dreamix.springcities.infrastructure.persistance.repository;

import com.dreamix.springcities.common.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}