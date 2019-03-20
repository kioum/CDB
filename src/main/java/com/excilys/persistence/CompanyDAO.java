package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.mapper.CompanyMapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;

public class CompanyDAO {
	private static final Logger LOG = LoggerFactory.getLogger(CompanyDAO.class);
	
	private final static String QUERY_GETLIST = "SELECT id, name "
			+ "FROM company";

	public static Optional<ArrayList<Company>> getList(){
		ArrayList<Company> companies = null;
		
		try (Connection conn = DAOFactory.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(QUERY_GETLIST);){
			companies = CompanyMapper.mapList(pstmt.executeQuery());
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
		
		return Optional.ofNullable(companies);
	}
}
