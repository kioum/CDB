package com.excilys.model;

import static org.junit.Assert.assertNotEquals;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;

import com.excilys.model.Computer.ComputerBuilder;

import junit.framework.TestCase;

public class ComputerTest extends TestCase {
	private Computer comp1;
	private ComputerBuilder compBuild;
	private Company manu1;
	
	@Override
	@Before
	public void setUp() {
		manu1 = new Company.CompanyBuilder().id(0L).name("Test company").build();
		
		comp1 = new Computer.ComputerBuilder().id(1L).name("Test Computer").introduced(Timestamp.valueOf("1960-09-11 00:11:22"))
				.discontinued(Timestamp.valueOf("2020-09-11 00:11:22")).manufacturer(manu1).build();
		
		compBuild = new Computer.ComputerBuilder();
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
		
		compBuild.id(1L);
		assertEquals(compBuild.build(), comp1);
		assertEquals(compBuild.build(), compBuild.build());
		
		compBuild.id(0L);
		assertNotEquals(compBuild.build(), comp1);
	}
	
	@Test
	public void testHashCode() {
		assertNotEquals(comp1.hashCode(), compBuild.build().hashCode());
		
		compBuild.id(comp1.getId());
		assertNotEquals(comp1.hashCode(), compBuild.build().hashCode());
		
		compBuild.name(comp1.getName());
		assertNotEquals(comp1.hashCode(), compBuild.build().hashCode());
		
		compBuild.introduced(comp1.getIntroduced());
		assertNotEquals(comp1.hashCode(), compBuild.build().hashCode());
		
		compBuild.discontinued(comp1.getDiscontinued());
		assertNotEquals(comp1.hashCode(), compBuild.build().hashCode());
		
		compBuild.manufacturer(comp1.getManufacturer());
		assertEquals(comp1.hashCode(), compBuild.build().hashCode());
	}
}
