package com.excilys.computerdatabase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({"com.excilys"})
public class AppConfig {
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
}