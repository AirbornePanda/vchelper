package de.jsauer.valhalla;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
public class Application {
    public static final String HERO_IMAGE_LOCATION = "/frontend/img/heroes/";
    public static final String GEAR_IMAGE_LOCATION = "/frontend/img/gear/";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
