package com.example.cardentity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class CardentityApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardentityApplication.class, args);
	}

	@Bean
	WebMvcConfigurer Configuration() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOriginPatterns("*")
						.allowedHeaders("*").maxAge(0).allowCredentials(true);
			}
		};
	}
}
