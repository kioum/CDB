package com.excilys.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.dto.CompanyDTO;
import com.excilys.dto.ComputerDTO;
import com.excilys.exception.TimestampException;
import com.excilys.mapper.ComputerMapper;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.model.User;
import com.excilys.service.UserService;
import com.excilys.ui.MainView;
import com.excilys.util.Order;

@Component
public class MainController {
	private MainView mw;
	private UserService userService;
	Client client;

	private static final Logger LOG = LoggerFactory.getLogger(MainController.class);
	private final String URL_API_COMPUTERS = "http://localhost:8080/webapp/computers";
	private final String URL_API_COMPANY = "http://localhost:8080/webapp/company";
	private final String URL_API_USERS = "http://localhost:8080/webapp/users";

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
			invocationBuilder = client.target(URL_API_COMPUTERS).path("").request(MediaType.APPLICATION_JSON);
			drawList(invocationBuilder.get().readEntity(List.class));
			break;
		case "LISTCOMPANY":
			invocationBuilder = client.target(URL_API_COMPANY).path("").request(MediaType.APPLICATION_JSON);
			drawList(invocationBuilder.get().readEntity(List.class));
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

	public void drawComputer() {
		Long id = Long.valueOf(mw.getInputUser("Enter the id : "));

		Invocation.Builder invocationBuilder = client.target(URL_API_COMPUTERS).path("/"+id).request(MediaType.APPLICATION_JSON);
		try {
			mw.drawComputerDetails(ComputerMapper.dtoToComputer(invocationBuilder.get().readEntity(ComputerDTO.class)));
		} catch (TimestampException e) {
			LOG.error(e.getMessage());
		}
	}

	public void createUser() {
		String name = mw.getInputUser("Enter the username : ");
		String password = mw.getInputUser("Enter the password : ");

		client.target(URL_API_USERS).request(MediaType.APPLICATION_JSON)
		.post(Entity.json(new User(name, password, true)), User.class);
	}

	public void deleteComputer() {
		Long id = Long.valueOf(mw.getInputUser("Enter the id : "));
		client.target(URL_API_COMPUTERS).path("/"+id).request(MediaType.APPLICATION_JSON).delete(ComputerDTO.class);
	}

	private void deleteCompany() {
		Long id = Long.valueOf(mw.getInputUser("Enter the id : "));
		client.target(URL_API_COMPANY).path("/"+id).request(MediaType.APPLICATION_JSON).delete(CompanyDTO.class);
	}

	/**
	 * Ask to user a id. For each element ask if user want changes that.
	 * Update computer in the DB
	 */
	public void updateComputer() {
		Long id = Long.valueOf(mw.getInputUser("Enter the id : "));
		Computer computer = new Computer();

		Invocation.Builder invocationBuilder = client.target(URL_API_COMPUTERS).path("/"+id).request(MediaType.APPLICATION_JSON);
		try {
			computer = ComputerMapper.dtoToComputer(invocationBuilder.get().readEntity(ComputerDTO.class));
		}catch(NoSuchElementException e) {
			LOG.error("Computer id =" + id + " doesn't exist");
		} catch (TimestampException e) {
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

		String discontinued = null;
		if(computer.getIntroduced() == null) {
			discontinued = mw.getInputUser("Change the date of discontinued ? (y or any key) :");
			if (discontinued.toUpperCase().equals("Y")) {
				computer.setDiscontinued(getInputTimestamp());
			}
		}

		String idManu = mw.getInputUser("Enter the id of Manufacturer or just press enter :");
		if (!idManu.equals("")) {
			computer.getManufacturer().setId(Long.valueOf(idManu));
		}

		ComputerDTO compDTO = ComputerMapper.computerToDTO(computer);

		client.target(URL_API_COMPUTERS).path("/"+id).request(MediaType.APPLICATION_JSON)
		.put(Entity.json(compDTO), ComputerDTO.class);
	}

	/**
	 * Ask to user many information and create the computer in the DB
	 */
	public void createComputer() {
		String name = mw.getInputUser("Enter the name :");

		mw.drawMessage("Enter the date of introduced :");
		Timestamp introduced = getInputTimestamp();

		Timestamp discontinued;
		if(introduced != null) {
			mw.drawMessage("Enter the date of discontinued :");
			discontinued = getInputTimestamp();
		}else discontinued = null;

		Long idManu = Long.valueOf(mw.getInputUser("Enter the id of Manufacturer : "));

		Company company = new Company.CompanyBuilder().id(idManu).build();
		Computer computer = new Computer.ComputerBuilder().name(name).introduced(introduced)
				.discontinued(discontinued).manufacturer(company).build();

		ComputerDTO compDTO = ComputerMapper.computerToDTO(computer);

		client.target(URL_API_COMPUTERS).request(MediaType.APPLICATION_JSON)
		.post(Entity.json(compDTO), ComputerDTO.class);

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
