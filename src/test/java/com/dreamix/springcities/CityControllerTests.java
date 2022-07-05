package com.dreamix.springcities;

import com.dreamix.springcities.city.controller.v1.CityController;
import com.dreamix.springcities.city.dto.CityDTO;
import com.dreamix.springcities.city.model.City;
import com.dreamix.springcities.city.repository.CityRepository;
import com.dreamix.springcities.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@EnabledIfSystemProperty(named = "spring.profiles.active", matches = "dev")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CityControllerTests {

	@Autowired
	UserRepository userRepository;
	@Autowired
	CityRepository cityRepository;
	@Autowired
	CityController cityController;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ModelMapper modelMapper;

	@Test
	void contextLoads() {
		assertThat(userRepository).isNotNull();
		assertThat(cityRepository).isNotNull();
		assertThat(cityController).isNotNull();
		assertThat(mockMvc).isNotNull();
		assertThat(modelMapper).isNotNull();
	}

	@Test
	void givenCityApiURIWithPathVariable_whenMockMVC_thenResponseOK() throws Exception {
		mockMvc.perform(get("/api/v1/city/{id}", 1))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andDo(print())
				.andExpect(jsonPath("$.id").isNotEmpty())
				.andExpect(jsonPath("$.name").isNotEmpty())
				.andExpect(jsonPath("$.photo").isNotEmpty());
	}

	@Test
	void givenCityApiURIWithQueryParameter_whenMockMVC_thenResponseOK() throws Exception {
		mockMvc.perform(get("/api/v1/city")
						.param("page", "0")
						.param("size", "10"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andDo(print())
				.andExpect(jsonPath("$.count").isNotEmpty())
				.andExpect(jsonPath("$.cityList").isNotEmpty());
	}

	@Test
	void givenCityApiURIWithSearchQueryParameter_whenMockMVC_thenResponseOK() throws Exception {
		mockMvc.perform(get("/api/v1/city")
						.param("page", "0")
						.param("size", "10")
						.param("searchText", "tokyo"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json"))
				.andDo(print())
				.andExpect(jsonPath("$.count").isNotEmpty())
				.andExpect(jsonPath("$.cityList").isNotEmpty());
	}

	@Test
	public void givenCityApiURIWithPost_whenMockMVC_thenResponseIsForbidden() throws Exception {
		City mockCity = new City(1001L, "MockCity", "MockPhoto");

		this.mockMvc.perform(put("/api/v1/city/{id}", 1)
						.contentType(MediaType.APPLICATION_JSON)
						.content(modelMapper.map(mockCity, CityDTO.class).toString()))
				.andDo(print())
				.andExpect(status().isForbidden());
	}

}
