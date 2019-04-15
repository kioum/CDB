package com.excilys.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Computer;

@Component
public class ComputerDAO {
	private static final Logger LOG = LoggerFactory.getLogger(ComputerDAO.class);

	private final static String QUERY_GETLIST = "SELECT c1.id, c1.name, c1.introduced, c1.discontinued, c2.id, c2.name " 
			+ "FROM computer c1 LEFT JOIN company c2 " 
			+ "ON c1.company_id = c2.id ;";
	private final static String QUERY_FINDBYID = "SELECT c1.id, c1.name, c1.introduced, c1.discontinued, c2.id, c2.name "
			+ "FROM computer c1 LEFT JOIN company c2 "
			+ "ON c1.company_id = c2.id "
			+ "WHERE c1.id = ?;";
	private final static String QUERY_FINDBYNAME = "SELECT c1.id, c1.name, c1.introduced, c1.discontinued, c2.id, c2.name "
			+ "FROM computer c1 LEFT JOIN company c2 "
			+ "ON c1.company_id = c2.id "
			+ "WHERE c1.name LIKE ? "
			+ "OR c2.name LIKE ?;";
	private final static String QUERY_CREATE = "INSERT INTO computer (name,introduced,discontinued,company_id) "
			+ "VALUES (?, ?, ?, ?);";
	private final static String QUERY_UPDATEBYID = "UPDATE computer "
			+ "SET name = ?, introduced = ?, discontinued = ?, company_id = ? "
			+ "WHERE id = ?;";
	private final static String QUERY_DELETEBYID = "DELETE FROM computer "
			+ "WHERE id = ?;";

	private JdbcTemplate jdbc;

	public ComputerDAO(JdbcTemplate jdbc) {
		System.setProperty("user.timezone", "UTC");
		TimeZone.setDefault(null);
		this.jdbc = jdbc;
	}

	public List<Computer> getList(){
		List<Computer> computers = new ArrayList<Computer>();

		try {
			computers = jdbc.query(QUERY_GETLIST, new ComputerMapper());
		} catch (DataAccessException e) {
			LOG.error(e.getMessage());
		}

		return computers;
	}

	public Optional<Computer> findById(Long id){
		Computer computer = null;

		try {
			computer = jdbc.queryForObject(QUERY_FINDBYID, new Object[] {id}, new ComputerMapper());
		} catch (DataAccessException e) {
			LOG.error(e.getMessage());
		}

		return Optional.ofNullable(computer);
	}

	public List<Computer> findByName(String name){
		List<Computer> computer = new ArrayList<Computer>();

		try {
			computer = jdbc.query(QUERY_FINDBYNAME, new Object[] {"%"+name+"%", "%"+name+"%"}, new ComputerMapper());
		} catch (DataAccessException e) {
			LOG.error(e.getMessage());
		}

		return computer;
	}

	public int create(Computer comp){
		try {
			return jdbc.update(QUERY_CREATE, new Object[] {comp.getName(), comp.getIntroduced(),
					comp.getDiscontinued(), comp.getManufacturer().getId()});
		} catch (DataAccessException e) {
			LOG.error(e.getMessage());
		}

		return -1;
	}

	public int update(Computer comp) {
		try {
			return jdbc.update(QUERY_UPDATEBYID, new Object[] {comp.getName(), comp.getIntroduced(),
					comp.getDiscontinued(), comp.getManufacturer().getId(), comp.getId()});
		} catch (DataAccessException e) {
			LOG.error(e.getMessage());
		}

		return -1;
	}

	public int deleteById(Long id){
		try {
			return jdbc.update(QUERY_DELETEBYID, new Object[] {id});
		} catch (DataAccessException e) {
			LOG.error(e.getMessage());
		}

		return -1;
	}
}
