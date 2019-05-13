package com.excilys.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import com.excilys.controller.MainController;

public class App {
	public static final void main(String[] args) {
		GenericApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		MainController controller = context.getBean(MainController.class);
		controller.mainMenu();
		context.close();
	}
}