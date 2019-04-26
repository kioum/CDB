package com.excilys.computerdatabase;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = {"com.excilys.persistence", "com.excilys.validator", 
		"com.excilys.service", "com.excilys.controller"})
@PropertySource(value = { "classpath:dao.properties" })
public class AppConfig {

	@Autowired
	private Environment env;

	@Bean
	public HikariConfig hikariConfig() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(env.getRequiredProperty("jdbcUrl"));
		dataSource.setUsername(env.getRequiredProperty("dataSource.user"));
		dataSource.setPassword(env.getRequiredProperty("dataSource.password"));
		dataSource.setDriverClassName(env.getRequiredProperty("driverClassName"));

		return dataSource;
	}

	@Bean
	public HikariDataSource hikariDataSource() {
		return new HikariDataSource(hikariConfig());
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(hikariDataSource());
		sessionFactory.setPackagesToScan("com.excilys.model");
		sessionFactory.setAnnotatedClasses(Computer.class);
		sessionFactory.setAnnotatedClasses(Company.class);
		sessionFactory.setHibernateProperties(hibernateProperties());

		return sessionFactory;
	}

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		properties.put("hibernate.format_sql", env.getProperty("hibernate.format_sql"));

		return properties;
	}
	
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory s) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(s);
		return txManager;
	}
}