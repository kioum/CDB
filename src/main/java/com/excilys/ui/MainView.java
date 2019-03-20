package com.excilys.ui;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.model.Computer;

public class MainView {
	private Scanner in;
	private static final Logger LOG = LoggerFactory.getLogger(MainView.class);

	public MainView() {
		this.in = new Scanner(System.in);
	}

	/**
	 * Draw the menu for the user
	 */
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

	/**
	 * Draw a message and return the response
	 * @param message
	 * @return String
	 */
	public String getInputUser(String message) {
		System.out.println(message);
		return in.nextLine();
	}

	/**
	 * Draw a message
	 * @param message
	 */
	public void drawMessage(String message) {
		System.out.println(message);
	}

	/**
	 * draw all of elements of list
	 * @param list
	 * @param page
	 */
	public <T> void drawList(List<T> list, int page) {
		System.out.println("Page " + page + " of list computers  : ");
		for(T comp :list)
			System.out.println("\t" + comp);
	}

	/**
	 * draw the computer
	 * @param computer
	 */
	public void drawComputerDetails(Computer computer) {
		System.out.println("\n" + computer + "\n");
	}
}
