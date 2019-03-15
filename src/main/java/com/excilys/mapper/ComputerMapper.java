package com.excilys.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Company;
import com.excilys.model.Computer;

public class ComputerMapper implements IMapper<Computer>{
	private static final Logger LOG = LoggerFactory.getLogger(ComputerMapper.class);
	
	@Override
	public Computer map(ResultSet res) {
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

	@Override
	public ArrayList<Computer> mapList(ResultSet res) {
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

}
