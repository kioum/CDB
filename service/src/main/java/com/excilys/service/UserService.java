package com.excilys.service;

import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.excilys.exception.UserException;
import com.excilys.model.Role;
import com.excilys.model.User;
import com.excilys.persistence.UserDAO;

@Service
public class UserService {
	private UserDAO userDAO;
	private MessageSource messageSource;
	
	public UserService(UserDAO userDAO, MessageSource messageSource) {
		this.userDAO = userDAO;
		this.messageSource = messageSource;
	}

	public void create(User user) throws NoSuchMessageException, UserException {
		Optional<User> optionComputer = userDAO.findByName(user.getName());
		if(optionComputer.isPresent()) {
			user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			userDAO.create(user, new Role(user.getName(), "USER")); 		
		}
		else {
			throw new UserException(messageSource.getMessage("user.name", null, LocaleContextHolder.getLocale()) +
					user.getName() + messageSource.getMessage("notfound", null, LocaleContextHolder.getLocale()));
		}

	}
}
