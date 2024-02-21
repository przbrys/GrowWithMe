package com.GrowWithMe.GrowWithMe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories
		(basePackages = {"com.GrowWithMe.GrowWithMe.repository"})
public class GrowWithMeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrowWithMeApplication.class, args);
	}
}
