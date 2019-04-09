package com.excilys.computerdatabase;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.excilys.service.ComputerService;

@Configuration
@ComponentScan({"com.excilys"})
public class AppConfig {
	public static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

	@Bean
	public ComputerService computerService() {
		return new ComputerService();
	}
}