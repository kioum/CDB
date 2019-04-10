package com.excilys.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.AppConfig;
import com.excilys.exception.ComputerException;
import com.excilys.exception.ValidatorException;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.ui.MainView;
import com.excilys.util.Order;

public class MainController {
	private MainView mw;
	
	private ComputerService computerService;
	private CompanyService companyService;

	private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

	/** 
	 * Constructor
	 */
	public MainController() {
		this.mw = new MainView();
		this.computerService = AppConfig.context.getBean(ComputerService.class);
		this.companyService = AppConfig.context.getBean(CompanyService.class);
		mainMenu();
	}

	/**
	 * Controller of the main Menu
	 */
	public void mainMenu() {
		mw.drawMenu();

		int cmd = Integer.parseInt(mw.getInputUser("").toUpperCase());

		switch (Order.values()[cmd].name()) {
		case "EXIT" :
			LOG.info("Exit - Bye");
			return;
		case "LISTCOMPUTER":
			drawList(computerService.getAll());
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
		default:
			break;
		}
		mainMenu();
	}

	public void drawComputer() {
		Long id = Long.valueOf(mw.getInputUser("Enter the id : "));

		try {
			mw.drawComputerDetails(computerService.findById(id));
		}catch(NoSuchElementException | ComputerException e) {
			LOG.error("Computer id =" + id + " doesn't exist");
		}
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
	public <T> void drawList(ArrayList<T> list) {
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

		try {
			computerService.create(computer);
		} catch (ValidatorException e) {
			LOG.info(e.getMessage());
		}
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
