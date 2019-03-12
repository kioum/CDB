package com.excilys.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class ConnectionDB {
	private Connection conn;
	private HashMap<Long, Computer> computers;
	private HashMap<Long, Company> companies;

	public ConnectionDB () throws ClassNotFoundException, SQLException 
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/computer-database-db", 
				"admincdb","qwerty1234");
		
		computers = new HashMap<Long, Computer>();
		companies = new HashMap<Long, Company>();
	}

	@Override
	protected void finalize()
	{
		try {
			if (conn != null && !conn.isClosed())
				conn.close();
		} catch (Exception e) {}
	}
	
	public HashMap<Long, Computer> listComputer() throws SQLException {
		Statement s = conn.createStatement();
		String query = "SELECT c1.id, c1.name, c1.introduced, c1.discontinued, c2.id, c2.name "
				+ "FROM computer c1, company c2 "
				+ "WHERE c1.company_id = c2.id ;";
		ResultSet res = s.executeQuery(query);

		while(res.next()) {
			Long idManu = res.getLong("c2.id");
			Company manufacturer = null;
			
			if(!companies.containsKey(idManu)) {
				String nameManu = res.getString("c2.name");
				manufacturer = new Company(idManu, nameManu);
				companies.put(idManu, manufacturer);
			}else manufacturer = companies.get(idManu);
			
			Long idComput = res.getLong("c1.id");
			Computer comput = new Computer(idComput, res.getString("c1.name"),
					res.getTimestamp("c1.introduced"), res.getTimestamp("c1.discontinued"), 
					manufacturer);
			
			computers.put(idComput, comput);
		}
		
		return computers;
	}
}
