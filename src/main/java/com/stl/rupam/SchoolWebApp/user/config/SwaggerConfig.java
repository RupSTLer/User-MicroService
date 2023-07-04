package com.stl.rupam.SchoolWebApp.user.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	
	@Bean
	Docket api()
	{
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("School Management System")
				.apiInfo(getInfo())
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
				
	}

	private ApiInfo getInfo() {
		return new ApiInfo(
				"User Microservice", 
				"Microservice that handles User service request URLs", 
				"1.0", 
				"Terms of service", 
				new Contact("Rupam Chakraborty", "", "rupam.chakraborty@stl.tech"), 
				"license of APIs", 
				"API license URLs", 
				Collections.emptyList());
	}

}
