package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.ComputerDTO;
import com.excilys.exception.TimestampException;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.util.AttrComputer;
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

	public static ArrayList<ComputerDTO> mapListDTO(ArrayList<Computer> list) {
		ArrayList<ComputerDTO> computers = new ArrayList<ComputerDTO>();

		for(Computer comp:list) {
			String introduced = TimestampConverter.formatToString(comp.getIntroduced(), "yyyy-MM-dd");
			String discontinued = TimestampConverter.formatToString(comp.getDiscontinued(), "yyyy-MM-dd");

			computers.add(new ComputerDTO(comp.getId(), comp.getName(), introduced, 
					discontinued, comp.getManufacturer().getId(), comp.getManufacturer().getName()));

		}

		return computers;
	}

	public static ComputerDTO computerToDTO(Computer comp) {
		String introduced = TimestampConverter.formatToString(comp.getIntroduced(), "yyyy-MM-dd");
		String discontinued = TimestampConverter.formatToString(comp.getDiscontinued(), "yyyy-MM-dd");

		return new ComputerDTO(comp.getId(), comp.getName(), introduced, 
				discontinued, comp.getManufacturer().getId(), comp.getManufacturer().getName());
	}

	public static Computer dtoToComputer(ComputerDTO computerDTO) throws TimestampException {
		Long id = computerDTO.getId();
		String name = computerDTO.getName();
		Timestamp introduced = null;
		Timestamp discontinued = null;

		introduced = TimestampConverter.valueOf(computerDTO.getIntroduced());
		discontinued = TimestampConverter.valueOf(computerDTO.getDiscontinued());

		Company company = new Company.CompanyBuilder().id(computerDTO.getManufacturerId())
				.name(computerDTO.getManufacturerName()).build();

		Computer computer = new Computer.ComputerBuilder().id(id).name(name).introduced(introduced)
				.discontinued(discontinued).manufacturer(company).build();

		return computer;
	}

	public static List<ComputerDTO> sortBy(List<ComputerDTO> list, String attr){
		list.sort(new Comparator<ComputerDTO>(){
			@Override
			public int compare(ComputerDTO arg0, ComputerDTO arg1) {
				switch(AttrComputer.valueOf(attr)) {
				case NAME:
					return arg0.getName().compareTo(arg1.getName());
				case INTRODUCED:
					return arg0.getIntroduced().compareTo(arg1.getIntroduced());
				case DISCONTINUED:
					return arg0.getDiscontinued().compareTo(arg1.getDiscontinued());
				case COMPANYID:
					return arg0.getManufacturerId().compareTo(arg1.getManufacturerId());
				}
				return 0;
			}
		});
		return list;
	}
}
