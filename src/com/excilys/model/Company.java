package com.excilys.model;

public class Company{
	private Long id;
	private String name;
	
	public Company() {
		id = (long) -1.0;
		name = "Unknown";
	}
	
	public Company(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Compagny [id=" + id + ", name=" + name + "]";
	}
}
