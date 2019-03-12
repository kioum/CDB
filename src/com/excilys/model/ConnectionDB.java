package com.excilys.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDB {
	private Connection conn;

	public ConnectionDB () throws ClassNotFoundException, SQLException 
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/computer-database-db", 
				"admincdb","qwerty1234");
	}

	@Override
	protected void finalize()
	{
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (Exception e) {}
	}
	
	public void drawComputer() throws SQLException {
		Statement s = conn.createStatement();
		String query = "SELECT id, name FROM computer";
		ResultSet res = s.executeQuery(query);
		
		while(res.next()) {
			System.out.println("ID = " + res.getInt("id")  + " name = " + res.getString("NAME"));
		}
	}
}
