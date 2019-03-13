package com.excilys.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excilys.model.Company;
import com.excilys.model.Computer;

public class ComputerDAO implements IDAO<Computer> {
	private final static String QUERY_GETLIST = "SELECT c1.id, c1.name, c1.introduced, c1.discontinued, c2.id, c2.name " 
			+ "FROM computer c1 LEFT JOIN company c2 " 
			+ "ON c1.company_id = c2.id ;";
	private final static String QUERY_FINDBYID = "SELECT c1.id, c1.name, c1.introduced, c1.discontinued, c2.id, c2.name "
			+ "FROM computer c1 LEFT JOIN company c2 "
			+ "ON c1.company_id = c2.id "
			+ "WHERE c1.id = ?;";
	private final static String QUERY_CREATE = "INSERT INTO computer (name,introduced,discontinued,company_id) "
			+ "VALUES (?, ?, ?, ?); ";
	private final static String QUERY_UPDATEBYID = "UPDATE computer "
			+ "SET name = ?, introduced = ?, discontinued = ?, company_id = ? "
			+ "WHERE id = ?;";
	private final static String QUERY_DELETEBYID = "DELETE FROM computer "
			+ "WHERE id = ?;";
	
	@Override
	public ArrayList<Computer> getList(){
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(QUERY_GETLIST);
			ResultSet res = pstmt.executeQuery();

			ArrayList<Computer> computers = new ArrayList<Computer>();

			while(res.next()) {
				computers.add(new Computer(res.getLong("c1.id"), res.getString("c1.name"),
						res.getTimestamp("c1.introduced"), res.getTimestamp("c1.discontinued"), 
						new Company(res.getLong("c2.id"), res.getString("c2.name"))));
			}

			return computers;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Computer findById(Long id){
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(QUERY_FINDBYID);
			pstmt.setLong(1, id);
			ResultSet res = pstmt.executeQuery();

			if(res.next()) {
				return new Computer(res.getLong("c1.id"), res.getString("c1.name"),
						res.getTimestamp("c1.introduced"), res.getTimestamp("c1.discontinued"), 
						new Company(res.getLong("c2.id"), res.getString("c2.name")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println("id invalide");
		return null;
	}

	@Override
	public int create(Computer comp){
		// TODO Auto-generated method stub
		
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(QUERY_CREATE);
			pstmt.setString(1, comp.getName());
			pstmt.setTimestamp(2, comp.getIntroduced());
			pstmt.setTimestamp(3, comp.getDiscontinued());
			pstmt.setLong(4, comp.getManufacturer().getId());
		
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int update(Computer comp) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(QUERY_UPDATEBYID);
			pstmt.setString(1, comp.getName());
			pstmt.setTimestamp(2, comp.getIntroduced());
			pstmt.setTimestamp(3, comp.getDiscontinued());
			pstmt.setLong(4, comp.getManufacturer().getId());
			pstmt.setLong(5, comp.getId());
			
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int deleteById(Long id){
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(QUERY_DELETEBYID);
			pstmt.setLong(1, id);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return -1;
	}

}
