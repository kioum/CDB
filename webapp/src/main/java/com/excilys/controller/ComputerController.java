package com.excilys.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
import com.excilys.validator.CompanyDTOValidator;
import com.excilys.validator.ComputerDTOValidator;

@Controller
@RequestMapping(value = "/")
public class ComputerController {
	private ComputerService computerService;
	private CompanyService companyService;
	private MessageSource messageSource;

	private static final String VIEW_DASHBOARD = "dashboard";
	private static final String VIEW_ADDCOMPUTER = "addComputer";
	private static final String VIEW_EDITCOMPUTER = "editComputer";

	private static final String COMPUTER = "computer";
	private static final String EXCEPTION = "exception";

	public ComputerController(ComputerService computerService, CompanyService companyService, 
			MessageSource message) {
		this.computerService = computerService;
		this.companyService = companyService;
		this.messageSource = message;
	}

	@InitBinder("computer")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new ComputerDTOValidator(new CompanyDTOValidator(messageSource), messageSource));
	}

	@GetMapping({ "/", "/dashboard", "/dashBoard", "/Dashboard", "/DashBoard" })
	public String getDashBoard(@RequestParam(required = false) Map<String, String> paths, Model model) {
		Page<ComputerDTO> pageComputer = new Page<>(computerService.getAll(), 10);

		String search = paths.get("search");
		if(search != null && !search.equals("")) {
			pageComputer.setList(computerService.findByName(search));
		}

		String sortBy = paths.get("sortBy");
		if(sortBy != null && !sortBy.equals("")) {
			pageComputer.setList(ComputerMapper.sortBy(pageComputer.getList(), sortBy.toUpperCase()));
		}

		String asc = paths.get("asc");
		if(asc == null) 
			asc = "true";

		if("".equals(asc) && !Boolean.valueOf(asc))
			Collections.reverse(pageComputer.getList());

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
		model.addAttribute(COMPUTER, new ComputerDTO());

		return VIEW_DASHBOARD;
	}

	@PostMapping({"/deleteComputer", "/deletecomputer", "/Deletecomputer"})
	public String deleteComputer(@RequestParam(value = "cb", required = false) String listComputers, Model model) {
		if(listComputers != null)
			for(String id: listComputers.split(","))
				try {
					computerService.deleteById(Long.valueOf(id));
				} catch (ValidatorException e) {
					model.addAttribute(EXCEPTION, e.getMessage());
				}

		return getDashBoard(new HashMap<>(), model);
	}

	@GetMapping({ "/AddComputer", "/addComputer", "/addcomputer" })
	public String getAddServlet(Model model) {
		model.addAttribute("companies", companyService.getAll());
		model.addAttribute(COMPUTER, new ComputerDTO());

		return VIEW_ADDCOMPUTER;
	}

	@PostMapping({ "/AddComputer", "/addComputer", "/addcomputer" })
	public String postAddServlet(@Validated @ModelAttribute("computer")ComputerDTO computer, Model model) {
		try {
			computerService.create(ComputerMapper.dtoToComputer(computer));
		} catch (TimestampException e) {
			model.addAttribute(EXCEPTION, e.getMessage());
			return getAddServlet(model);
		}

		return "redirect:/"+getDashBoard(new HashMap<>(), model);
	}

	@GetMapping({ "/editcomputer", "/editComputer", "/EditComputer" })
	public String getEditServlet(@RequestParam(required = false) Map<String, String> paths, Model model) {
		model.addAttribute("companies", companyService.getAll());

		String id = paths.get("id");
		if(id != null) {
			Computer comp = new Computer();
			try {
				comp = computerService.findById(Long.valueOf(id));
				model.addAttribute(COMPUTER, ComputerMapper.computerToDTO(comp));
			}catch (ComputerException e) {
				model.addAttribute("id", id);
				return "404";
			}
		} 

		return VIEW_EDITCOMPUTER;
	}

	@PostMapping({ "/editcomputer", "/editComputer", "/EditComputer" })
	public String postEditServlet(@Validated @ModelAttribute("computer")ComputerDTO computer, Model model) {

		try {
			computerService.update(ComputerMapper.dtoToComputer(computer));
		} catch (ValidatorException | TimestampException e) {
			model.addAttribute(EXCEPTION, e.getMessage());
			Map<String, String> paths = new HashMap<>();
			paths.put("id", String.valueOf(computer.getId()));
			return getEditServlet(paths, model);
		}

		return "redirect:/"+getDashBoard(new HashMap<>(), model);
	}
}
