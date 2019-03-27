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
	private static DAOFactory instance;
	
	private static final Logger LOG = LoggerFactory.getLogger(DAOFactory.class);
	
	private static final String FICHIER_PROPERTIES = "/home/excilys/eclipse-workspace/computer-database/"
			+ "WebContent/WEB-INF/dao.properties";

	private static String url;
	private static String driver;
	private static String username;
	private static String password;

	private DAOFactory(){
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
	
	public Connection getConnection() {		
		try {
			Class.forName(driver);
			return DriverManager.getConnection(url, username, password);
		} catch (SQLException | ClassNotFoundException e) {
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