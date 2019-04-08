package com.excilys.service;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.CompanyDTO;
import com.excilys.exception.CompanyException;
import com.excilys.exception.ValidatorException;
import com.excilys.mapper.CompanyMapper;
import com.excilys.model.Company;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.DAOFactory;
import com.excilys.validator.CompanyValidator;

public class CompanyService {
	private static CompanyService instance;

	private static final Logger LOG = LoggerFactory.getLogger(CompanyService.class);

	private CompanyService() {}

	public ArrayList<CompanyDTO> getAll() {
		return CompanyMapper.mapDTO(CompanyDAO.getInstance().getList());
	}

	public Company findById(Long id) throws CompanyException {
		Optional<Company> optionCompany = CompanyDAO.getInstance().findById(id);
		if(optionCompany.isPresent())
			return CompanyDAO.getInstance().findById(id).get();
		else throw new CompanyException("Company id : " + id + " not found !");
	}

	public void deleteById(Long id) throws ValidatorException {
		try {
			CompanyValidator.isDeletable(id);
			CompanyDAO.getInstance().deleteById(id);
		} catch (ValidatorException e) {
			LOG.error(e.getMessage());
			throw new ValidatorException(e.getMessage());
		}
	}

	public static CompanyService getInstance() {
		if(instance == null) {
			synchronized (DAOFactory.class) {
				if(instance == null) {
					instance = new CompanyService();
				}
			}
		}
		
		return instance;
	}
}
