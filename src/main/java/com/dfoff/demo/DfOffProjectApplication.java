package com.dfoff.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class DfOffProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(DfOffProjectApplication.class, args);
	}

}
