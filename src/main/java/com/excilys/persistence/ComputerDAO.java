package com.excilys.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Computer;
import com.zaxxer.hikari.HikariDataSource;

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
			+ "VALUES (?, ?, ?, ?); ";
	private final static String QUERY_UPDATEBYID = "UPDATE computer "
			+ "SET name = ?, introduced = ?, discontinued = ?, company_id = ? "
			+ "WHERE id = ?;";
	private final static String QUERY_DELETEBYID = "DELETE FROM computer "
			+ "WHERE id = ?;";
	
	private HikariDataSource dataSource;

	public ComputerDAO(HikariDataSource dataSource) {
		System.setProperty("user.timezone", "UTC");
		TimeZone.setDefault(null);
		this.dataSource = dataSource;
	}

	public ArrayList<Computer> getList(){
		ArrayList<Computer> computers = new ArrayList<Computer>();

		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(QUERY_GETLIST);){
			computers = ComputerMapper.mapList(pstmt.executeQuery());
		} catch (SQLException e) {
			LOG.debug(e.getMessage());
		}

		return computers;
	}

	public Optional<Computer> findById(Long id){
		Computer computer = null;

		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(QUERY_FINDBYID);){
			pstmt.setLong(1, id);
			computer = ComputerMapper.map(pstmt.executeQuery());
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}

		return Optional.ofNullable(computer);
	}

	public ArrayList<Computer> findByName(String name){
		ArrayList<Computer> computer = new ArrayList<Computer>();

		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(QUERY_FINDBYNAME);){
			pstmt.setString(1, "%" + name + "%");
			pstmt.setString(2, "%" + name + "%");
			computer = ComputerMapper.mapList(pstmt.executeQuery());
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}

		return computer;
	}

	public int create(Computer comp){
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(QUERY_CREATE);){
			pstmt.setString(1, comp.getName());
			pstmt.setTimestamp(2, comp.getIntroduced());
			pstmt.setTimestamp(3, comp.getDiscontinued());
			pstmt.setLong(4, comp.getManufacturer().getId());
			return pstmt.executeUpdate();

		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}

		return -1;
	}

	public int update(Computer comp) {
		try(Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(QUERY_UPDATEBYID);){
			pstmt.setString(1, comp.getName());
			pstmt.setTimestamp(2, comp.getIntroduced());
			pstmt.setTimestamp(3, comp.getDiscontinued());
			pstmt.setLong(4, comp.getManufacturer().getId());
			pstmt.setLong(5, comp.getId());

			return pstmt.executeUpdate();
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}

		return -1;
	}

	public int deleteById(Long id){
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(QUERY_DELETEBYID);){
			pstmt.setLong(1, id);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}

		return -1;
	}
}
