package com.excilys.validator;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.excilys.exception.ValidatorException;
import com.excilys.model.Computer;

@Component
public class ComputerValidator {
	private MessageSource messageSource;
	private CompanyValidator companyValidator;

	public ComputerValidator(CompanyValidator companyValidator, MessageSource message) {
		this.companyValidator = companyValidator;
		this.messageSource = message;
	}

	public void isWellFormed(Computer comp) throws ValidatorException {	
		if(comp == null) 
			throw new ValidatorException(messageSource.getMessage("computerValidator.null", null, LocaleContextHolder.getLocale()));

		if(comp.getId() < 0L) 
			throw new ValidatorException(messageSource.getMessage("computerValidator.id", null, LocaleContextHolder.getLocale()));

		if("".equals(comp.getName())) 
			throw new ValidatorException(messageSource.getMessage("computerValidator.name", null, LocaleContextHolder.getLocale()));

		if(comp.getIntroduced() == null && comp.getDiscontinued() != null)
			throw new ValidatorException(messageSource.getMessage("computerValidator.withoutIntroduced", null, LocaleContextHolder.getLocale()));

		if(comp.getIntroduced() != null && comp.getDiscontinued() != null) 
			if(comp.getIntroduced().after(comp.getDiscontinued()))
				throw new ValidatorException(messageSource.getMessage("computerValidator.introAfterDisc", null, LocaleContextHolder.getLocale()));

		companyValidator.isWellFormed(comp.getManufacturer());
	}
}
