package com.excilys.service;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.ComputerDTO;
import com.excilys.exception.CompanyException;
import com.excilys.exception.ComputerException;
import com.excilys.exception.ValidatorException;
import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Computer;
import com.excilys.persistence.ComputerDAO;
import com.excilys.validator.ComputerValidator;

public class ComputerService {
	private ComputerDAO computerDAO;
	private CompanyService companyService;

	private static final Logger LOG = LoggerFactory.getLogger(ComputerService.class);

	public ComputerService(ComputerDAO computerDAO, CompanyService companyService) {
		this.computerDAO = computerDAO;
		this.companyService = companyService;
	}

	public ArrayList<ComputerDTO> getAll() {
		return ComputerMapper.mapListDTO(computerDAO.getList());
	}

	public Computer findById(Long id) throws ComputerException {
		Optional<Computer> optionComputer = computerDAO.findById(id);
		if(optionComputer.isPresent())
			return computerDAO.findById(id).get();
		else throw new ComputerException("Computer id : " + id + " not found !");
	}

	public void create(Computer comp) throws ValidatorException {
		try {
			try {
				findById(comp.getId());
				companyService.findById(comp.getManufacturer().getId());
				throw new ValidatorException("Computer with id = " + comp.getId() + " already exist");
			}catch(CompanyException e) {
				throw new ValidatorException("Company with id = " + comp.getId() + " doesn't exist");
			} catch (ComputerException e) { } 

			ComputerValidator.isWellFormed(comp);
			computerDAO.create(comp);
		} catch (ValidatorException e) {
			LOG.error(e.getMessage());
			throw new ValidatorException(e.getMessage());
		}	
	}

	public void update(Computer comp) throws ValidatorException {
		try {
			findById(comp.getId());

			ComputerValidator.isWellFormed(comp);
			computerDAO.update(comp);
		} catch (ValidatorException e) {
			LOG.error(e.getMessage());
			throw new ValidatorException(e.getMessage());
		}catch (ComputerException e) { 
			LOG.error(e.getMessage());
			throw new ValidatorException("Computer with id = " + comp.getId() + " doesn't exist");
		}	
	}

	public void deleteById(Long id) throws ValidatorException {
		try {
			findById(id);
			computerDAO.deleteById(id);
		} catch (ComputerException e) {
			LOG.error(e.getMessage());
			throw new ValidatorException("Computer with id = " + id + " doesn't exist");
		}
	}

	public ArrayList<ComputerDTO> findByName(String search) {
		return ComputerMapper.mapListDTO(computerDAO.findByName(search));
	}
}
