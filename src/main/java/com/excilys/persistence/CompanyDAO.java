package com.excilys.persistence;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.model.Company;

@Component
public class CompanyDAO {
	private static final Logger LOG = LoggerFactory.getLogger(CompanyDAO.class);

	private final static String QUERY_GETLIST = "FROM Company";
	private final static String QUERY_DELETECOMPUTERS = "DELETE FROM computer "
			+ "WHERE company_id = ?;";
	private final static String QUERY_DELETEBYID = "DELETE FROM company "
			+ "WHERE id = ?;";

	private JdbcTemplate jdbc;
	private SessionFactory sessionFactory;
	private Session session;

	public CompanyDAO(JdbcTemplate jdbc, SessionFactory session) {
		this.jdbc = jdbc;

		this.sessionFactory = session;
		this.session = session.openSession();
	}

	public List<Company> getList(){
		isOpenSession();
		List<Company> companies = this.session.createQuery(QUERY_GETLIST, Company.class).list();
		return companies;
	}

	public Optional<Company> findById(long id){
		return Optional.ofNullable(session.get(Company.class, id));
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

	public void isOpenSession() {
		if(!this.session.isOpen()) {
			this.session = this.sessionFactory.openSession();
		}
	}
}
