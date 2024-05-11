package com.matjarna.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.matjarna.dto.EntityDtoList;
import com.matjarna.dto.SearchFilters;
import com.matjarna.dto.user.ChangePasswordRequest;
import com.matjarna.dto.user.RegisterRequest;
import com.matjarna.dto.user.UserDto;
import com.matjarna.facade.user.IUserFacade;
import com.matjarna.model.user.User;
import com.matjarna.model.user.UserData;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api")
public class UserApi {

	@Autowired
	private IUserFacade userFacade;

	@GetMapping("private/user")
	public ResponseEntity<UserDto> getUser(HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserData user = (UserData) authentication.getPrincipal();
		long id = user.getId();
		UserDto userDto = userFacade.findByID(id);
		return ResponseEntity.ok(userDto);
	}

	@PostMapping(value = "private/user/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDto createUser(@RequestBody @Valid RegisterRequest registerRequest) {
		return userFacade.createUser(registerRequest);
	}

	@GetMapping(value = "private/users/")
	public ResponseEntity<EntityDtoList<UserDto, User>> getUsers(Pageable pageable, SearchFilters searchTerm) {
		EntityDtoList<UserDto, User> users = userFacade.getUsers(pageable, searchTerm);
		return ResponseEntity.status(200).body(users);
	}

	@PatchMapping(value = "private/user/{userId}/activate")
	public ResponseEntity<Void> activateUser(@PathVariable("userId") Long userId) {
		userFacade.changeUserState(userId, true);
		return ResponseEntity.status(200).build();
	}

	@PatchMapping(value = "private/user/{userId}/deactivate")
	public ResponseEntity<Void> deactivateUser(@PathVariable("userId") Long userId) {
		userFacade.changeUserState(userId, false);
		return ResponseEntity.status(200).build();
	}

	@PatchMapping(value = "private/user/password")
	public ResponseEntity<String> changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserData userData = (UserData) authentication.getPrincipal();
		long userId = userData.getId();
		userFacade.changeUserPassword(userId, changePasswordRequest);
		return ResponseEntity.status(200).body("Password changed successfully");
	}

	@PatchMapping(value = "private/user/image")
	public ResponseEntity<UserDto> changeUserImage(
			@RequestParam(value = "imageUrl", required = false) String imageUrl) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserData userData = (UserData) authentication.getPrincipal();
		long userId = userData.getId();
		UserDto user = userFacade.changeUserImage(userId, imageUrl);
		return ResponseEntity.status(200).body(user);
	}

	@PatchMapping(value = "private/user/names")
	public ResponseEntity<UserDto> changeUserNames(@RequestParam(value = "firstName", required = true) String firstName,
			@RequestParam(value = "lastName", required = false) String lastName) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserData userData = (UserData) authentication.getPrincipal();
		long userId = userData.getId();
		UserDto user = userFacade.changeUserNames(userId, firstName, lastName);
		return ResponseEntity.status(200).body(user);
	}
}