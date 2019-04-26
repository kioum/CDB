package com.excilys.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

public class CompanyTest extends Company {
	private Company comp1;
	private CompanyBuilder compBuild;
	
	@Before
	public void setUp() {
		comp1 = new Company.CompanyBuilder().id(0L).name("Test company").build();
		compBuild = new Company.CompanyBuilder();
	}
	
	@Test
	public void testEquals() {
		assertEquals(comp1, comp1);
		assertNotEquals(comp1, null);
		assertNotEquals(comp1, new Object());
		
		assertNotEquals(comp1, compBuild.build());
	}
	
	@Test
	public void testEqualsWithOther() {
		assertNotEquals(compBuild.build(), comp1);
		assertEquals(compBuild.build(), compBuild.build());
		
		compBuild.id(0L);
		assertNotEquals(compBuild.build(), comp1);
		
		compBuild.name("Test company");
		assertEquals(compBuild.build(), comp1);
	}
	
	@Test
	public void testHashCode() {
		assertNotEquals(comp1.hashCode(), compBuild.build().hashCode());
		
		compBuild.id(comp1.getId());
		assertNotEquals(comp1.hashCode(), compBuild.build().hashCode());
		
		compBuild.name(comp1.getName());
		assertEquals(comp1.hashCode(), compBuild.build().hashCode());
	}
}
