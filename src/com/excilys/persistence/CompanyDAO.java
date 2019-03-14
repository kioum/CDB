package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excilys.mapper.CompanyMapper;
import com.excilys.model.Company;

public class CompanyDAO implements IDAO<Company> {
	private final static String QUERY_GETLIST = "SELECT id, name "
			+ "FROM company";
	/*private final static String QUERY_FINDBYID = "";
	private final static String QUERY_CREATE = "";
	private final static String QUERY_UPDATEBYID = "";
	private final static String QUERY_DELETEBYID = "";*/

	@Override
	public ArrayList<Company> getList(){
		try (Connection conn = DAOFactory.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(QUERY_GETLIST);){
			return new CompanyMapper().mapList(pstmt.executeQuery());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Company findById(Long id){
		return null;
	}

	@Override
	public int create(Company comp){
		return 0;
	}

	@Override
	public int update(Company comp) {
		return 0;
	}

	@Override
	public int deleteById(Long id) {
		return 0;
	}

}
