package com.excilys.model;

import java.sql.Timestamp;

public class Computer {
	private Long id;
	private String name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private Company manufacturer;
	
	public Computer(Long id, String name, Timestamp introduced, Timestamp discontinued, Company manufacturer) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.manufacturer = manufacturer;
	}

	public Company getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Company manufacturer) {
		this.manufacturer = manufacturer;
	}

	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", manufacturer=" + manufacturer + "]";
	}
	
}
