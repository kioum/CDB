package com.excilys.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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
				+ "FROM computer c1 LEFT JOIN company c2 "
				+ "ON c1.company_id = c2.id ;";
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

	public HashMap<Long, Company> listCompany() throws SQLException {
		Statement s = conn.createStatement();
		String query = "SELECT id, name "
				+ "FROM company";
		ResultSet res = s.executeQuery(query);

		while(res.next()) {
			Long idManu = res.getLong("id");

			if(!companies.containsKey(idManu)) {
				String nameManu = res.getString("name");
				companies.put(idManu, new Company(idManu, nameManu));
			}
		}

		return companies;
	}

	public Computer showComputerDetails(Long id) throws SQLException {
		listComputer();

		if(!computers.containsKey(id)) {
			System.err.println("Computer id=" + id + " est introuvable");
		}

		return computers.get(id);
	}

	public Computer createComputer(String name, Timestamp introduced, 
			Timestamp discontinued, Long idManu) throws SQLException {
		Statement s = conn.createStatement();
		String query = "INSERT INTO computer (name,introduced,discontinued,company_id) "
				+ "VALUES ('" + name + "', '" + introduced + "', '" 
				+ discontinued + "', " + idManu + "); ";

		s.executeUpdate(query);

		query = "SELECT max(id) FROM computer";
		ResultSet res = s.executeQuery(query);

		if(!companies.containsKey(idManu)) {
			String nameManu = res.getString("name");
			companies.put(idManu, new Company(idManu, nameManu));
		}

		Computer tmp = new Computer(res.getLong("id"), name, introduced, 
				discontinued, companies.get(idManu));

		return tmp;
	}

	public Computer updateComputer(Computer c) throws SQLException {
		Statement s = conn.createStatement();
		String query = "UPDATE computer "
				+ "SET name = '" + c.getName() + "' "
				+ "AND introduced = '" + c.getIntroduced() + "' "
				+ "AND discontinued = '" + c.getDiscontinued() + "' "
				+ "AND company_id = " + c.getManufacturer().getId() + " "
				+ "WHERE id = " + c.getId() + ";";

		s.executeUpdate(query);
		computers.replace(c.getId(), c);
		
		return computers.get(c.getId());
	}
}
