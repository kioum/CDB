package com.excilys.validator;

import com.excilys.model.Computer;

public class ComputerValidator {
	public static boolean isCreatable(Computer comp) {
		if(comp == null) return false;
		
		if(comp.getId() < 0L) return false;
		
		if(comp.getName() == null || comp.getName().equals("")) return false;

		if(comp.getIntroduced() == null && comp.getDiscontinued() != null) return false;
		
		if(comp.getIntroduced() != null && comp.getDiscontinued() != null) 
			if(comp.getIntroduced().after(comp.getDiscontinued())) return false;

		if(!CompanyValidator.iscreatable(comp.getManufacturer())) return false;
			
		return true;
	}
}
