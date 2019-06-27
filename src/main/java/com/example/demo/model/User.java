package com.example.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "username",unique = true)
	private String userName;

	@Column(name = "password")
	private String password;

	@Column(name = "role")
	private String role;

	@Column(name = "hovaten")
	private String hovaten;

	public String getHovaten() {
		return hovaten;
	}

	public void setHovaten(String hovaten) {
		this.hovaten = hovaten;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public User(){
	};

	public User(User a){
		this.setId(a.getId());
		this.setPassword(a.getPassword());
		this.setUserName(a.getUserName());
		this.setRole(a.getRole());
		this.setHovaten(a.getHovaten());
	}
	public User(String userName, String password, String role, String hovaten){
		this.setPassword(password);
		this.setUserName(userName);
		this.setRole(role);
		this.setHovaten(hovaten);
	}

	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role));
		return authorities;
	}
}
