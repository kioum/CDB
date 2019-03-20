package com.excilys.model;

public class Company{
	private Long id;
	private String name;
	
	public Company() {
		id = -1L;
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
		if (obj == this) return true;
		if (obj == null) return false;
		if (this.getClass() != obj.getClass()) return false;
		
  Company comp = (Company)obj;
  
		if (this.id != comp.getId()) return false;
		
		if (this.name == null && comp.getName() != null) return false;
		if (this.name != null && comp.getName() == null) return false;
		if (!this.name.equals(comp.getName())) return false;
		
		return true;
	}

	@Override
	public String toString() {
		return "Compagny [id=" + id + ", name=" + name + "]";
	}
}
