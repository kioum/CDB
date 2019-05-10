package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.ComputerDTO;
import com.excilys.exception.TimestampException;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.util.AttrComputer;
import com.excilys.util.TimestampConverter;

public class ComputerMapper {
	private static final Logger LOG = LoggerFactory.getLogger(ComputerMapper.class);
	
	private ComputerMapper() {

	}

	public static Computer map(ResultSet res) throws SQLException {
		Company company = new Company.CompanyBuilder()
				.id(res.getLong("c2.id"))
				.name(res.getString("c2.name")).build();

		return new Computer.ComputerBuilder()
				.id(res.getLong("c1.id"))
				.name(res.getString("c1.name"))
				.introduced(res.getTimestamp("c1.introduced"))
				.discontinued(res.getTimestamp("c1.discontinued")) 
				.manufacturer(company).build();
	}

	public static List<ComputerDTO> mapListDTO(List<Computer> list) {
		String format = "yyyy-MM-dd";
		List<ComputerDTO> computers = new ArrayList<>();

		for(Computer comp:list) {
			String introduced = TimestampConverter.formatToString(comp.getIntroduced(), format);
			String discontinued = TimestampConverter.formatToString(comp.getDiscontinued(), format);

			if(comp.getManufacturer() == null)
				comp.setManufacturer(new Company.CompanyBuilder().id(0).build());

			computers.add(new ComputerDTO(comp.getId(), comp.getName(), introduced, 
					discontinued, comp.getManufacturer().getId(), comp.getManufacturer().getName()));

		}

		return computers;
	}

	public static List<ComputerDTO> hashMaptoDTO(List<HashMap<String, ?>> compJson) {
		List<ComputerDTO> computers = new ArrayList<>();

		for(HashMap<String, ?> json: compJson)
			try {
				computers.add(new ComputerDTO((long)json.get("id"), (String)json.get("name"), 
						(String)json.get("introduced"), (String)json.get("discontinued"), 
						(long)json.get("manufacturerId"), (String)json.get("manufacturerName")));
			}catch(ClassCastException e ) {
				LOG.error("Response of list Computers isn't a list of computerDTO");
				return new ArrayList<>();
			}
		return computers;
	}


	public static ComputerDTO computerToDTO(Computer comp) {
		String format = "yyyy-MM-dd";
		String introduced = TimestampConverter.formatToString(comp.getIntroduced(), format);
		String discontinued = TimestampConverter.formatToString(comp.getDiscontinued(), format);

		if(comp.getManufacturer() != null)
			return new ComputerDTO(comp.getId(), comp.getName(), introduced, 
					discontinued, comp.getManufacturer().getId(), comp.getManufacturer().getName());
		else return new ComputerDTO(comp.getId(), comp.getName(), introduced, discontinued, 0L, null);
	}

	public static Computer dtoToComputer(ComputerDTO computerDTO) throws TimestampException {
		String name = computerDTO.getName();

		Timestamp introduced = TimestampConverter.valueOf(computerDTO.getIntroduced());
		Timestamp discontinued = TimestampConverter.valueOf(computerDTO.getDiscontinued());

		Company company = new Company.CompanyBuilder().id(computerDTO.getManufacturerId())
				.name(computerDTO.getManufacturerName()).build();

		return new Computer.ComputerBuilder().id(computerDTO.getId()).name(name).introduced(introduced)
				.discontinued(discontinued).manufacturer(company).build();
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
				case COMPANYNAME:
					String str1 = arg0.getManufacturerName() == null ? "" : arg0.getManufacturerName();
					String str2 = arg1.getManufacturerName() == null ? "" : arg1.getManufacturerName();
					return str1.compareTo(str2);
				}
				return 0;
			}
		});
		return list;
	}
}
