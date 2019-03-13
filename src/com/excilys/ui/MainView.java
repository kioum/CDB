package com.excilys.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.excilys.model.Company;
import com.excilys.model.Computer;

public class MainView {
	
	private final int NBELEMENTAFF = 20;
	private Scanner in;
	
	public MainView() {
		this.in = new Scanner(System.in);
	}
	
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
		System.out.println("0. Exit");
	}

	public String getInputUser(String message) {
		System.out.println(message);
		return in.nextLine();
	}
	
	public void drawMessage(String message) {
		System.out.println(message);
	}
	
	public void drawListComputer(ArrayList<Computer> list) {
		System.out.println("List computers : ");
		for(int i = 0; i < Math.floor(list.size()/NBELEMENTAFF) + 1; i++) {
			int beg = i*NBELEMENTAFF;
			int end = (i+1)*NBELEMENTAFF;
			
			if(end > list.size())
				end = list.size();
			
			for(Computer comp :list.subList(beg, end))
					System.out.println("\t" + comp);
			System.out.println("To continue press any key");
			in.nextLine();
		}
	}

	public void drawListCompany(ArrayList<Company> list) {
		System.out.println("List company : ");
		for(int i = 0; i < Math.floor(list.size()/NBELEMENTAFF) +1; i++) {
			int beg = i*NBELEMENTAFF;
			int end = (i+1)*NBELEMENTAFF;
			
			if(end > list.size())
				end = list.size();
			
			for(Company comp :list.subList(beg, end))
					System.out.println("\t" + comp);
			System.out.println("To continue press any key");
			in.nextLine();
		}
	}

	public void drawComputerDetails(Computer computer) {
		System.out.println("\n" + computer + "\n");
	}
}
