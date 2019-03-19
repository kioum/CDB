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

	public Timestamp getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
	}

	public Timestamp getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (obj == this) return true;
		if (obj == null) return false;
		if (this.getClass() != obj.getClass()) return false;
		
		Computer comp = (Computer)obj;
		
		if (this.id != comp.getId()) return false;
		
		if (this.name == null && comp.getName() != null) return false;
		if (this.name != null && comp.getName() == null) return false;
		if (!this.name.equals(comp.getName())) return false;
		
		if (this.introduced == null && comp.getIntroduced() != null) return false;
		if (this.introduced != null && comp.getIntroduced() == null) return false;
		if (!this.introduced.equals(comp.getIntroduced())) return false;
		
		if (this.discontinued == null && comp.getDiscontinued() != null) return false;
		if (this.discontinued != null && comp.getDiscontinued() == null) return false;
		if (!this.discontinued.equals(comp.getDiscontinued())) return false;
		
		if (this.manufacturer == null && comp.getManufacturer() != null) return false;
		if (this.manufacturer != null && comp.getManufacturer() == null) return false;
		if (!this.manufacturer.equals(comp.getManufacturer())) return false;
		
		return true;
	}
	
	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", manufacturer=" + manufacturer + "]";
	}
	
}
