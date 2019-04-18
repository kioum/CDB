package com.excilys.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.excilys.dto.ComputerDTO;
import com.excilys.exception.CompanyException;
import com.excilys.exception.ComputerException;
import com.excilys.exception.ValidatorException;
import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Computer;
import com.excilys.persistence.ComputerDAO;
import com.excilys.validator.ComputerValidator;

@Service
public class ComputerService {
	private ComputerDAO computerDAO;
	private CompanyService companyService;
	private ComputerValidator computerValidator;
	private MessageSource messageSource;

	private static final Logger LOG = LoggerFactory.getLogger(ComputerService.class);

	public ComputerService(ComputerDAO computerDAO, CompanyService companyService, 
			ComputerValidator computerValidator, MessageSource messageSource) {
		this.companyService = companyService;
		this.computerDAO = computerDAO;
		this.computerValidator = computerValidator;
		this.messageSource = messageSource;
	}

	public List<ComputerDTO> getAll() {
		return ComputerMapper.mapListDTO(computerDAO.getList());
	}

	public Computer findById(Long id) throws ComputerException {
		Optional<Computer> optionComputer = computerDAO.findById(id);
		if(optionComputer.isPresent())
			return computerDAO.findById(id).get();
		else {
			throw new ComputerException(messageSource.getMessage("computer.id", null, LocaleContextHolder.getLocale()) +
					id + messageSource.getMessage("notfound", null, LocaleContextHolder.getLocale()));
		}
	}

	public void create(Computer comp) throws ValidatorException {
		try {
			try {
				findById(comp.getId());
				companyService.findById(comp.getManufacturer().getId());
				throw new ValidatorException(messageSource.getMessage("computer.id", null, LocaleContextHolder.getLocale()) 
						+ comp.getId() + messageSource.getMessage("alreadyexist", null, LocaleContextHolder.getLocale()));
			}catch(CompanyException e) {
				throw new ValidatorException(messageSource.getMessage("computer.id", null, LocaleContextHolder.getLocale()) 
						+ comp.getId() + messageSource.getMessage("notexist", null, LocaleContextHolder.getLocale()));
			} catch (ComputerException e) {			
				computerValidator.isWellFormed(comp);
				computerDAO.create(comp); 
			} 

		} catch (ValidatorException e) {
			LOG.error(e.getMessage());
			throw new ValidatorException(e.getMessage());
		}	
	}

	public void update(Computer comp) throws ValidatorException {
		try {
			findById(comp.getId());

			computerValidator.isWellFormed(comp);
			computerDAO.update(comp);
		} catch (ValidatorException e) {
			LOG.error(e.getMessage());
			throw new ValidatorException(e.getMessage());
		}catch (ComputerException e) { 
			LOG.error(e.getMessage());
			throw new ValidatorException(messageSource.getMessage("computer.id", null, LocaleContextHolder.getLocale()) 
					+ comp.getId() + messageSource.getMessage("notexist", null, LocaleContextHolder.getLocale()));
		}	
	}

	public void deleteById(Long id) throws ValidatorException {
		try {
			findById(id);
			computerDAO.deleteById(id);
		} catch (ComputerException e) {
			LOG.error(e.getMessage());
			throw new ValidatorException(messageSource.getMessage("computer.id", null, LocaleContextHolder.getLocale()) 
					+ id + messageSource.getMessage("notexist", null, LocaleContextHolder.getLocale()));
		}
	}

	public ArrayList<ComputerDTO> findByName(String search) {
		return ComputerMapper.mapListDTO(computerDAO.findByName(search));
	}
}
