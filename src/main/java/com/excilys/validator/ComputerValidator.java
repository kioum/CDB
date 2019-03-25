package com.excilys.validator;

import com.excilys.model.Computer;

public class ComputerValidator {
	public static boolean isCreatable(Computer comp) {
		if(comp == null) return false;

		if(comp.getIntroduced() != null && comp.getDiscontinued() != null)
			if(comp.getIntroduced().getTime() > comp.getDiscontinued().getTime()) return false;

		return true;
	}
}
