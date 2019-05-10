package com.excilys.persistence;

import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.excilys.model.Company;
import com.excilys.model.Computer;

@Component
public class ComputerDAO {
	private static final String QUERY_GETLIST = "FROM Computer";
	private static final String QUERY_FINDBYNAME = "FROM Computer c1 WHERE c1.name LIKE :name ";

	private SessionFactory sessionFactory;
	private Session session;

	public ComputerDAO(SessionFactory session) {
		System.setProperty("user.timezone", "UTC");
		TimeZone.setDefault(null);
		this.sessionFactory = session;
		this.session = session.openSession();
	}

	public List<Computer> getList(){
		isOpenSession();
		return this.session.createQuery(QUERY_GETLIST, Computer.class).list();
	}

	public Optional<Computer> findById(Long id){
		isOpenSession();
		return Optional.ofNullable(session.get(Computer.class, id));
	}

	public List<Computer> findByName(String name){
		isOpenSession();
		return this.session.createQuery(QUERY_FINDBYNAME, Computer.class)
				.setParameter("name", "%"+name+"%").list();
	}

	public void createOrUpdate(Computer comp){
		isOpenSession();
		
		Computer loadComputer;
		if(comp.getId() != 0L) {
			loadComputer = session.load(Computer.class, comp.getId());
			loadComputer.setName(comp.getName());
			loadComputer.setIntroduced(comp.getIntroduced());
			loadComputer.setDiscontinued(comp.getDiscontinued());
		}else loadComputer = comp;
	
		loadComputer.setManufacturer(session.get(Company.class, comp.getManufacturer().getId()));	
		
		session.beginTransaction();
		session.saveOrUpdate(loadComputer);
		session.clear();
		session.getTransaction().commit();
		session.close();
	}

	public void deleteById(Long id){
		isOpenSession();
		
		session.beginTransaction();
		session.delete(session.load(Computer.class, id));
		session.getTransaction().commit();
		session.close();
	}

	public void isOpenSession() {
		if(!this.session.isOpen()) {
			this.session = this.sessionFactory.openSession();
		}
	}
}
