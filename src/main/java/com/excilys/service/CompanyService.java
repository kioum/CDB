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

public class CompanyService {
	private CompanyDAO companyDAO;

	private static final Logger LOG = LoggerFactory.getLogger(CompanyService.class);

	public CompanyService(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	public ArrayList<CompanyDTO> getAll() {
		return CompanyMapper.mapDTO(companyDAO.getList());
	}

	public Company findById(Long id) throws CompanyException {
		Optional<Company> optionCompany = companyDAO.findById(id);
		if(optionCompany.isPresent())
			return companyDAO.findById(id).get();
		else throw new CompanyException("Company id : " + id + " not found !");
	}

	public void deleteById(Long id) throws ValidatorException {
		try {
			findById(id);
		} catch (CompanyException e) { 
			LOG.error(e.getMessage());
			throw new ValidatorException("Company with id = " + id + " doesn't exist");
		}	
		companyDAO.deleteById(id);
	}
}
