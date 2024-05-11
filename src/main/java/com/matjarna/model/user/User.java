package com.matjarna.model.user;

import com.matjarna.constants.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "User")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "email", unique = true, nullable = false)
	@NotBlank(message = "Email is required")
	@Email(message = "Email should be valid")
	private String email;

	@Column(name = "password", nullable = false)
	@NotBlank(message = "Password is required")
	@Pattern(regexp = Constants.REGEXP_PASSWORD, message = "Password must be at least 6 characters and contain at least one number and one character.")
	private String password;

	@Column(name = "first_name", nullable = false)
	@NotBlank(message = "First name is required")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "active")
	private Boolean active = true;

	@Column(name = "profile_picture")
	private String profilePicture;

	public User() {

	}

	public Long getId() {
		return id;
	}

	public User(String email, String password, String firstName, String lastName, boolean active,
			String profilePicture) {
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.profilePicture = profilePicture;
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
}