package com.excilys.service;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.controller.MainController;
import com.excilys.dto.ComputerDTO;
import com.excilys.exception.ValidatorException;
import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Computer;
import com.excilys.persistence.ComputerDAO;
import com.excilys.persistence.DAOFactory;
import com.excilys.validator.ComputerValidator;

public class ComputerService {
	private static ComputerService instance;
	
	private static final Logger LOG = LoggerFactory.getLogger(ComputerService.class);

	private ComputerService() {}

	public ArrayList<ComputerDTO> getAll() {
		return ComputerMapper.mapDTO(ComputerDAO.getInstance().getList());
	}
	
	public Optional<Computer> findById(Long id) {
		return ComputerDAO.getInstance().findById(id);
	}
	

	public void create(Computer comp) throws ValidatorException {
		try {
			ComputerValidator.isCreatable(comp);
			ComputerDAO.getInstance().create(comp);
		} catch (ValidatorException e) {
			LOG.error(e.getMessage());
			throw new ValidatorException(e.getMessage());
		}	
	}
	
	public static ComputerService getInstance() {
		if(instance == null) {
			synchronized (DAOFactory.class) {
				if(instance == null) {
					instance = new ComputerService();
				}
			}
		}
		return instance;
	}
}
