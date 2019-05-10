package com.excilys.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "company")
public class Company{
	@Id
	@Column(name="id", unique=true)
	private long id;
	@Column(name="name")
	private String name;

	@Cascade(CascadeType.DELETE)
	@OneToMany(mappedBy = "manufacturer", fetch=FetchType.LAZY)
	private Set<Computer> computers;

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

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]";
	}

	public static class CompanyBuilder{
		private long id;
		private String name;

		public CompanyBuilder() {
			id = 0L;
			name = null;
		}

		public CompanyBuilder id(long id) {
			this.id = id;
			return this;
		}

		public CompanyBuilder name(String name) {
			if(name != null)
				this.name = name;
			else 
				this.name = "";
			return this;
		}

		public Company build() {
			Company comp = new Company();
			comp.id = id;
			comp.name = name;

			return comp;
		}
	}
}
