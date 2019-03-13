package com.excilys.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactory {

	private static final String FICHIER_PROPERTIES = "WebContent/WEB-INF/dao.properties";

	private static String url;
	private static String driver;
	private static String username;
	private static String password;

	static {
		Properties config = new Properties();
		try {
			config.load(new FileInputStream(FICHIER_PROPERTIES));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			Class.forName(driver);
			return DriverManager.getConnection(url, username, password);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}