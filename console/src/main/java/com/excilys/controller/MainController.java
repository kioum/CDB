package com.excilys.controller;
 
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.dto.ComputerDTO;
import com.excilys.exception.ComputerException;
import com.excilys.exception.TimestampException;
import com.excilys.exception.ValidatorException;
import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.model.User;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.service.UserService;
import com.excilys.ui.MainView;
import com.excilys.util.Order;

@Component
public class MainController {
	private MainView mw;
	private ComputerService computerService;
	private CompanyService companyService;
	private UserService userService;
	Client client;

	private static final Logger LOG = LoggerFactory.getLogger(MainController.class);
	private final String URL_API = "http://localhost:8080/webapp/computers";

	/** 
	 * Constructor
	 */
	public MainController() {
		client = ClientBuilder.newClient();
		this.mw = new MainView();
	}

	/**
	 * Controller of the main Menu
	 */
	public void mainMenu() {
		mw.drawMenu();
		
		int cmd = Integer.parseInt(mw.getInputUser("").toUpperCase());
		Invocation.Builder invocationBuilder;

		switch (Order.values()[cmd].name()) {
		case "EXIT" :
			LOG.info("Exit - Bye");
			return;
		case "LISTCOMPUTER":
			invocationBuilder = client.target(URL_API).path("").request(MediaType.APPLICATION_JSON);
			drawList(invocationBuilder.get().readEntity(List.class));
			break;
		case "LISTCOMPANY":
			drawList(companyService.getAll());
			break;
		case "SHOWCOMPUTER":
			drawComputer();
			break;
		case "CREATECOMPUTER":
			createComputer();
			break;
		case "UPDATECOMPUTER":
			updateComputer();
			break;
		case "DELETECOMPUTER":
			deleteComputer();
			break;
		case "DELETECOMPANY":
			deleteCompany();
			break;
		case "CREATEUSER":
			createUser();
			break;
		default:
			break;
		}
		mainMenu();
	}

	public void drawComputer() {
		Long id = Long.valueOf(mw.getInputUser("Enter the id : "));

		Invocation.Builder invocationBuilder = client.target(URL_API).path("/"+id).request(MediaType.APPLICATION_JSON);
		try {
			mw.drawComputerDetails(ComputerMapper.dtoToComputer(invocationBuilder.get().readEntity(ComputerDTO.class)));
		} catch (TimestampException e) {
			LOG.error(e.getMessage());
		}
	}

	public void createUser() {
		String name = mw.getInputUser("Enter the username : ");
		String password = mw.getInputUser("Enter the password : ");

		userService.create(new User(name, password, true));
	}
	
	public void deleteComputer() {
		Long id = Long.valueOf(mw.getInputUser("Enter the id : "));
		try {
			computerService.deleteById(id);
		} catch (ValidatorException e) {
			LOG.error(e.getMessage());
		}
	}

	private void deleteCompany() {
		Long id = Long.valueOf(mw.getInputUser("Enter the id : "));
		try {
			companyService.deleteById(id);
		} catch (ValidatorException e) {
			LOG.error(e.getMessage());
		}
	}

	/**
	 * Ask to user the number element by page and control the pagination
	 * with p (Prec), n (next) or q (quit)
	 * @param list
	 */
	public <T> void drawList(List<T> list) {
		int nbElement = Integer.parseInt(mw.getInputUser("How many by page ? "));

		Page<T> page = new Page<T>(list, nbElement);
		mw.drawList(page.currentPage(), page.getNumPage());

		String cmd = "";

		while (!cmd.equals("Q")) {
			if (cmd.equals("P")) {
				mw.drawList(page.precPage(), page.getNumPage());
			} else if (cmd.equals("N")) {
				mw.drawList(page.nextPage(), page.getNumPage());
			}
			cmd = mw.getInputUser("Press key : p (Prec), n (next) or q (quit)").toUpperCase();
		}
	}

	/**
	 * Ask to user a id. For each element ask if user want changes that.
	 * Update computer in the DB
	 */
	public void updateComputer() {
		Long id = Long.valueOf(mw.getInputUser("Enter the id : "));
		Computer computer = null;

		try {
			computer = computerService.findById(id);
		}catch(NoSuchElementException e) {
			LOG.error("Computer id =" + id + " doesn't exist");
		} catch (ComputerException e) {
			LOG.error(e.getMessage());
		}

		String name = mw.getInputUser("Enter the name or just press enter :");
		if (!name.equals("")) { 
			computer.setName(name); 
		}

		String introduced = mw.getInputUser("Change the date of introduced ? (y or any key) :");
		if (introduced.toUpperCase().equals("Y")) {
			computer.setIntroduced(getInputTimestamp());
		}

		String discontinued = mw.getInputUser("Change the date of discontinued ? (y or any key) :");
		if (discontinued.toUpperCase().equals("Y")) {
			computer.setDiscontinued(getInputTimestamp());
		}

		String idManu = mw.getInputUser("Enter the id of Manufacturer or just press enter :");
		if (!idManu.equals("")) {
			computer.getManufacturer().setId(Long.valueOf(idManu));
		}

		try {
			computerService.update(computer);
		} catch (ValidatorException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage());
		}
	}

	/**
	 * Ask to user many information and create the computer in the DB
	 */
	public void createComputer() {
		String name = mw.getInputUser("Enter the name :");

		mw.drawMessage("Enter the date of introduced :");
		Timestamp introduced = getInputTimestamp();

		mw.drawMessage("Enter the date of discontinued :");
		Timestamp discontinued = getInputTimestamp();

		Long idManu = Long.valueOf(mw.getInputUser("Enter the id of Manufacturer : "));

		Company company = new Company.CompanyBuilder().id(idManu).build();
		Computer computer = new Computer.ComputerBuilder().name(name).introduced(introduced)
				.discontinued(discontinued).manufacturer(company).build();

		computerService.create(computer);
	}

	/**
	 * Ask to user to enter a Date with format "YYYY-MM-DD" and return the date.
	 * @return Timestamp
	 */
	public Timestamp getInputTimestamp() {
		String date = mw.getInputUser("Enter a date like YYYY-MM-DD or null:");

		if (date.equals("null")) {
			return null;
		}

		Date newdate = null;
		try {
			newdate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			LOG.info("Parse of date failed");
			return getInputTimestamp();
		}

		return new Timestamp(newdate.getTime());
	}
}
