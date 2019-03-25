package com.excilys.service;

import java.util.ArrayList;

import com.excilys.dto.CompanyDTO;
import com.excilys.mapper.CompanyMapper;
import com.excilys.persistence.CompanyDAO;

public class CompanyService {
	public static ArrayList<CompanyDTO> getAll() {
		return CompanyMapper.mapDTO(CompanyDAO.getList());
	}
	
}
