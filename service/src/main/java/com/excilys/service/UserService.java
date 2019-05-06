package com.excilys.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.excilys.model.Role;
import com.excilys.model.User;
import com.excilys.persistence.UserDAO;

@Service
public class UserService {
	private UserDAO userDAO;
	
	public UserService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public void create(User user) {
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		userDAO.create(user, new Role(user.getName(), "USER")); 
	}
}
