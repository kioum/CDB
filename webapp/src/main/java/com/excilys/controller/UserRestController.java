package com.excilys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.exception.UserException;
import com.excilys.model.User;
import com.excilys.service.UserService;

@RestController
@RequestMapping(path = "/users", produces = "application/json")
public class UserRestController {
	private UserService userService;
	private static final Logger LOG = LoggerFactory.getLogger(UserRestController.class);

	public UserRestController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<User> createComputer(@Validated @RequestBody User user){
		try {
			userService.create(user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (NoSuchMessageException | UserException e) {
			LOG.error(e.getMessage());
			return new ResponseEntity<User>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
