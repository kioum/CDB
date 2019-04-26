package com.excilys.persistence;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.model.Company;

@Component
public class CompanyDAO {
	private final static String QUERY_GETLIST = "FROM Company";

	private SessionFactory sessionFactory;
	private Session session;

	public CompanyDAO(SessionFactory session) {
		this.sessionFactory = session;
		this.session = session.openSession();
	}

	public List<Company> getList(){
		isOpenSession();
		List<Company> companies = this.session.createQuery(QUERY_GETLIST, Company.class).list();
		return companies;
	}

	public Optional<Company> findById(long id){
		isOpenSession();
		return Optional.ofNullable(session.get(Company.class, id));
	}

	@Transactional
	public void deleteById(Long id){
		isOpenSession();
		session.beginTransaction();
		session.delete(session.load(Company.class, id));
		session.getTransaction().commit();
		session.close();
	}

	public void isOpenSession() {
		if(!this.session.isOpen()) {
			this.session = this.sessionFactory.openSession();
		}
	}
}
