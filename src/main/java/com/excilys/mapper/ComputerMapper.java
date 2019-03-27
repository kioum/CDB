package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.ComputerDTO;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.util.TimestampConverter;

public abstract class ComputerMapper {
	private static final Logger LOG = LoggerFactory.getLogger(ComputerMapper.class);

	public static Computer map(ResultSet res) {
		Computer comp = null;
		try {
			if(res.next()) {
				Company company = new Company.CompanyBuilder().id(res.getLong("c2.id")).name(res.getString("c2.name")).build();
				comp = new Computer.ComputerBuilder().id(res.getLong("c1.id")).name(res.getString("c1.name"))
						.introduced(res.getTimestamp("c1.introduced")).discontinued(res.getTimestamp("c1.discontinued")) 
						.manufacturer(company).build();
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}

		return comp;
	}

	public static ArrayList<Computer> mapList(ResultSet res) {
		ArrayList<Computer> computers = new ArrayList<Computer>();

		try {
			while(res.next()) {
				Company company = new Company.CompanyBuilder().id(res.getLong("c2.id")).name(res.getString("c2.name")).build();
				computers.add(new Computer.ComputerBuilder().id(res.getLong("c1.id")).name(res.getString("c1.name"))
						.introduced(res.getTimestamp("c1.introduced")).discontinued(res.getTimestamp("c1.discontinued")) 
						.manufacturer(company).build());
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}

		return computers;
	}

	public static ArrayList<ComputerDTO> mapDTO(ArrayList<Computer> list) {
		ArrayList<ComputerDTO> computers = new ArrayList<ComputerDTO>();

		for(Computer comp:list) {
			String introduced = TimestampConverter.formatToString(comp.getIntroduced(), "yyyy-MM-dd");
			String discontinued = TimestampConverter.formatToString(comp.getDiscontinued(), "yyyy-MM-dd");

			computers.add(new ComputerDTO(comp.getId(), comp.getName(), introduced, 
					discontinued, comp.getManufacturer().getId(), comp.getManufacturer().getName()));

		}
		
		return computers;
	}

	public static Computer dtoToComputer(ComputerDTO computerDTO) {
		Long id = computerDTO.getId();
		String name = computerDTO.getName();
		Timestamp introduced = TimestampConverter.valueOf(computerDTO.getIntroduced());
		Timestamp discontinued = TimestampConverter.valueOf(computerDTO.getDiscontinued());
		Company company = null;

		if(computerDTO.getId() > 0L)
			company = new Company.CompanyBuilder().id(computerDTO.getManufacturerId())
			.name(computerDTO.getManufacturerName()).build();

		Computer computer = new Computer.ComputerBuilder().id(id).name(name).introduced(introduced)
				.discontinued(discontinued).manufacturer(company).build();

		return computer;
	}
}
