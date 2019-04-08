package com.excilys.service;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.ComputerDTO;
import com.excilys.exception.ComputerException;
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
		return ComputerMapper.mapListDTO(ComputerDAO.getInstance().getList());
	}

	public Computer findById(Long id) throws ComputerException {
		Optional<Computer> optionComputer = ComputerDAO.getInstance().findById(id);
		if(optionComputer.isPresent())
			return ComputerDAO.getInstance().findById(id).get();
		else throw new ComputerException("Computer id : " + id + " not found !");
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

	public void update(Computer comp) throws ValidatorException {
		try {
			ComputerValidator.isUpdatable(comp);
			ComputerDAO.getInstance().update(comp);
		} catch (ValidatorException e) {
			LOG.error(e.getMessage());
			throw new ValidatorException(e.getMessage());
		}
	}

	public void deleteById(Long id) throws ValidatorException {
		// TODO Auto-generated method stub
		try {
			ComputerValidator.isDeletable(id);
			ComputerDAO.getInstance().deleteById(id);
		} catch (ValidatorException e) {
			LOG.error(e.getMessage());
			throw new ValidatorException(e.getMessage());
		}
	}

	public ArrayList<ComputerDTO> findByName(String search) {
		return ComputerMapper.mapListDTO(ComputerDAO.getInstance().findByName(search));
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
