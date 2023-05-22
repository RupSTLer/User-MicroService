package com.stl.rupam.SchoolWebApp.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
	
	private static final String GET = "GET";
	private static final String POST = "POST";
	private static final String PUT = "PUT";
	private static final String DELETE = "DELETE";
	
	//configuring CORS - Cross Origin Resource Sharing
	@Bean
	WebMvcConfigurer corsConfigurer()
	{
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
//				WebMvcConfigurer.super.addCorsMappings(registry);
				registry.addMapping("/**")    //allow any mapping
//						.allowedOrigins("http://localhost:4200")
						.allowedMethods(GET, POST, PUT, DELETE)    //allowed methods
						.allowedHeaders("*")
//						.exposedHeaders("Authorization")
						.allowedOriginPatterns("*")
						.allowCredentials(true);
			}
		};
	}

}