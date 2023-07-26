package com.dfoff.demo;

import org.apache.logging.log4j.util.ProcessIdUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
@EntityScan(basePackages = {"com.dfoff.demo.domain"})
public class DfOffProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(DfOffProjectApplication.class, args);
	}

}
