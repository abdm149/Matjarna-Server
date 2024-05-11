package com.matjarna.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.matjarna.dto.user.RegisterRequest;
import com.matjarna.dto.user.UserDto;
import com.matjarna.model.user.User;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public abstract UserDto toUserDto(User user);

	public abstract User toUser(UserDto user);

	@Mapping(source = "password", target = "password", qualifiedByName = "passwordEncoder")
	public abstract User toUser(RegisterRequest registerRequest);

	@Named("passwordEncoder")
	public String passwordEncoder(String password) {
		return passwordEncoder.encode(password);
	}
}
