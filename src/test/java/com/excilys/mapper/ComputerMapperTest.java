package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.excilys.model.Company;
import com.excilys.model.Computer;

import junit.framework.TestCase;

public class ComputerMapperTest extends TestCase{

	@Mock
	private ResultSet rs;

	@Override
	@Before
	public void setUp() throws SQLException {
		rs = Mockito.mock(ResultSet.class);
		
		Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
		Mockito.when(rs.getLong("c1.id")).thenReturn(Long.valueOf(1));
		Mockito.when(rs.getString("c1.name")).thenReturn("Test computer");
		Mockito.doReturn(Timestamp.valueOf("1960-09-11 00:11:22"))
		.when(rs).getTimestamp("c1.introduced");
		Mockito.doReturn(Timestamp.valueOf("2020-09-11 00:11:22")).when(rs)
		.getTimestamp("c1.discontinued");
		Mockito.doReturn(Long.valueOf(1)).when(rs).getLong("c2.id");
		Mockito.doReturn("Company").when(rs).getString("c2.name");
	}

	@Test
	public void testMap() {
		Computer comp = ComputerMapper.map(rs);
		
		assertEquals(comp.getId(), Long.valueOf(1));
		assertEquals(comp.getName(), "Test computer");
		assertEquals(comp.getIntroduced(), Timestamp.valueOf("1960-09-11 00:11:22"));
		assertEquals(comp.getDiscontinued(), Timestamp.valueOf("2020-09-11 00:11:22"));
		assertEquals(comp.getManufacturer(), new Company.CompanyBuilder().id(1L).name("Company").build());
	}

	@Test
	public void testMapList() {
		ArrayList<Computer> comp = ComputerMapper.mapList(rs);
		
		assertEquals(comp.size(), 1);
		assertEquals(comp.get(0).getId(), Long.valueOf(1));
		assertEquals(comp.get(0).getName(), "Test computer");
		assertEquals(comp.get(0).getIntroduced(), Timestamp.valueOf("1960-09-11 00:11:22"));
		assertEquals(comp.get(0).getDiscontinued(), Timestamp.valueOf("2020-09-11 00:11:22"));
		assertEquals(comp.get(0).getManufacturer(), new Company.CompanyBuilder().id(1L).name("Company").build());
	}

}
