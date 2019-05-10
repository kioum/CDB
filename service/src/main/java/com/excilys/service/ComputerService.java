package com.excilys.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.excilys.dto.ComputerDTO;
import com.excilys.exception.ComputerException;
import com.excilys.exception.ValidatorException;
import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Computer;
import com.excilys.persistence.ComputerDAO;

@Service
public class ComputerService {
	private ComputerDAO computerDAO;
	private MessageSource messageSource;
	
	private static final String COMPUTER_ID = "computer.id";

	private static final Logger LOG = LoggerFactory.getLogger(ComputerService.class);

	public ComputerService(ComputerDAO computerDAO, MessageSource messageSource) {
		this.computerDAO = computerDAO;
		this.messageSource = messageSource;
	}

	public List<ComputerDTO> getAll() {
		return ComputerMapper.mapListDTO(computerDAO.getList());
	}

	public Computer findById(Long id) throws ComputerException {
		Optional<Computer> optionComputer = computerDAO.findById(id);
		if(optionComputer.isPresent()) {
			return optionComputer.get();			
		}
		else {
			throw new ComputerException(messageSource.getMessage(COMPUTER_ID, null, LocaleContextHolder.getLocale()) +
					id + messageSource.getMessage("notfound", null, LocaleContextHolder.getLocale()));
		}
	}

	public void create(Computer comp) {
		computerDAO.createOrUpdate(comp); 
	}

	public void update(Computer comp) throws ValidatorException {
		try {
			findById(comp.getId());
			computerDAO.createOrUpdate(comp);
		}catch (ComputerException e) { 
			LOG.error(e.getMessage());
			throw new ValidatorException(messageSource.getMessage(COMPUTER_ID, null, LocaleContextHolder.getLocale()) 
					+ comp.getId() + messageSource.getMessage("notexist", null, LocaleContextHolder.getLocale()));
		}	
	}

	public void deleteById(Long id) throws ValidatorException {
		try {
			findById(id);
			computerDAO.deleteById(id);
		} catch (ComputerException e) {
			LOG.error(e.getMessage());
			throw new ValidatorException(messageSource.getMessage(COMPUTER_ID, null, LocaleContextHolder.getLocale()) 
					+ id + " " + messageSource.getMessage("notexist", null, LocaleContextHolder.getLocale()));
		}
	}

	public List<ComputerDTO> findByName(String search) {
		return ComputerMapper.mapListDTO(computerDAO.findByName(search));
	}
}
