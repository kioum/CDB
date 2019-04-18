package com.excilys.validator;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.excilys.exception.ValidatorException;
import com.excilys.model.Company;

@Component
public class CompanyValidator {
	private MessageSource messageSource;
	
	public CompanyValidator(MessageSource message) {
		this.messageSource = message;
	}
	
	public void isWellFormed(Company comp) throws ValidatorException {
		if(comp == null) 
			throw new ValidatorException(messageSource.getMessage("companyValidator.null", null, LocaleContextHolder.getLocale()));

		if(comp.getId() != null && comp.getId() < 0L) 
			throw new ValidatorException(messageSource.getMessage("companyValidator.id", null, LocaleContextHolder.getLocale()));
		
		if("".equals(comp.getName()))
			throw new ValidatorException(messageSource.getMessage("companyValidator.name", null, LocaleContextHolder.getLocale()));

	}
}
