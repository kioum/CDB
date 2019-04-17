package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.excilys.dto.ComputerDTO;
import com.excilys.exception.TimestampException;
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
	public void testMap() throws SQLException {
		Computer comp = ComputerMapper.map(rs);
		
		assertEquals(comp.getId(), Long.valueOf(1));
		assertEquals(comp.getName(), "Test computer");
		assertEquals(comp.getIntroduced(), Timestamp.valueOf("1960-09-11 00:11:22"));
		assertEquals(comp.getDiscontinued(), Timestamp.valueOf("2020-09-11 00:11:22"));
		assertEquals(comp.getManufacturer(), new Company.CompanyBuilder().id(1L).name("Company").build());
	}
	
	@Test
	public void testComputerToDTO() throws SQLException {
		Computer comp = ComputerMapper.map(rs);
		ComputerDTO compDTO = ComputerMapper.computerToDTO(comp);

		assertNotNull(compDTO);
		assertEquals(compDTO.getId(), comp.getId().intValue());
		assertEquals(compDTO.getName(), comp.getName());
		assertEquals(compDTO.getIntroduced(), "1960-09-11");
		assertEquals(compDTO.getDiscontinued(), "2020-09-11");
		assertEquals(compDTO.getManufacturerId(), comp.getManufacturer().getId().longValue());
		assertEquals(compDTO.getManufacturerName(), comp.getManufacturer().getName());
	}

	@Test
	public void testDtoToComputer() throws TimestampException, SQLException {
		ComputerDTO compDTO = ComputerMapper.computerToDTO(ComputerMapper.map(rs));
		Computer comp = ComputerMapper.dtoToComputer(compDTO);

		assertNotNull(comp);
		assertEquals(compDTO.getId(), comp.getId().intValue());
		assertEquals(compDTO.getName(), comp.getName());
		assertEquals(compDTO.getManufacturerId(), comp.getManufacturer().getId().longValue());
		assertEquals(compDTO.getManufacturerName(), comp.getManufacturer().getName());
	}
	
	@Test
	public void testMapListDTO() {
		/*ArrayList<Computer> comp = ComputerMapper.mapList(rs);
		ArrayList<ComputerDTO> compDTO = ComputerMapper.mapListDTO(new ArrayList<Computer>());
		assertEquals(compDTO.size(), 0);
		
		compDTO = ComputerMapper.mapListDTO(comp);
		assertEquals(comp.size(), compDTO.size());
		assertEquals(comp.get(0).getId(), compDTO.get(0).getId());
		assertEquals(comp.get(0).getName(), compDTO.get(0).getName());
		assertEquals(comp.get(0).getManufacturer().getId(), compDTO.get(0).getManufacturerId());
		assertEquals(comp.get(0).getManufacturer().getName(), compDTO.get(0).getManufacturerName());*/
	}
}
