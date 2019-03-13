package com.excilys.controller;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ComputerDAO;
import com.excilys.ui.MainView;

public class MainController {
	private MainView mw;
	private ComputerDAO dComputer;
	private CompanyDAO dCompany;

	public MainController() throws ClassNotFoundException, SQLException {
		this.mw = new MainView();
		this.dCompany = new CompanyDAO();
		this.dComputer = new ComputerDAO();
		
		mainMenu();
	}

	public void mainMenu() throws SQLException {
		mw.drawMenu();

		switch(mw.getInputUser("").toUpperCase()) {
		case "1":
			mw.drawListComputer(dComputer.getList());
			break;
		case "2":
			mw.drawListCompany(dCompany.getList());	
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
				mw.drawMessage("Your computer is deleted !\n");
			else mw.drawMessage("Impossible to delete your computer\n");
			break;
		case "0" :
			return;
		}
		mainMenu();
	}

	public void updateComputer() throws SQLException {
		Long id = Long.valueOf(mw.getInputUser("Enter the id : "));
		Computer computer = dComputer.findById(id);

		String name = mw.getInputUser("Enter the name or just press enter :");
		if(!name.equals("")) computer.setName(name);

		String introduced = mw.getInputUser("Change the date of introduced (y or any key) :");
		if(introduced.toUpperCase().equals("Y")) 
			computer.setIntroduced(getATimestamp());

		String discontinued = mw.getInputUser("Change the date of discontinued (y or any key) :");
		if(discontinued.toUpperCase().equals("Y")) 
			computer.setDiscontinued(getATimestamp());

		String idManu = mw.getInputUser("Enter the id of Manufacturer or just press enter :");
		if(!idManu.equals("")) 
			computer.getManufacturer().setId(Long.valueOf(idManu));
		
		int success = dComputer.update(computer);
		if(success == 1)
			mw.drawMessage("Your computer is updated !\n");
		else mw.drawMessage("Impossible to update your computer\n");
	}

	public void createComputer() throws SQLException {
		String name = mw.getInputUser("Enter the name :");

		mw.drawMessage("Enter the date of introduced :");
		Timestamp introduced = getATimestamp();

		mw.drawMessage("Enter the date of discontinued :");
		Timestamp discontinued = getATimestamp();

		Long idManu = Long.valueOf(mw.getInputUser("Enter the id of Manufacturer : "));
		
		int success = dComputer.create(new Computer(Long.valueOf(0), name, introduced, discontinued, 
				new Company(idManu, "")));
		if(success == 1)
			mw.drawMessage("Your computer is created !\n");
		else mw.drawMessage("Impossible to create your computer\n");
	}

	public Timestamp getATimestamp() {
		String date = mw.getInputUser("Enter a date like YYYY-MM-DD or null:");

		if(date.equals("null")) return null;

		Date newdate = null;
		try {
			newdate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			mw.drawMessage("Error : parse of date doens't work");
			return getATimestamp();
		}

		return new Timestamp(newdate.getTime());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			new MainController();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
