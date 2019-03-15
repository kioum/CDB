package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Company;

public class CompanyMapper implements IMapper<Company> {
	private static final Logger LOG = LoggerFactory.getLogger(CompanyMapper.class);
	
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
			LOG.error(e.getMessage());
		}
		
		return companies;
	}

}
