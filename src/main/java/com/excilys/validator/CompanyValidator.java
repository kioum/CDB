package com.excilys.validator;

import com.excilys.exception.ValidatorException;
import com.excilys.model.Company;

public class CompanyValidator {

	public static void isWellFormed(Company comp) throws ValidatorException {
		if(comp == null) 
			throw new ValidatorException("Impossible to have a company with null");

		if(comp.getId() < 0L) 
			throw new ValidatorException("Impossible to have a company with id negative");
		
		if("".equals(comp.getName()))
			throw new ValidatorException("Impossible to have a company without or with an empty name");

	}
}
