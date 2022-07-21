package com.dreamix.springcities.city;

import com.dreamix.springcities.city.domain.entity.City;
import com.dreamix.springcities.city.domain.service.GetCityService;
import com.dreamix.springcities.city.domain.service.SaveCityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CityServiceTests {

    private static final Long TEST_ID = 9_999L;
    private static final String TEST_NAME = "Test City";
    private static final String TEST_PHOTO = "Test Photo";
    private static final Long TEST_GET_CITY_ID = 1_005L;
    private static final String TEST_SEARCH_TEXT = "tokyo";
    private City testCity;

    @Autowired
    private SaveCityService saveCityService;
    @Autowired
    private GetCityService getCityService;

    @Before
    public void contextLoads() {
        assertNotNull(saveCityService);
        assertNotNull(getCityService);
    }

    @Before
    public void saveTestCity() {
        testCity = new City(TEST_ID, TEST_NAME, TEST_PHOTO);
        assertNotNull(saveCityService.save(testCity));
    }

    /* SaveCityService */

    @Test
    public void givenCity_whenSaveCity_thenCityNotNull() {
        assertNotNull(saveCityService.save(testCity));
    }

    /* GetCityService */

    @Test
    public void givenId_whenGetCity_thenCityNotNull() {
        assertNotNull(getCityService.get(TEST_GET_CITY_ID));
    }

    @Test
    public void given_whenGetCount_thenCountGreaterThanZero() {
        assertTrue(getCityService.getCount() > 0);
    }

    @Test
    public void given_whenGetCountOfSearchResults_thenCountGreaterThanZero() {
        assertTrue(getCityService.getCountOfSearchResults(TEST_SEARCH_TEXT) > 0);
    }

    @Test
    public void given_whenAll_thenListNotEmpty() {
        isNotEmpty(getCityService.getAll(0, 10));
    }

    @Test
    public void given_whenGetAllWithSearchText_thenListNotEmpty() {
        isNotEmpty(getCityService.getAll(0, 10, TEST_SEARCH_TEXT));
    }

}
