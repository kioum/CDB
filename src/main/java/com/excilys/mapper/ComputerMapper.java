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
import com.excilys.persistence.CompanyDAO;

public class ComputerMapper {
	private static final Logger LOG = LoggerFactory.getLogger(ComputerMapper.class);

	public static Computer map(ResultSet res) {
		try {
			if(res.next())
				return new Computer(res.getLong("c1.id"), res.getString("c1.name"),
						res.getTimestamp("c1.introduced"), res.getTimestamp("c1.discontinued"), 
						new Company(res.getLong("c2.id"), res.getString("c2.name")));
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		};

		return null;
	}

	public static ArrayList<Computer> mapList(ResultSet res) {
		ArrayList<Computer> computers = new ArrayList<Computer>();

		try {
			while(res.next()) {
				computers.add(new Computer(res.getLong("c1.id"), res.getString("c1.name"),
						res.getTimestamp("c1.introduced"), res.getTimestamp("c1.discontinued"), 
						new Company(res.getLong("c2.id"), res.getString("c2.name"))));
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}

		return computers;
	}

	public static ArrayList<ComputerDTO> mapDTO(ArrayList<Computer> list) {
		ArrayList<ComputerDTO> computers = new ArrayList<ComputerDTO>();

		for(Computer comp:list)
			computers.add(new ComputerDTO(comp.getId(), comp.getName(), String.valueOf(comp.getIntroduced()), 
					String.valueOf(comp.getDiscontinued()), comp.getManufacturer().getId()));

		return computers;
	}

	public static Computer dtoToComputer(ComputerDTO computerDTO) {
		Long id = computerDTO.getId();
		String name = computerDTO.getName();
		
		Timestamp introduced = null;
		if(!computerDTO.getIntroduced().equals(""))
			introduced = Timestamp.valueOf(computerDTO.getIntroduced());
		
		Timestamp discontinued = null;
		if(!computerDTO.getDiscontinued().equals(""))
			discontinued = Timestamp.valueOf(computerDTO.getDiscontinued());
		
		Company comp = CompanyDAO.findById(computerDTO.getManufacturer()).get();

		return new Computer(id, name, introduced, discontinued, comp);
	}
}
