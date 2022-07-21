package com.dreamix.springcities.city;

import com.dreamix.springcities.city.controller.v1.CityController;
import com.dreamix.springcities.city.application.facade.dto.GetCityDTO;
import com.dreamix.springcities.city.domain.entity.City;
import org.junit.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@EnabledIfSystemProperty(named = "spring.profiles.active", matches = "dev")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CityControllerTests {

	@Autowired
	CityController cityController;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void contextLoads() {
		assertThat(cityController).isNotNull();
		assertThat(mockMvc).isNotNull();
	}

	@Test
	public void givenCityApiURIWithPathVariable_whenMockMVC_thenResponseOK() throws Exception {
		mockMvc.perform(get("/api/v1/city/{id}", 1))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.id").isNotEmpty())
				.andExpect(jsonPath("$.name").isNotEmpty())
				.andExpect(jsonPath("$.photo").isNotEmpty());
	}

	@Test
	public void givenCityApiURIWithInvalidPathVariable_whenMockMVC_thenResponseNotFound() throws Exception {
		mockMvc.perform(get("/api/v1/city/{id}", 2000))
				.andExpect(status().isNotFound());
	}

	@Test
	public void givenCityApiURIWithQueryParameter_whenMockMVC_thenResponseOK() throws Exception {
		mockMvc.perform(get("/api/v1/city")
						.param("page", "0")
						.param("size", "10"))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.count").isNotEmpty())
				.andExpect(jsonPath("$.cityList").isNotEmpty());
	}

	@Test
	public void givenCityApiURIWithInvalidPageQueryParameter_whenMockMVC_thenResponseBadRequest() throws Exception {
		mockMvc.perform(get("/api/v1/city")
						.param("page", "-1")
						.param("size", "10"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void givenCityApiURIWithInvalidSizeQueryParameter_whenMockMVC_thenResponseBadRequest() throws Exception {
		mockMvc.perform(get("/api/v1/city")
						.param("page", "0")
						.param("size", "-1"))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void givenCityApiURIWithSearchQueryParameter_whenMockMVC_thenResponseOK() throws Exception {
		mockMvc.perform(get("/api/v1/city")
						.param("page", "0")
						.param("size", "10")
						.param("searchText", "tokyo"))
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