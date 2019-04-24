package com.excilys.persistence;

import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.model.Computer;

@Component
public class ComputerDAO {
	private static final Logger LOG = LoggerFactory.getLogger(ComputerDAO.class);

	private final static String QUERY_GETLIST = "FROM Computer";
	private final static String QUERY_FINDBYNAME = "FROM Computer c1 WHERE c1.name LIKE :name ";
	private final static String QUERY_DELETEBYID = "DELETE FROM computer "
			+ "WHERE id = ?;";

	private JdbcTemplate jdbc;
	private SessionFactory sessionFactory;
	private Session session;

	public ComputerDAO(JdbcTemplate jdbc, SessionFactory session) {
		System.setProperty("user.timezone", "UTC");
		TimeZone.setDefault(null);
		this.jdbc = jdbc;
		this.sessionFactory = session;
		this.session = session.openSession();
	}

	public List<Computer> getList(){
	    isOpenSession();
	    List<Computer> computers = this.session.createQuery(QUERY_GETLIST, Computer.class).list();
		return computers;
	}

	public Optional<Computer> findById(Long id){
		return Optional.ofNullable(session.get(Computer.class, id));
	}

	public List<Computer> findByName(String name){
		isOpenSession();
	    List<Computer> computers = this.session.createQuery(QUERY_FINDBYNAME, Computer.class)
	    		.setParameter("name", "%"+name+"%").list();
		return computers;
	}

	@Transactional
	public void createOrUpdate(Computer comp){
		isOpenSession();
		Transaction tx = session.getTransaction();
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(comp);
			tx.commit();
		} catch (DataAccessException e) {
			LOG.error(e.getMessage());
		}finally {
			session.close();
		}
	}

	public int deleteById(Long id){
		try {
			return jdbc.update(QUERY_DELETEBYID, new Object[] {id});
		} catch (DataAccessException e) {
			LOG.error(e.getMessage());
		}

		return -1;
	}

	public void isOpenSession() {
		if(!this.session.isOpen()) {
			this.session = this.sessionFactory.openSession();
		}
	}
}
