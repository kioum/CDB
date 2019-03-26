package com.excilys.validator;

import com.excilys.model.Company;

public class CompanyValidator {

	public static boolean iscreatable(Company comp) {
		if(comp == null) return false;

		if(comp.getId() < 0L) return false;

		if(comp.getName() == null || comp.getName().equals("")) return false;

		return true;
	}

}
