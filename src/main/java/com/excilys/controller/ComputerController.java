package com.excilys.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.dto.ComputerDTO;
import com.excilys.exception.ComputerException;
import com.excilys.exception.TimestampException;
import com.excilys.exception.ValidatorException;
import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;

@Controller
public class ComputerController {
	private ComputerService computerService;
	private CompanyService companyService;
	
	private final String VIEW_DASHBOARD = "WEB-INF/views/dashboard.jsp";
	private final String VIEW_ADDCOMPUTER = "WEB-INF/views/addComputer.jsp";
	private final String VIEW_EDITCOMPUTER = "WEB-INF/views/editComputer.jsp";
	
	public ComputerController(ComputerService computerService, CompanyService companyService) {
		this.computerService = computerService;
		this.companyService = companyService;
	}

	@GetMapping({ "/", "/dashboard", "/dashBoard", "/Dashboard", "/DashBoard" })
	public String getDashBoard(@RequestParam(required = false) Map<String, String> paths, Model model) {
		Page<ComputerDTO> pageComputer = new Page<ComputerDTO>(computerService.getAll(), 10);

		String search = paths.get("search");
		if(search != null && !search.equals("")) {
			pageComputer.setList(computerService.findByName(search));
		}

		String sortBy = paths.get("sortBy");
		if(sortBy != null && !sortBy.equals("")) {
			pageComputer.setList(ComputerMapper.sortBy(pageComputer.getList(), sortBy.toUpperCase()));
		}

		String asc = paths.get("asc");
		if(asc == null) asc = "true";
		else if(asc != "") {
			if(!Boolean.valueOf(asc))
				Collections.reverse(pageComputer.getList());
		}

		String maxElement = paths.get("maxElement");
		if(maxElement != null && !maxElement.equals("")) {
			pageComputer.setMaxElement(Integer.valueOf(maxElement));
		}

		String numPage = paths.get("numPage");
		if(numPage != null && !numPage.equals("")) {
			pageComputer.setNumPage(Integer.valueOf(numPage));
		}

		model.addAttribute("page", pageComputer);
		model.addAttribute("search", search);
		model.addAttribute("sortBy", sortBy);
		model.addAttribute("asc", asc);
		
		return VIEW_DASHBOARD;
	}

	@PostMapping({ "/", "/dashboard", "/dashBoard", "/Dashboard", "/DashBoard" })
	public String postDashBoard(@RequestParam(value = "cb", required = false) String listComputers, Model model) {
		
		if(listComputers != null)
			for(String id: listComputers.split(","))
				try {
					computerService.deleteById(Long.valueOf(id));
				} catch (ValidatorException e) {
					model.addAttribute("exception", e.getMessage());
				}
		
		
		return getDashBoard(Map.of(), model);
	}
	
	@GetMapping({ "/AddServlet", "/addServlet", "/addservlet" })
	public String getAddServlet(@RequestParam(required = false) Map<String, String> paths, Model model) {
		model.addAttribute("companies", companyService.getAll());
		
		return VIEW_ADDCOMPUTER;
	}

	@PostMapping({ "/AddServlet", "/addServlet", "/addservlet" })
	public String postAddServlet(@RequestParam(required = false) Map<String, String> paths, Model model) {
		String computerName = paths.get("computerName");

		String introduced = paths.get("introduced");	

		String discontinued = paths.get("discontinued");
		if (discontinued == null) discontinued = "";

		String companyId = paths.get("companyId");

		ComputerDTO compDTO = new ComputerDTO(0L, computerName, introduced, discontinued, Long.valueOf(companyId), "unknown");

		try {
			computerService.create(ComputerMapper.dtoToComputer(compDTO));
		} catch (ValidatorException | TimestampException e) {
			model.addAttribute("exception", e.getMessage());
			return getAddServlet(paths, model);
		}
		return VIEW_DASHBOARD;
	}
	
	@GetMapping({ "/editservlet", "/editServlet", "/EditServlet" })
	public String getEditServlet(@RequestParam(required = false) Map<String, String> paths, Model model) {
		model.addAttribute("companies", companyService.getAll());

		String id = paths.get("id");
		if(id != null) {
			try {
				Computer comp = computerService.findById(Long.valueOf(id));
				model.addAttribute("computer", ComputerMapper.computerToDTO(comp));
			}catch (ComputerException e) {
				return "404";
			}
		}
		return VIEW_EDITCOMPUTER;
	}

	@PostMapping({ "/editservlet", "/editServlet", "/EditServlet" })
	public String postEditServlet(@RequestParam(required = false) Map<String, String> paths, Model model) {
		String computerId = paths.get("id");
		String computerName = paths.get("computerName");
		String introduced = paths.get("introduced");
		String discontinued = paths.get("discontinued");

		if (discontinued == null) 
			discontinued = "";

		String companyId = paths.get("companyId");
		ComputerDTO compDTO = new ComputerDTO(Long.valueOf(computerId), computerName, introduced, discontinued, Long.valueOf(companyId), "unknown");

		try {
			computerService.update(ComputerMapper.dtoToComputer(compDTO));
		} catch (ValidatorException | TimestampException e) {
			model.addAttribute("exception", e.getMessage());
			return getEditServlet(paths, model);
		}

		return VIEW_DASHBOARD;
	}
}
