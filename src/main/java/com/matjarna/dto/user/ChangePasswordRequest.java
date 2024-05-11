package com.matjarna.dto.user;

import com.matjarna.constants.Constants;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ChangePasswordRequest {

	@NotBlank(message = "Current password is required")
	private String currentPassword;

	@NotBlank(message = "Password is required")
	@Pattern(regexp = Constants.REGEXP_PASSWORD, message = "Password must be at least 6 characters and contain at least one number and one character.")
	private String password;

	@NotBlank(message = "Confirm password is required")
	private String confirmPassword;

	public ChangePasswordRequest() {

	}

	public ChangePasswordRequest(String currentPassword, String password, String confirmPassword) {
		super();
		this.currentPassword = currentPassword;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
