package com.excilys.controller;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.Page;
import com.excilys.persistence.CompanyDAO;
import com.excilys.persistence.ComputerDAO;
import com.excilys.ui.MainView;
import com.excilys.util.Order;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainController {
  private MainView mw;
  private ComputerDAO daoComputer;
  private CompanyDAO daoCompany;

  private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

  /** 
   * Constructor
   */
  public MainController() {
    this.mw = new MainView();
    this.daoCompany = new CompanyDAO();
    this.daoComputer = new ComputerDAO();

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
        drawList(daoComputer.getList());
        break;
      case "LISTCOMPANY":
        drawList(daoCompany.getList());
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
      default:
        break;
    }
    mainMenu();
  }

  public void drawComputer() {
    Long id = Long.valueOf(mw.getInputUser("Enter the id : "));
    mw.drawComputerDetails(daoComputer.findById(id));
  }

  public void deleteComputer() {
    Long id = Long.valueOf(mw.getInputUser("Enter the id : "));
    successLog(daoComputer.deleteById(id), "delete");
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
    Computer computer = daoComputer.findById(id);

    String name = mw.getInputUser("Enter the name or just press enter :");
    if (!name.equals("")) { 
      computer.setName(name); 
    }

    String introduced = mw.getInputUser("Change the date of introduced ? (y or any key) :");
    if (introduced.toUpperCase().equals("Y")) {
      computer.setIntroduced(getATimestamp());
    }

    String discontinued = mw.getInputUser("Change the date of discontinued ? (y or any key) :");
    if (discontinued.toUpperCase().equals("Y")) {
      computer.setDiscontinued(getATimestamp());
    }

    String idManu = mw.getInputUser("Enter the id of Manufacturer or just press enter :");
    if (!idManu.equals("")) {
      computer.getManufacturer().setId(Long.valueOf(idManu));

    }
    successLog(daoComputer.update(computer), "update");
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

    successLog(daoComputer.create(new Computer(Long.valueOf(0), name, introduced, discontinued, 
        new Company(idManu, ""))), "create");
  }

  /**
   * Ask to user to enter a Date with format "YYYY-MM-DD" and return the date.
   * @return Timestamp
   */
  public Timestamp getATimestamp() {
    String date = mw.getInputUser("Enter a date like YYYY-MM-DD or null:");

    if (date.equals("null")) {
      return null;
    }

    Date newdate = null;
    try {
      newdate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
    } catch (ParseException e) {
      LOG.info("Parse of date failed");
      return getATimestamp();
    }

    return new Timestamp(newdate.getTime());
  }

  public void successLog(int success, String cmd) {
    if (success == 1) {
      LOG.info("Your computer is " + cmd + "d !\n");
    } else {
      LOG.info("Impossible to " + cmd + " your computer\n");
    }
  }
}
