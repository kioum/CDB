package com.excilys.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DAOFactory {
	private static DAOFactory instance;

	private static HikariDataSource dataSource;

	private static final Logger LOG = LoggerFactory.getLogger(DAOFactory.class);

	private static final String FICHIER_PROPERTIES = "/home/excilys/eclipse-workspace/computer-database/"
			+ "WebContent/WEB-INF/dao.properties";

	private DAOFactory(){
		Properties config = new Properties();
		try {
			config.load(new FileInputStream(FICHIER_PROPERTIES));
		} catch (FileNotFoundException e) {
			LOG.error(e.getMessage());
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}

		HikariConfig hikariCfg = new HikariConfig(config);
		dataSource = new HikariDataSource(hikariCfg);
	}

	public Connection getConnection() {		
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}

		return null;
	}

	public static DAOFactory getInstance() {
		if(instance == null) {
			synchronized (DAOFactory.class) {
				if(instance == null) {
					instance = new DAOFactory();
				}
			}
		}

		return instance;
	}
}