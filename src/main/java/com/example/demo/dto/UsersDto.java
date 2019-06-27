package com.example.demo.dto;

import com.example.demo.model.User;

public class UsersDto {

	private long id;

	private String userName;

	private String password;

	private String token;

	private String role;

	private String hovaten;
	public UsersDto(){

	}

	public UsersDto(User us){
		this.id = us.getId();
		this.userName = us.getUserName();
		this.role = us.getRole();
		this.hovaten = us.getHovaten();
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHovaten() {
		return hovaten;
	}

	public void setHovaten(String hovaten) {
		this.hovaten = hovaten;
	}
}
