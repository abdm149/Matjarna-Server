package com.matjarna.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.matjarna.dto.user.ChangePasswordRequest;
import com.matjarna.dto.user.RegisterRequest;
import com.matjarna.dto.user.UserDto;

@Service
public class UserHelperMethods {

	public static RegisterRequest createValidRegisterRequest(String email, String password) {
		RegisterRequest registerRequest = createRegisterRequest(email, "Matjarna", "Application", "example-image-url",
				password, password);
		return registerRequest;
	}

	public static RegisterRequest createBlankRegisterRequest() {
		RegisterRequest registerRequest = createRegisterRequest("", "Matjarna", "Application", "example-image-url", "",
				"");
		return registerRequest;
	}

	public static RegisterRequest createInvalidEmailRegisterRequest() {
		RegisterRequest registerRequest = createRegisterRequest(null, "Matjarna", "Application", "example-image-url",
				"password123", "password123");
		return registerRequest;
	}

	public static RegisterRequest createDuplicateEmailRegisterRequest() {
		RegisterRequest registerRequest = createRegisterRequest("admin@matjarna.com", "Matjarna", "Application",
				"example-image-url", "password123", "password123");
		return registerRequest;
	}

	public static RegisterRequest createDuplicateEmailWithChangeInCaseRegisterRequest() {
		RegisterRequest registerRequest = createRegisterRequest("ADMIN@Matjarna.COM", "Matjarna", "Application",
				"example-image-url", "password123", "password123");
		return registerRequest;
	}

	public static RegisterRequest createInvalidPasswordRegisterRequest() {
		RegisterRequest registerRequest = createRegisterRequest("email@email.com", "Matjarna", "Application",
				"example-image-url", null, null);
		return registerRequest;
	}

	public static RegisterRequest createDifferentPasswordsRegisterRequest() {
		RegisterRequest registerRequest = createRegisterRequest("email12@gmail.com", "Matjarna", "Application",
				"example-image-url", "password1", "password2");
		return registerRequest;
	}

	public static RegisterRequest createBlankFirstNameRegisterRequest() {
		RegisterRequest registerRequest = createRegisterRequest("email123@email.com", "", "Application",
				"example-image-url", "1password12", "1password12");
		return registerRequest;
	}

	private static RegisterRequest createRegisterRequest(String email, String firstName, String lastName,
			String profilePicture, String password, String confirmPassword) {
		RegisterRequest registerRequest = new RegisterRequest();
		registerRequest.setEmail(email);
		registerRequest.setFirstName(firstName);
		registerRequest.setLastName(lastName);
		registerRequest.setProfilePicture(profilePicture);
		registerRequest.setPassword(password);
		registerRequest.setConfirmPassword(confirmPassword);
		return registerRequest;
	}

	public static ChangePasswordRequest createPasswordRequest(String currentPassword, String password,
			String confirmPassword) {
		ChangePasswordRequest passwordRequest = new ChangePasswordRequest();
		passwordRequest.setCurrentPassword(currentPassword);
		passwordRequest.setPassword(password);
		passwordRequest.setConfirmPassword(confirmPassword);
		return passwordRequest;
	}

	public static long totalNumberCalculator(List<UserDto> users, String searchTerm, Boolean active) {
		long counter = 0;
		for (UserDto user : users) {
			if ((active == null || user.isActive() == active) && // Check active status
					(searchTerm == null || searchTerm.isEmpty()
							|| (user.getEmail() != null
									&& user.getEmail().toLowerCase().contains(searchTerm.toLowerCase()))
							|| (user.getFirstName() != null
									&& user.getFirstName().toLowerCase().contains(searchTerm.toLowerCase()))
							|| (user.getLastName() != null
									&& user.getLastName().toLowerCase().contains(searchTerm.toLowerCase())))) {
				counter++;
			}
		}
		return counter;
	}
}