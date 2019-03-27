package com.excilys.service;

import java.util.ArrayList;

import com.excilys.dto.CompanyDTO;
import com.excilys.mapper.CompanyMapper;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.DAOFactory;

public class CompanyService {
	private static CompanyService instance;
	
	private CompanyService() {}
	
	public ArrayList<CompanyDTO> getAll() {
		return CompanyMapper.mapDTO(CompanyDAO.getInstance().getList());
	}
	
	public static CompanyService getInstance() {
        if(instance == null) {
            synchronized (DAOFactory.class) {
                if(instance == null) {
                	instance = new CompanyService();
                }
            }
        }
        return instance;
	}
}
