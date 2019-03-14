package com.excilys.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ComputerDAO;
import com.excilys.ui.MainView;

public class MainController {
	private MainView mw;
	private ComputerDAO dComputer;
	private CompanyDAO dCompany;
	
	private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

	public MainController(){
		this.mw = new MainView();
		this.dCompany = new CompanyDAO();
		this.dComputer = new ComputerDAO();
		
		mainMenu();
	}

	/**
	 * Controller of the main Menu
	 */
	public void mainMenu(){
		mw.drawMenu();

		switch(mw.getInputUser("").toUpperCase()) {
		case "1":
			drawList(dComputer.getList());
			break;
		case "2":
			drawList(dCompany.getList());
			break;
		case "3":
			Long id = Long.valueOf(mw.getInputUser("Enter the id : "));
			mw.drawComputerDetails(dComputer.findById(id));
			break;
		case "4":
			createComputer();
			break;
		case "5":
			updateComputer();
			break;
		case "6":
			id = Long.valueOf(mw.getInputUser("Enter the id : "));
			int success = dComputer.deleteById(id);
			if(success == 1)
				LOG.info("Your computer is deleted !\n");
			else LOG.info("Impossible to delete your computer\n");
			break;
		case "0" :
			LOG.info("Exit - Bye");
			return;
		}
		mainMenu();
	}

	/**
	 * Ask to user the number element by page and control the pagination
	 * with p (Prec), n (next) or q (quit)
	 * @param list
	 */
	public <T> void drawList(ArrayList<T> list) {
		int nbElement = Integer.parseInt(mw.getInputUser("How many by page ? "));
		
		String cmd = "";
		int cpt = 0;
		int maxPage = (int) (Math.floor(list.size()/nbElement));
		while(!cmd.equals("Q")) {
			if(cmd.equals("P") && cpt > 0)
				cpt--;
			if(cmd.equals("N") && cpt < maxPage) 
				cpt++;
			
			int beg = cpt * nbElement;
			int end = (cpt+1) * nbElement;
			
			if(end > list.size())
				end = list.size();
			
			mw.drawList(list.subList(beg, end), cpt);
			cmd = mw.getInputUser("Press key : p (Prec), n (next) or q (quit)").toUpperCase();
		}
	}
	
	/**
	 * Ask to user a id. For each element ask if user want changes that.
	 * Update computer in the DB
	 */
	public void updateComputer() {
		Long id = Long.valueOf(mw.getInputUser("Enter the id : "));
		Computer computer = dComputer.findById(id);

		String name = mw.getInputUser("Enter the name or just press enter :");
		if(!name.equals("")) computer.setName(name);

		String introduced = mw.getInputUser("Change the date of introduced ? (y or any key) :");
		if(introduced.toUpperCase().equals("Y")) 
			computer.setIntroduced(getATimestamp());

		String discontinued = mw.getInputUser("Change the date of discontinued ? (y or any key) :");
		if(discontinued.toUpperCase().equals("Y")) 
			computer.setDiscontinued(getATimestamp());

		String idManu = mw.getInputUser("Enter the id of Manufacturer or just press enter :");
		if(!idManu.equals("")) 
			computer.getManufacturer().setId(Long.valueOf(idManu));
		
		int success = dComputer.update(computer);
		if(success == 1)
			LOG.info("Your computer is updated !\n");
		else LOG.info("Impossible to update your computer\n");
	}

	/**
	 * Ask to user many information and create the computer in the DB
	 */
	public void createComputer() {
		String name = mw.getInputUser("Enter the name :");

		mw.drawMessage("Enter the date of introduced :");
		Timestamp introduced = getATimestamp();

		mw.drawMessage("Enter the date of discontinued :");
		Timestamp discontinued = getATimestamp();

		Long idManu = Long.valueOf(mw.getInputUser("Enter the id of Manufacturer : "));
		
		int success = dComputer.create(new Computer(Long.valueOf(0), name, introduced, discontinued, 
				new Company(idManu, "")));
		if(success == 1)
			LOG.info("Your computer is created !\n");
		else LOG.info("Impossible to create your computer\n");
	}

	/**
	 * Ask to user to enter a Date with format "YYYY-MM-DD" and return the date.
	 * @return Timestamp
	 */
	public Timestamp getATimestamp() {
		String date = mw.getInputUser("Enter a date like YYYY-MM-DD or null:");

		if(date.equals("null")) return null;

		Date newdate = null;
			try {
				newdate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				LOG.info("Parse of date failed");
				return getATimestamp();
			}

		return new Timestamp(newdate.getTime());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			new MainController();
	}
}
