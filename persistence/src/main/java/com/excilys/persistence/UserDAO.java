package com.excilys.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.excilys.model.Role;
import com.excilys.model.User;

@Component
public class UserDAO {
	private SessionFactory sessionFactory;
	private Session session;

	public UserDAO(SessionFactory session) {
		this.sessionFactory = session;
		this.session = session.openSession();
	}

	public void create(User user, Role role){
		isOpenSession();
		
		session.beginTransaction();
		session.save(user);
		session.save(role);
		session.getTransaction().commit();
		session.close();
	}

	public void isOpenSession() {
		if(!this.session.isOpen()) {
			this.session = this.sessionFactory.openSession();
		}
	}
}
