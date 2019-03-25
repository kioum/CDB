package com.excilys.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public class CompanyTest extends Company {
	Company comp1;
	Company comp2;
	
	@Before
	public void setUp() {
		comp1 = new Company.CompanyBuilder().id(0L).name("Test company").build();		
		comp2 = new Company.CompanyBuilder().id(1L).name("Test company 2").build();
	}
	
	@Test
	public void testEquals() {
		assertEquals(comp1, comp1);
		assertNotEquals(comp1, null);
		assertNotEquals(comp1, new Object());
		assertNotEquals(comp1, comp2);
	}
}
