package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.excilys.model.Company;

import junit.framework.TestCase;

public class CompanyMapperTest extends TestCase{

	@Mock
	ResultSet rs;

	@BeforeEach
	public void setUp() throws SQLException {
		rs = Mockito.mock(ResultSet.class);
		Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
		Mockito.when(rs.getLong("id")).thenReturn(Long.valueOf(1));
		Mockito.doReturn("Test Company").when(rs).getString("name");
	}

	/*@Test
	public void testMap() {
		Company comp = new CompanyMapper().map(rs);
		
		assertEquals(comp.getId(), Long.valueOf(1));
		assertEquals(comp.getName(), "Test Company");
	}*/

	@Test
	public void testMapList() {
		ArrayList<Company> comp = new CompanyMapper().mapList(rs);
		
		assertEquals(comp.size(), 1);
		assertEquals(comp.get(0).getId(), Long.valueOf(1));
		assertEquals(comp.get(0).getName(), "Test Company");
		
	}
}
