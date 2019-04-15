package com.excilys.computerdatabase;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.excilys.controller.MainController;

public class App {
	public static final void main(String[] args) {
		GenericApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		MainController controler = context.getBean(MainController.class);
		controler.mainMenu();
		context.close();
	}
}