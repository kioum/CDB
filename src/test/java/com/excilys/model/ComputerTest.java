package com.excilys.model;

import static org.junit.Assert.assertNotEquals;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class ComputerTest extends TestCase {
	Computer comp1;
	Computer comp2;
	
	@Before
	public void setUp() {
		comp1 = new Computer((long)1, "Test computer",  
				Timestamp.valueOf("1960-09-11 00:11:22"), Timestamp.valueOf("2020-09-11 00:11:22"), 
				new Company((long)0, "Test company"));
		
		comp2 = new Computer((long)2, "Test Computer",  
				Timestamp.valueOf("1960-09-11 00:11:22"), Timestamp.valueOf("2020-09-11 00:11:22"), 
				new Company((long)1, "Test company 2S"));
	}
	
	@Test
	public void testEquals() {
		assertEquals(comp1, comp1);
		assertNotEquals(comp1, null);
		assertNotEquals(comp1, new Object());
		assertNotEquals(comp1, comp2);
	}
}
