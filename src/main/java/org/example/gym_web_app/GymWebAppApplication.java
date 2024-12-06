package org.example.gym_web_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = {"org.example.gym_web_app.repository"})
@EntityScan(basePackages = {"com.example.gym_web_app.model"})
public class GymWebAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(GymWebAppApplication.class, args);
	}
}
