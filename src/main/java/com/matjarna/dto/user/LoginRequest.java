package com.matjarna.dto.user;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

	@NotBlank(message = "Email shouldn't be blank")
	private String email;

	@NotBlank(message = "Password shouldn't be blank")
	private String password;

	public LoginRequest() {

	}

	public LoginRequest(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}