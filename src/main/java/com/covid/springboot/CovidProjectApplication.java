package com.covid.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.covid.springboot.repository.CovidRepository;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = CovidRepository.class)
@EnableScheduling
public class CovidProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CovidProjectApplication.class, args);
	}

}
