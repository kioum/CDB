package com.excilys.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DAOFactory {
	private static final Logger LOG = LoggerFactory.getLogger(DAOFactory.class);
	
	private static final String FICHIER_PROPERTIES = "/home/excilys/eclipse-workspace/computer-database/"
			+ "WebContent/WEB-INF/dao.properties";

	private static String url;
	private static String driver;
	private static String username;
	private static String password;

	static {
		Properties config = new Properties();
		try {
			config.load(new FileInputStream(FICHIER_PROPERTIES));
		} catch (FileNotFoundException e) {
			LOG.error(e.getMessage());
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}

		driver = config.getProperty("jdbc.driver");
		url = config.getProperty("jdbc.url");
		username = config.getProperty("jdbc.username");
		password = config.getProperty("jdbc.password");
	}
	
	/**
	 * get the instance of the database
	 * @return Connection
	 */
	public static Connection getConnection() {
		try {
			if(driver == null) System.out.println("null");
			Class.forName(driver);
			return DriverManager.getConnection(url, username, password);
		} catch (SQLException | ClassNotFoundException e) {
			LOG.error(e.getMessage());
		}
		return null;
	}
}