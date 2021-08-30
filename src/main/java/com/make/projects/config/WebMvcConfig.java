package com.make.projects.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	private final long MAX_AGE_SECS = 3600;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") //CORS 적용할 패턴
		.allowedOriginPatterns("*") // 자원 공유할 ORIGIN 지정
		.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
		.allowedHeaders("*")
		.allowCredentials(true)
		.maxAge(MAX_AGE_SECS);
	}
}
