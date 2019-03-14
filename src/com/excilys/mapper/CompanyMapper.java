package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excilys.model.Company;

public class CompanyMapper implements IMapper<Company> {
	@Override
	public Company map(ResultSet res) {
		return null;
	}

	@Override
	public ArrayList<Company> mapList(ResultSet res) {
		ArrayList<Company> companies = new ArrayList<Company>();

		try {
			while(res.next()) {
				companies.add(new Company(res.getLong("id"), res.getString("name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return companies;
	}

}
