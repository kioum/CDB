package com.excilys.computerdatabase;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ComputerDAO;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({"com.excilys"})
public class AppConfig {
	public static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
	private static final String FICHIER_PROPERTIES = "/home/excilys/eclipse-workspace/computer-database/"
			+ "WebContent/WEB-INF/dao.properties";
	
	@Bean
	public HikariConfig HikariConfig() {
		return new HikariConfig(FICHIER_PROPERTIES);
	}

	@Bean
	public HikariDataSource hikariDataSource() {
		return new HikariDataSource(HikariConfig());
	}

	@Bean
	public ComputerService computerService() {
		return new ComputerService(computerDAO(), companyService());
	}

	@Bean
	public CompanyService companyService() {
		return new CompanyService(companyDAO());
	}

	@Bean
	public ComputerDAO computerDAO() {
		return new ComputerDAO(hikariDataSource());
	}

	@Bean
	public CompanyDAO companyDAO() {
		return new CompanyDAO(hikariDataSource());
	}
}