package com.excilys.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.excilys.model.Computer;
import com.excilys.model.ConnectionDB;
import com.excilys.ui.MainView;

public class MainController {
	private MainView mw;
	private ConnectionDB conn;
	private Scanner in;

	public MainController() throws ClassNotFoundException, SQLException {
		this.mw = new MainView();
		this.conn = new ConnectionDB();
		this.in = new Scanner(System.in);
		mainMenu();
	}

	public void mainMenu() throws SQLException {
		mw.drawMenu();

		switch(in.nextLine().toUpperCase()) {
		case "1":
			mw.drawListComputer(conn.listComputer().subList(0, 10));
			break;
		case "2":
			mw.drawListCompany(conn.listCompany().subList(0, 10));	
			break;
		case "3":
			showDetailsMenu();
			break;
		case "4":
			break;
		case "5":
			break;
		case "6":
			break;
		default:
			mainMenu();
		}
	}

	private void showDetailsMenu() {
		
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
