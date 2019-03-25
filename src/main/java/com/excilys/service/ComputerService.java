package com.excilys.service;

import java.util.ArrayList;

import com.excilys.dto.ComputerDTO;
import com.excilys.mapper.ComputerMapper;
import com.excilys.persistence.ComputerDAO;

public class ComputerService {
	public static ArrayList<ComputerDTO> getAll() {
		return ComputerMapper.mapDTO(ComputerDAO.getList());
	}
	
}
