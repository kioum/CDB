package com.excilys.dto;

public class ComputerDTO {
	private long id;
	private String name;
	private String introduced;
	private String discontinued;
	private long manufacturerId;
	private String manufacturerName;

	public ComputerDTO(long id, String name, String introduced, String discontinued, long manufacturerId, String manufacturerName) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.manufacturerId = manufacturerId;
		this.setManufacturerName(manufacturerName);
	}

	public Long getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(Long manufacturer) {
		this.manufacturerId = manufacturer;
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

	public String getIntroduced() {
		return introduced;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	@Override
	public String toString() {
		return "ComputerDTO [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
				+ discontinued + ", manufacturerId=" + manufacturerId + ", manufacturerName=" + manufacturerName + "]";
	}
}
