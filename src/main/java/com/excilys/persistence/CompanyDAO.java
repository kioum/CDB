package com.excilys.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.mapper.CompanyMapper;
import com.excilys.model.Company;

@Component
public class CompanyDAO {
	private static final Logger LOG = LoggerFactory.getLogger(CompanyDAO.class);

	private final static String QUERY_GETLIST = "SELECT id, name "
			+ "FROM company";
	private final static String QUERY_FINDBYID = "SELECT id, name "
			+ "FROM company "
			+ "WHERE id = ?";
	private final static String QUERY_DELETECOMPUTERS = "DELETE FROM computer "
			+ "WHERE company_id = ?;";
	private final static String QUERY_DELETEBYID = "DELETE FROM company "
			+ "WHERE id = ?;";

	private JdbcTemplate jdbc;

	public CompanyDAO(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public List<Company> getList(){
		List<Company> companies = new ArrayList<Company>();;

		try {
			companies = jdbc.query(QUERY_GETLIST, new BeanPropertyRowMapper<Company>(Company.class));
		}catch(DataAccessException e) {
			LOG.error(e.getMessage());
		}

		return companies;
	}

	public Optional<Company> findById(long id){
		Company company = new Company();
		
		try {
			company = jdbc.queryForObject(QUERY_FINDBYID, new Object[]{id}, new CompanyMapper());
		}catch(DataAccessException e) {
			LOG.error(e.getMessage());
		}
		
		return Optional.ofNullable(company);
	}

	@Transactional(rollbackFor = {DataAccessException.class})
	public int deleteById(Long id){
		try {
			jdbc.update(QUERY_DELETECOMPUTERS, new Object[]{id});
			return jdbc.update(QUERY_DELETEBYID, new Object[]{id});
		}catch(DataAccessException e) {
			LOG.error(e.getMessage());
		}
		
		return -1;
	}
}
