package com.excilys.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_roles")
public class Role {
	@Id
	@Column(name="user_role_id", unique=true)
	private int id;
	@Column(name="username")
	private String username;
	@Column(name="role")
	private String roleUser;
	
	public Role() {
		super();
		this.id = 0;
		this.username = "";
		this.roleUser = "";
	}
	
	public Role(String username, String role) {
		super();
		this.username = username;
		this.roleUser = role;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRole() {
		return roleUser;
	}
	public void setRole(String role) {
		this.roleUser = role;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", username=" + username + ", role=" + roleUser + "]";
	}
}
