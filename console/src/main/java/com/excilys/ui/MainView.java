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
		LOG.info("|---------------------|\t");
		LOG.info("|  Computer Database  |\t");
		LOG.info("|---------------------|\t\n");
		LOG.info("1. Show list computers");
		LOG.info("2. Show list companies");
		LOG.info("3. Show computer details");
		LOG.info("4. Create computer");
		LOG.info("5. Update computer");
		LOG.info("6. Delete computer");
		LOG.info("7. Delete company");
		LOG.info("8. Create User");
		LOG.info("0. Exit");
	}

	/**
	 * Draw a message and return the response
	 * @param message
	 * @return String
	 */
	public String getInputUser(String message) {
		LOG.info(message);
		return in.nextLine();
	}

	/**
	 * Draw a message
	 * @param message
	 */
	public void drawMessage(String message) {
		LOG.info(message);
	}

	/**
	 * draw all of elements of list
	 * @param list
	 * @param page
	 */
	public <T> void drawList(List<T> list, int page) {
		LOG.info("Page {} of list computers  : ", page);
		for(T comp :list) {
			LOG.info("\t {}", comp);
		}
	}

	/**
	 * draw the computer
	 * @param computer
	 */
	public void drawComputerDetails(Computer computer) {
		LOG.info("\n {} \n", computer);
	}
}
