package com.dreamix.springcities;

import com.dreamix.springcities.application.controller.CityController;
import com.dreamix.springcities.infrastructure.persistance.repository.CityRepository;
import com.dreamix.springcities.infrastructure.persistance.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnabledIfSystemProperty(named = "spring.profiles.active", matches = "dev")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SpringCitiesApplicationTests {

	@Autowired
	UserRepository userRepository;
	@Autowired
	CityRepository cityRepository;
	@Autowired
	CityController cityController;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
		assertThat(userRepository).isNotNull();
		assertThat(cityRepository).isNotNull();
		assertThat(cityController).isNotNull();
	}

	@Test
	void testCityControllerGet() throws Exception {
		mockMvc.perform(get("/api/cities/get/1"))
				.andExpect(status().isOk());
	}

	@Test
	void testCityControllerGetAll() throws Exception {
		mockMvc.perform(get("/api/cities/get/10/1"))
				.andExpect(status().isOk());
	}

}
