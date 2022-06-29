/*
 *
 * Initializer class which is used to read city info from a csv file and store them in DB.
 * It also creates a default user.
 * Uses city, user repositories and city, user entity classes.
 *
 */
package com.dreamix.springcities.application;

import com.dreamix.springcities.common.model.City;
import com.dreamix.springcities.common.model.User;
import com.dreamix.springcities.infrastructure.persistance.repository.CityRepository;
import com.dreamix.springcities.infrastructure.persistance.repository.UserRepository;
import com.opencsv.CSVReader;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Component
public class Initializer implements CommandLineRunner {

    @Autowired
    private final CityRepository cityRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private Environment environment;

    @Override
    public void run(String... args) {
        try {
            if(userRepository.findByUserName(environment.getProperty("app.defaultuser.username")).isEmpty()) {
                userRepository.saveAndFlush(new User(environment.getProperty("app.defaultuser.username"), encoder.encode(environment.getProperty("app.defaultuser.password")), environment.getProperty("app.role.editor")));
            }
        } catch (Exception exc) {
            log.error("User could not be created. Terminating application...");
        }

        try {
            InputStream ioStream;
            if ((ioStream = this.getClass().getClassLoader().getResourceAsStream("data/cities.csv")) != null) {
                CSVReader reader = new CSVReader(new InputStreamReader(ioStream));

                // Determine the index for each attribute of City entity.
                int idColumnIndex = -1,
                        nameColumnIndex = -1,
                        photoColumnIndex = -1;
                String[] cityData;
                if ((cityData = reader.readNext()) != null) {
                    for (int i = 0; i < 3; i++) {
                        if (cityData[i].equalsIgnoreCase("id")) {
                            idColumnIndex = i;
                        } else if (cityData[i].equalsIgnoreCase("name")) {
                            nameColumnIndex = i;
                        } else if (cityData[i].equalsIgnoreCase("photo")) {
                            photoColumnIndex = i;
                        }
                    }

                    if (idColumnIndex > -1 && nameColumnIndex > -1 && photoColumnIndex > -1) {
                        List<City> cityList = new ArrayList<>();
                        while ((cityData = reader.readNext()) != null) {
                            cityList.add(new City(Long.parseLong(cityData[idColumnIndex]), cityData[nameColumnIndex], cityData[photoColumnIndex]));
                        }
                        cityRepository.saveAll(cityList);
                    } else {
                        log.error("City data file does not contain one or more column definitions. Terminating application...");

                        System.exit(1);
                    }
                } else {
                    log.error("City data file is empty. Terminating application...");

                    System.exit(1);
                }
            } else {
                log.error("City data file could not be read. Terminating application...");

                System.exit(1);
            }
        } catch (Exception exc) {
            log.error("City data file could not be read. Terminating application...");

            System.exit(1);
        }
    }

}
