package com.excilys.ui;

import java.util.ArrayList;
import java.util.List;

import com.excilys.model.Company;
import com.excilys.model.Computer;

public class MainView {

	public void drawMenu() {
		System.out.println("|---------------------|\t");
		System.out.println("|  Computer Database  |\t");
		System.out.println("|---------------------|\t\n");
		System.out.println("1. Show list computers");
		System.out.println("2. Show list companies");
		System.out.println("3. Show computer details");
		System.out.println("4. Create computer");
		System.out.println("5. Update computer");
		System.out.println("6. Delete computer");
	}
	
	public void drawListComputer(List<Computer> list) {
		System.out.println(" List computers : ");
		for(Computer computer:list) {
			System.out.println("\t" + computer);
		}
	}
	
	public void drawListCompany(List<Company> list) {
		System.out.println(" List company : ");
		for(Company company:list) {
			System.out.println("\t" + company);
		}
	}
}
