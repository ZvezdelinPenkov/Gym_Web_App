package org.example.gym_web_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "org.example.gym_web_app.model")
@EnableJpaRepositories(basePackages = "org.example.gym_web_app.repository")
@ComponentScan(basePackages = {
		"org.example.gym_web_app.controller",
		"org.example.gym_web_app.service",
		"org.example.gym_web_app.config",
		"org.example.gym_web_app.security",
		"org.example.gym_web_app.util"
})
public class GymWebAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(GymWebAppApplication.class, args);
	}
}