package com.app.lms;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EnableJpaRepositories(basePackages = {"com.app.lms"})
public class AppConfiguration {
	
	
}
