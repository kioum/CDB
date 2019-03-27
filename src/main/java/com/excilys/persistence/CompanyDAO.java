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

public class CompanyDAO {
	private static CompanyDAO instance;
	
	private static final Logger LOG = LoggerFactory.getLogger(CompanyDAO.class);
	
	private final static String QUERY_GETLIST = "SELECT id, name "
			+ "FROM company";
	private final static String QUERY_FINDBYID = "SELECT id, name "
			+ "FROM company "
			+ "WHERE id = ?";

	private CompanyDAO() {}
	
	public ArrayList<Company> getList(){
		ArrayList<Company> companies = new ArrayList<Company>();
		
		try (PreparedStatement pstmt = getConnection().prepareStatement(QUERY_GETLIST);){
			companies = CompanyMapper.mapList(pstmt.executeQuery());
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
		
		return companies;
	}
	
	public Optional<Company> findById(long id){
		Company company = null;
		
		try (PreparedStatement pstmt = getConnection().prepareStatement(QUERY_FINDBYID);){
			pstmt.setLong(1, id);
			company = CompanyMapper.map(pstmt.executeQuery());
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
		
		return Optional.ofNullable(company);
	}
	
	private Connection getConnection() {
		return DAOFactory.getInstance().getConnection();
	}
	
	public static CompanyDAO getInstance() {
        if(instance == null) {
            synchronized (DAOFactory.class) {
                if(instance == null) {
                	instance = new CompanyDAO();
                }
            }
        }
        return instance;
	}
}
