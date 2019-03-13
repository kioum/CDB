package com.excilys.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excilys.model.Company;

public class CompanyDAO implements IDAO<Company> {
	private final static String QUERY_GETLIST = "SELECT id, name "
			+ "FROM company";
	private final static String QUERY_FINDBYID = "";
	private final static String QUERY_CREATE = "";
	private final static String QUERY_UPDATEBYID = "";
	private final static String QUERY_DELETEBYID = "";

	@Override
	public ArrayList<Company> getList() throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = conn.prepareStatement(QUERY_GETLIST);
		ResultSet res = pstmt.executeQuery();

		ArrayList<Company> companies = new ArrayList<Company>();

		while(res.next()) {
			companies.add(new Company(res.getLong("id"), res.getString("name")));
		}

		return companies;
	}

	@Override
	public Company findById(Long id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int create(Company comp) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Company comp) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(Long id) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

}
