package com.dreamix.springcities.city;

import com.dreamix.springcities.city.application.facade.dto.GetCityDTO;
import com.dreamix.springcities.city.controller.v1.CityController;
import com.dreamix.springcities.city.domain.entity.City;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CityControllerTests {

	private static final Long TEST_GET_CITY_ID = 1L;
	private static final Long TEST_GET_CITY_INVALID_ID = 2000L;
	private static final int TEST_PAGE = 0;
	private static final int TEST_SIZE = 10;
	private static final int INVALID_TEST_PAGE = -1;
	private static final int INVALID_TEST_SIZE = -1;
	private static final String TEST_SEARCH_TEXT = "tokyo";

	@Autowired
	private CityController cityController;
	@Autowired
	private MockMvc mockMvc;

	@Before
	public void contextLoads() {
		assertNotNull(cityController);
		assertNotNull(mockMvc);
	}

	@Test
	public void givenCityApiURIWithPathVariable_whenMockMVC_thenResponseOK() throws Exception {
		mockMvc.perform(get("/api/v1/city/{id}", TEST_GET_CITY_ID))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.id").isNotEmpty())
				.andExpect(jsonPath("$.name").isNotEmpty())
				.andExpect(jsonPath("$.photo").isNotEmpty());
	}

	@Test
	public void givenCityApiURIWithInvalidPathVariable_whenMockMVC_thenResponseNotFound() throws Exception {
		mockMvc.perform(get("/api/v1/city/{id}", TEST_GET_CITY_INVALID_ID))
				.andExpect(status().isNotFound());
	}

	@Test
	public void givenCityApiURIWithQueryParameter_whenMockMVC_thenResponseOK() throws Exception {
		mockMvc.perform(get("/api/v1/city")
						.param("page", String.valueOf(TEST_PAGE))
						.param("size", String.valueOf(TEST_SIZE)))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.count").isNotEmpty())
				.andExpect(jsonPath("$.cityList").isNotEmpty());
	}

	@Test
	public void givenCityApiURIWithInvalidPageQueryParameter_whenMockMVC_thenResponseBadRequest() throws Exception {
		mockMvc.perform(get("/api/v1/city")
						.param("page", String.valueOf(INVALID_TEST_PAGE))
						.param("size", String.valueOf(TEST_SIZE)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void givenCityApiURIWithInvalidSizeQueryParameter_whenMockMVC_thenResponseBadRequest() throws Exception {
		mockMvc.perform(get("/api/v1/city")
						.param("page", String.valueOf(TEST_PAGE))
						.param("size", String.valueOf(INVALID_TEST_SIZE)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void givenCityApiURIWithSearchQueryParameter_whenMockMVC_thenResponseOK() throws Exception {
		mockMvc.perform(get("/api/v1/city")
						.param("page", String.valueOf(TEST_PAGE))
						.param("size", String.valueOf(TEST_SIZE))
						.param("searchText", TEST_SEARCH_TEXT))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.count").isNotEmpty())
				.andExpect(jsonPath("$.cityList").isNotEmpty());
	}

	@Test
	public void givenCityApiURIWithPut_whenMockMVC_thenResponseForbidden() throws Exception {
		City mockCity = new City(1001L, "MockCity", "MockPhoto");

		this.mockMvc.perform(put("/api/v1/city/{id}", 1)
						.contentType(MediaType.APPLICATION_JSON)
						.content(new GetCityDTO(mockCity.getId(), mockCity.getName(), mockCity.getPhoto()).toString()))
				.andExpect(status().isForbidden());
	}

}