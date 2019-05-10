package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.CompanyDTO;
import com.excilys.model.Company;

public class CompanyMapper {
	private static final Logger LOG = LoggerFactory.getLogger(CompanyMapper.class);
	
	private CompanyMapper() {
		
	}
	
	public static Company map(ResultSet res) throws SQLException {
		return new Company.CompanyBuilder()
				.id(res.getLong("id"))
				.name(res.getString("name")).build();
	}

	public static List<CompanyDTO> mapDTO(List<Company> list) {
		ArrayList<CompanyDTO> companies = new ArrayList<>();

		for(Company comp:list)
			companies.add(new CompanyDTO(comp.getId(), comp.getName()));

		return companies;
	}
	
	public static List<CompanyDTO> hashMaptoDTO(List<HashMap<String, ?>> compJson) {
		List<CompanyDTO> companies = new ArrayList<>();

		for(HashMap<String, ?> json: compJson)
			try {
				companies.add(new CompanyDTO((long)json.get("id"), (String)json.get("name")));
			}catch(ClassCastException e ) {
				LOG.error("Response of list Companies isn't a list of companyDTO");
				return null;
			}
		return companies;
	}
}
