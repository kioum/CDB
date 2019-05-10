package com.excilys.validator;

import java.sql.Timestamp;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.excilys.dto.CompanyDTO;
import com.excilys.dto.ComputerDTO;
import com.excilys.exception.TimestampException;
import com.excilys.util.TimestampConverter;

@Component
public class ComputerDTOValidator implements Validator{
	private MessageSource messageSource;
	private CompanyDTOValidator companyValidator;

	public ComputerDTOValidator(CompanyDTOValidator companyValidator, MessageSource message) {
		this.companyValidator = companyValidator;
		this.messageSource = message;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return ComputerDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(target == null)
			errors.reject(messageSource.getMessage("computerValidator.null", null, LocaleContextHolder.getLocale()));

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", messageSource.getMessage("computerValidator.id", null, LocaleContextHolder.getLocale()));
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", messageSource.getMessage("computerValidator.name", null, LocaleContextHolder.getLocale()));

		ComputerDTO computer = (ComputerDTO) target;

		if(computer == null) 
			return;
		
		if(computer.getId() < 0L) 
			errors.reject(messageSource.getMessage("computerValidator.id", null, LocaleContextHolder.getLocale()));

		if("".equals(computer.getIntroduced()) && !"".equals(computer.getDiscontinued()))
			errors.reject(messageSource.getMessage("computerValidator.withoutIntroduced", null, LocaleContextHolder.getLocale()));

		if(!"".equals(computer.getIntroduced()) && !"".equals(computer.getDiscontinued())) {
			try {
				Timestamp introduced = TimestampConverter.valueOf(computer.getIntroduced());
				Timestamp discontinued = TimestampConverter.valueOf(computer.getDiscontinued());
				if(introduced.after(discontinued))
					errors.reject(messageSource.getMessage("computerValidator.introAfterDisc", null, LocaleContextHolder.getLocale()));
			} catch (TimestampException e) {
				errors.reject(e.getMessage());
			}
		}

		companyValidator.validate(new CompanyDTO(computer.getManufacturerId(), computer.getManufacturerName()), errors);
	}
}
