package com.excilys.validator;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.excilys.exception.ValidatorException;
import com.excilys.model.Company;
import com.excilys.model.Company.CompanyBuilder;

public class CompanyValidatorTest {
	private CompanyBuilder compBuild;
	private CompanyValidator companyValidator;

	@Before
	public void setUp() {
		compBuild = new Company.CompanyBuilder();
		companyValidator = new CompanyValidator(null);
	}

	@Test
	public void testIsWellFormed() {
		testExceptionIsWellFormed(null);
		testExceptionIsWellFormed(compBuild.build());
		testExceptionIsWellFormed(compBuild.id(1L).build());
		
		try {
			companyValidator.isWellFormed(compBuild.name("Test").build());
			assertTrue(true);
		}catch(ValidatorException e) { assertTrue(false); }
	}
	
	public void testExceptionIsWellFormed(Company comp) {
		try {
			companyValidator.isWellFormed(comp);
		}catch(ValidatorException e) {
			assertTrue(true);
		}
	}
}
