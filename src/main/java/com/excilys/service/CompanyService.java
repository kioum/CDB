package com.excilys.service;

import java.util.ArrayList;
import java.util.Optional;

import com.excilys.dto.CompanyDTO;
import com.excilys.exception.CompanyException;
import com.excilys.mapper.CompanyMapper;
import com.excilys.model.Company;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.DAOFactory;

public class CompanyService {
	private static CompanyService instance;
	
	private CompanyService() {}
	
	public ArrayList<CompanyDTO> getAll() {
		return CompanyMapper.mapDTO(CompanyDAO.getInstance().getList());
	}
	
	public Company findById(Long id) throws CompanyException {
		Optional<Company> optionCompany = CompanyDAO.getInstance().findById(id);
		if(optionCompany.isPresent())
			return CompanyDAO.getInstance().findById(id).get();
		else throw new CompanyException("Company id : " + id + " not found !");
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
