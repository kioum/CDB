package com.excilys.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

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

	public ArrayList<Computer> listComputer() throws SQLException {
		Statement s = conn.createStatement();
		String query = "SELECT c1.id, c1.name, c1.introduced, c1.discontinued, c2.id, c2.name "
				+ "FROM computer c1 LEFT JOIN company c2 "
				+ "ON c1.company_id = c2.id ;";
		ResultSet res = s.executeQuery(query);

		ArrayList<Computer> computers = new ArrayList<Computer>();

		while(res.next()) {
			computers.add(new Computer(res.getLong("c1.id"), res.getString("c1.name"),
					res.getTimestamp("c1.introduced"), res.getTimestamp("c1.discontinued"), 
					new Company(res.getLong("c2.id"), res.getString("c2.name"))));
		}

		return computers;
	}

	public ArrayList<Company> listCompany() throws SQLException {
		Statement s = conn.createStatement();
		String query = "SELECT id, name "
				+ "FROM company";
		ResultSet res = s.executeQuery(query);

		ArrayList<Company> companies = new ArrayList<Company>();

		while(res.next()) {
			companies.add(new Company(res.getLong("id"), res.getString("name")));
		}

		return companies;
	}

	public Computer showComputerDetails(Long id) throws SQLException {
		Statement s = conn.createStatement();
		String query = "SELECT c1.id, c1.name, c1.introduced, c1.discontinued, c2.id, c2.name "
				+ "FROM computer c1 LEFT JOIN company c2 "
				+ "ON c1.company_id = c2.id "
				+ "WHERE c1.id = " + id;
		ResultSet res = s.executeQuery(query);

		if(res.next()) {
			return new Computer(res.getLong("c1.id"), res.getString("c1.name"),
					res.getTimestamp("c1.introduced"), res.getTimestamp("c1.discontinued"), 
					new Company(res.getLong("c2.id"), res.getString("c2.name")));
		}
		
		System.err.println("id invalide");
		return null;
	}

	public int createComputer(String name, Timestamp introduced, 
			Timestamp discontinued, Long idManu) throws SQLException {
		Statement s = conn.createStatement();
		String query = "INSERT INTO computer (name,introduced,discontinued,company_id) "
				+ "VALUES ('" + name + "', '" + introduced + "', '" 
				+ discontinued + "', " + idManu + "); ";

		return s.executeUpdate(query);
	}
	
	public int updateComputer(Computer c) throws SQLException {
		Statement s = conn.createStatement();
		String query = "UPDATE computer "
				+ "SET name = '" + c.getName() + "' "
				+ "AND introduced = '" + c.getIntroduced() + "' "
				+ "AND discontinued = '" + c.getDiscontinued() + "' "
				+ "AND company_id = " + c.getManufacturer() + " "
				+ "WHERE id = " + c.getId() + ";";

		return s.executeUpdate(query);
	}

	public int deleteComputer(Long id) throws SQLException {
		Statement s = conn.createStatement();
		String query = "DELETE FROM computer "
				+ "WHERE id = " + id + ";";

		return s.executeUpdate(query);
	}
}
