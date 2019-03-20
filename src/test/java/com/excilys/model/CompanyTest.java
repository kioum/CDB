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
		comp1 = new Company(0L, "Test company");		
		comp2 = new Company(1L, "Test company 2S");
	}
	
	@Test
	public void testEquals() {
		assertEquals(comp1, comp1);
		assertNotEquals(comp1, null);
		assertNotEquals(comp1, new Object());
		assertNotEquals(comp1, comp2);
	}
}
