package com.excilys.validator;

import com.excilys.exception.ValidatorException;
import com.excilys.model.Computer;

public class ComputerValidator {

	public static void isWellFormed(Computer comp) throws ValidatorException {
		if(comp == null) 
			throw new ValidatorException("Impossible to create a computer with null");

		if(comp.getId() < 0L) 
			throw new ValidatorException("Impossible to create a computer with id negative");

		if("".equals(comp.getName())) 
			throw new ValidatorException("Impossible to create a computer without or with an empty name");

		if(comp.getIntroduced() == null && comp.getDiscontinued() != null) 
			throw new ValidatorException("Impossible to create a computer with a Discontinued Date"
					+ " and without Introduced Date");

		if(comp.getIntroduced() != null && comp.getDiscontinued() != null) 
			if(comp.getIntroduced().after(comp.getDiscontinued())) 
				throw new ValidatorException("Impossible to create a computer with a Introduced Date"
						+ " after the Discontinued Date");
		
		CompanyValidator.isWellFormed(comp.getManufacturer());
	}
}
