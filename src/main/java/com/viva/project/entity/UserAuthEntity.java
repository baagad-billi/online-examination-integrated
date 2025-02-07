package com.viva.project.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_auth")
public class UserAuthEntity {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "user_name")
	private String username;
	
	@Column(name = "password")
	private String password;

	@Column(name = "logged_in")
	private String isLoggedIn;

	@Column(name = "is_active")
	private String isActive;

	@Column(name = "retry_count")
	private long retryCount;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIsLoggedIn() {
		return isLoggedIn;
	}

	public void setIsLoggedIn(String isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public long getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(long retryCount) {
		this.retryCount = retryCount;
	}

}
