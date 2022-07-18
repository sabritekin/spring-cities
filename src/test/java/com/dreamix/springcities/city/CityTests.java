package com.dreamix.springcities.city;

import com.dreamix.springcities.city.model.City;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@EnabledIfSystemProperty(named = "spring.profiles.active", matches = "dev")
@RunWith(SpringRunner.class)
public class CityTests {

    private static final Long TEST_ID = 9_999L;
    private static final String TEST_NAME = "Test City";
    private static final String TEST_PHOTO = "Test Photo";
    private City testCity;

    @Before
    public void createTestCity() {
        testCity = new City(TEST_ID, TEST_NAME, TEST_PHOTO);
    }

    @Before
    public void givenCity_whenCityConstructor_thenObjectCreated() {
        assertNotNull(testCity);
    }

    @Test
    public void givenCity_whenIdGetter_thenGetId() {
        assertEquals(TEST_ID, testCity.getId());
    }

    @Test
    public void givenCity_whenNameGetter_thenGetName() {
        assertEquals(TEST_NAME, testCity.getName());
    }

    @Test
    public void givenCity_whenPhotoGetter_thenGetPhoto() {
        assertEquals(TEST_PHOTO, testCity.getPhoto());
    }

    @Test
    public void givenCity_whenIdSetter_thenSetId() {
        Long newTestId = 10_000L;
        testCity.setId(newTestId);
        assertEquals(newTestId, testCity.getId());
    }

    @Test
    public void givenCity_whenNameSetter_thenSetName() {
        String newTestName = "New Test City";
        testCity.setName(newTestName);
        assertEquals(newTestName, testCity.getName());
    }

    @Test
    public void givenCity_whenPhotoSetter_thenSetPhoto() {
        String newTestPhoto = "New Test Photo";
        testCity.setPhoto(newTestPhoto);
        assertEquals(newTestPhoto, testCity.getPhoto());
    }

}
