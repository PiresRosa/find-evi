package com.dnd.spaced;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpacedApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpacedApplication.class, args);
	}

}
