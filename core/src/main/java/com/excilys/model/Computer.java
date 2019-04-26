package com.excilys.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "computer")
public class Computer {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="introduced")
	private Timestamp introduced;
	@Column(name="discontinued")
	private Timestamp discontinued;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="company_id")
	private Company manufacturer;

	public Company getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Company manufacturer) {
		this.manufacturer = manufacturer;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", manufacturer=" + manufacturer + "]";
	}

	public static class ComputerBuilder{
		private long id;
		private String name;
		private Timestamp introduced;
		private Timestamp discontinued;
		private Company manufacturer;

		public ComputerBuilder() {
			this.id = 0L;
			this.name = null;
			this.introduced = null;
			this.discontinued = null;
			this.manufacturer = null;
		}

		public ComputerBuilder id(long id) {
			this.id = id;
			return this;
		}

		public ComputerBuilder name(String name) {
			this.name = name;
			return this;
		}

		public ComputerBuilder introduced(Timestamp introduced) {
			this.introduced = introduced;
			return this;
		}

		public ComputerBuilder discontinued(Timestamp discontinued) {
			this.discontinued = discontinued;
			return this;
		}

		public ComputerBuilder manufacturer(Company company) {
			this.manufacturer = company;
			return this;
		}

		public Computer build() {
			Computer comp = new Computer();
			comp.id = id;
			comp.name = name;
			comp.introduced = introduced;
			comp.discontinued = discontinued;
			comp.manufacturer = manufacturer;

			return comp;
		}
	}
}
