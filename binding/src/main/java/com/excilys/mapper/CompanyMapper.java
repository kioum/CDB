package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.dto.CompanyDTO;
import com.excilys.model.Company;

public class CompanyMapper {
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
}
