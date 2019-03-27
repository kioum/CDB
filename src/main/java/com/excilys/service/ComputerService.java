package com.excilys.service;

import java.util.ArrayList;

import com.excilys.dto.ComputerDTO;
import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Computer;
import com.excilys.persistence.ComputerDAO;
import com.excilys.persistence.DAOFactory;
import com.excilys.validator.ComputerValidator;

public class ComputerService {
	private static ComputerService instance;
	
	private ComputerService() {}
	
	public ArrayList<ComputerDTO> getAll() {
		return ComputerMapper.mapDTO(ComputerDAO.getInstance().getList());
	}
	
	public boolean create(Computer comp) {
		if(ComputerValidator.isCreatable(comp)) {
			ComputerDAO.getInstance().create(comp);
			return true;
		}
		
		return false;
	}
	
	public static ComputerService getInstance() {
        if(instance == null) {
            synchronized (DAOFactory.class) {
                if(instance == null) {
                	instance = new ComputerService();
                }
            }
        }
        return instance;
	}
}
