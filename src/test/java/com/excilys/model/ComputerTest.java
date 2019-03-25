package com.excilys.model;

import static org.junit.Assert.assertNotEquals;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class ComputerTest extends TestCase {
	Computer comp1;
	Computer comp2;
	
	@Override
	@Before
	public void setUp() {
		Company manu1 = new Company.CompanyBuilder().id(0L).name("Test company").build();		
		Company manu2 = new Company.CompanyBuilder().id(1L).name("Test company 2").build();
		
		comp1 = new Computer.ComputerBuilder().id(1L).name("Test Computer").introduced(Timestamp.valueOf("1960-09-11 00:11:22"))
				.discontinued(Timestamp.valueOf("2020-09-11 00:11:22")).manufacturer(manu1).build();
		
		comp2 = new Computer.ComputerBuilder().id(2L).name("Test Computer").introduced(Timestamp.valueOf("1960-09-11 00:11:22"))
				.discontinued(Timestamp.valueOf("2020-09-11 00:11:22")).manufacturer(manu2).build();
	}
	
	@Test
	public void testEquals() {
		assertEquals(comp1, comp1);
		assertNotEquals(comp1, null);
		assertNotEquals(comp1, new Object());
		assertNotEquals(comp1, comp2);
	}
}
