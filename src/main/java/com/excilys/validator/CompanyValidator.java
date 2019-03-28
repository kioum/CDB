package com.excilys.validator;

import com.excilys.exception.CompanyException;
import com.excilys.exception.ValidatorException;
import com.excilys.model.Company;
import com.excilys.service.CompanyService;

public class CompanyValidator {

	public static void exist(Company comp) throws ValidatorException {
		if(comp == null) 
			throw new ValidatorException("Impossible to have a company with null");

		if(comp.getId() < 0L) 
			throw new ValidatorException("Impossible to have a company with id negative");

		try {
			CompanyService.getInstance().findById(comp.getId());
		}catch(CompanyException e) {
			throw new ValidatorException("Company with id = " + comp.getId() + " doesn't exist");
		}

		if(comp.getName() == null || comp.getName().equals("")) 
			throw new ValidatorException("Impossible to have a company without or with an empty name");

	}
}
