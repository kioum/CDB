package com.excilys.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.excilys.dto.CompanyDTO;
import com.excilys.exception.CompanyException;
import com.excilys.exception.ValidatorException;
import com.excilys.mapper.CompanyMapper;
import com.excilys.model.Company;
import com.excilys.persistence.CompanyDAO;

@Service
public class CompanyService {
	private CompanyDAO companyDAO;
	private MessageSource messageSource;

	private static final Logger LOG = LoggerFactory.getLogger(CompanyService.class);

	public CompanyService(CompanyDAO companyDAO, MessageSource messageSource) {
		this.companyDAO = companyDAO;
		this.messageSource = messageSource;
	}

	public List<CompanyDTO> getAll() {
		return CompanyMapper.mapDTO(companyDAO.getList());
	}

	public Company findById(Long id) throws CompanyException {
		Optional<Company> optionCompany = companyDAO.findById(id);
		if(optionCompany.isPresent())
			return optionCompany.get();
		else {
			throw new CompanyException(messageSource.getMessage("company.id", null, LocaleContextHolder.getLocale()) +
					id + messageSource.getMessage("notfound", null, LocaleContextHolder.getLocale()));
		}
	}

	public void deleteById(Long id) throws ValidatorException {
		try {
			findById(id);
		} catch (CompanyException e) { 
			LOG.error(e.getMessage());
			throw new ValidatorException(messageSource.getMessage("company.id", null, LocaleContextHolder.getLocale()) +
					id + messageSource.getMessage("notexist", null, LocaleContextHolder.getLocale()));
		}	
		companyDAO.deleteById(id);
	}
}
