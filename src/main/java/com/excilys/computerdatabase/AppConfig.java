package com.excilys.computerdatabase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = {"com.excilys.persistence", 
		"com.excilys.service", "com.excilys.controller"})
public class AppConfig {
	private static final String FICHIER_PROPERTIES = "/home/excilys/eclipse-workspace/computer-database/"
			+ "WebContent/WEB-INF/dao.properties";

	@Bean
	public HikariConfig hikariConfig() {
		return new HikariConfig(FICHIER_PROPERTIES);
	}

	@Bean
	public HikariDataSource hikariDataSource() {
		return new HikariDataSource(hikariConfig());
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		JdbcTemplate jdbc = new JdbcTemplate();
		jdbc.setDataSource(hikariDataSource());
		return jdbc;
	}

}