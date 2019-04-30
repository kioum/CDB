package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.dto.CompanyDTO;
import com.excilys.model.Company;

public class CompanyMapper {
	public static Company map(ResultSet res) throws SQLException {
		Company company = new Company();

		company = new Company.CompanyBuilder()
				.id(res.getLong("id"))
				.name(res.getString("name")).build();
		
		return company;
	}

	public static ArrayList<CompanyDTO> mapDTO(List<Company> list) {
		ArrayList<CompanyDTO> companies = new ArrayList<CompanyDTO>();

		for(Company comp:list)
			companies.add(new CompanyDTO(comp.getId(), comp.getName()));

		return companies;
	}
}
