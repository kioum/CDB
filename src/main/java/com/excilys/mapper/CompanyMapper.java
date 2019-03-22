package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.dto.CompanyDTO;
import com.excilys.model.Company;

public class CompanyMapper {
	private static final Logger LOG = LoggerFactory.getLogger(CompanyMapper.class);

	public static ArrayList<Company> mapList(ResultSet res) {
		ArrayList<Company> companies = new ArrayList<Company>();

		try {
			while(res.next()) {
				companies.add(new Company(res.getLong("id"), res.getString("name")));
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}

		return companies;
	}
	
	public static Company map(ResultSet res) {
		Company company = new Company();

		try {
			if(res.next()) {
				company = new Company(res.getLong("id"), res.getString("name"));
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}

		return company;
	}

	public static ArrayList<CompanyDTO> mapDTO(ArrayList<Company> list) {
		ArrayList<CompanyDTO> companies = new ArrayList<CompanyDTO>();

		for(Company comp:list)
			companies.add(new CompanyDTO(comp.getId(), comp.getName()));

		return companies;
	}

}
