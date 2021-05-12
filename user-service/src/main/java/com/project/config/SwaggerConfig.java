package com.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	@Bean
	public Docket getDocket() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.project"))
				.build()
				.apiInfo(getApiInfo())
				.useDefaultResponseMessages(false);
	}
	
	public ApiInfo getApiInfo() {
		
		ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
		
		return apiInfoBuilder.title("Users")
				.version("v1")
				.description("User Database Manipulation")
				.license("sandeep.ramesh@ust.com")
				.build();
		
		
	}
}
