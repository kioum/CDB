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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		Company comp = (Company)obj;
		if(comp == null) return false;
		if(comp.id == id && comp.name == name) return true;
		else return false;
	}

	@Override
	public String toString() {
		return "Compagny [id=" + id + ", name=" + name + "]";
	}
}
