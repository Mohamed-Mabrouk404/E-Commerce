package com.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSconfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
//	            .allowedOrigins("https://4d94-197-58-80-146.ngrok-free.app") // Specify exact origins
				.allowedOrigins("http://localhost:3000")
				.allowCredentials(true) // Allow credentials (cookies, etc.)
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
				.allowedHeaders("*");
	}
}