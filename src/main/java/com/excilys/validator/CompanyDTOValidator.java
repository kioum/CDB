package com.excilys.validator;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.excilys.dto.CompanyDTO;

@Component
public class CompanyDTOValidator implements Validator {
	private MessageSource messageSource;
	
	public CompanyDTOValidator(MessageSource message) {
		this.messageSource = message;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return CompanyDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(target == null)
			errors.reject(messageSource.getMessage("companyValidator.null", null, LocaleContextHolder.getLocale()));
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", messageSource.getMessage("computerValidator.id", null, LocaleContextHolder.getLocale()));
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", messageSource.getMessage("computerValidator.name", null, LocaleContextHolder.getLocale()));
		
		CompanyDTO company = (CompanyDTO) target;
		
		if(company.getId() < 0L) 
			errors.reject(messageSource.getMessage("companyValidator.id", null, LocaleContextHolder.getLocale()));
		
	}
}
