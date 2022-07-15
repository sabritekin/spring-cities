package com.dreamix.springcities.city.model;

import org.junit.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@EnabledIfSystemProperty(named = "spring.profiles.active", matches = "dev")
@RunWith(SpringRunner.class)
public class CityTests {

    private final Long testId = 1002L;
    private final String testName = "Test City";
    private final String testPhoto = "Test Photo";

    @Test
    public void givenCity_whenCityConstructor_thenObjectCreated() {
        City testCity = new City(testId, testName, testPhoto);
        assertNotNull(testCity);
        assertEquals(testId, testCity.getId());
        assertEquals(testName, testCity.getName());
        assertEquals(testPhoto, testCity.getPhoto());
    }

    @Test
    public void givenCityWithNullId_whenCityConstructor_thenNullPointerException() {
        assertThrows(NullPointerException.class, () -> new City(null, testName, testPhoto));
    }

    @Test
    public void givenCityWithNullName_whenCityConstructor_thenNullPointerException() {
        assertThrows(NullPointerException.class, () -> new City(testId, null, testPhoto));
    }

    @Test
    public void givenCityWithNullPhoto_whenCityConstructor_thenNullPointerException() {
        assertThrows(NullPointerException.class, () -> new City(testId, testName, null));
    }

}
