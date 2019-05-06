package com.excilys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User{
	@Id
	@Column(name="username", unique=true)
	private String name;
	@Column(name="password")
	private String password;
	@Column(name="enabled")
	private Boolean enabled;
	
	public User(String name, String password, Boolean enabled) {
		super();
		this.name = name;
		this.password = password;
		this.enabled = enabled;
	}
	
	public User() {
		super();
		this.name = "";
		this.password = "";
		this.enabled = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", password=" + password + ", enabled=" + enabled + "]";
	}
}