package com.matjarna.facade.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.matjarna.dto.EntityDtoList;
import com.matjarna.dto.SearchFilters;
import com.matjarna.dto.user.ChangePasswordRequest;
import com.matjarna.dto.user.RegisterRequest;
import com.matjarna.dto.user.UserDto;
import com.matjarna.exception.ValidationException;
import com.matjarna.mapper.user.UserMapper;
import com.matjarna.model.user.User;
import com.matjarna.model.user.UserData;
import com.matjarna.service.user.IAuthenticationService;
import com.matjarna.service.user.IUserService;

@Service
public class UserFacade implements IUserFacade {

	@Autowired
	private IAuthenticationService authenticationService;

	@Autowired
	private IUserService userService;

	@Autowired
	private UserMapper userMapper;

	@Override
	public String loginUser(String email, String password) {
		return authenticationService.authenticate(email.toLowerCase(), password);
	}

	@Override
	public UserDto findByID(long id) {
		User user = userService.findByID(id);
		if (user != null) {
			UserDto userDto = userMapper.toUserDto(user);
			return userDto;
		} else {
			throw new ValidationException("User not found where id = " + id);
		}
	}

	@Override
	public UserDto createUser(RegisterRequest registerRequest) {
		if (registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
			User user = userMapper.toUser(registerRequest);
			UserDto mappedUser = userMapper.toUserDto(userService.createUser(user));
			return mappedUser;
		} else {
			throw new ValidationException("Password do not match");
		}
	}

	@Override
	public EntityDtoList<UserDto, User> getUsers(Pageable pageable, SearchFilters searchTerm) {
		Page<User> page = userService.getUsers(pageable, searchTerm);
		EntityDtoList<UserDto, User> entityDtoList = new EntityDtoList<>(page, userMapper::toUserDto);
		return entityDtoList;
	}

	@Override
	public void changeUserState(long id, Boolean active) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserData user = (UserData) authentication.getPrincipal();
		long loggedId = user.getId();
		if (loggedId != id) {
			userService.changeUserState(id, active);
		} else {
			throw new ValidationException("You can't change your own state");
		}
	}

	@Override
	public void changeUserPassword(long userId, ChangePasswordRequest changePasswordRequest) {
		if (changePasswordRequest.getPassword().equals(changePasswordRequest.getConfirmPassword())) {
			userService.changeUserPassword(userId, changePasswordRequest.getCurrentPassword(),
					changePasswordRequest.getPassword(), changePasswordRequest.getConfirmPassword());
		} else {
			throw new ValidationException("Passwords do not match");
		}
	}

	@Override
	public UserDto changeUserImage(long id, String imageUrl) {
		User user = userService.changeUserImage(id, imageUrl);
		UserDto mappedUser = userMapper.toUserDto(user);
		return mappedUser;
	}

	@Override
	public UserDto changeUserNames(long id, String firstName, String lastName) {
		User user = userService.changeUserNames(id, firstName, lastName);
		UserDto mappedUser = userMapper.toUserDto(user);
		return mappedUser;
	}
}