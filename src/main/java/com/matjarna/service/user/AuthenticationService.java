package com.matjarna.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.matjarna.exception.AuthenticationFailedException;
import com.matjarna.model.user.UserData;
import com.matjarna.service.jwt.IJwtService;

@Service
public class AuthenticationService implements IAuthenticationService {
	@Autowired
	private IJwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public String authenticate(String email, String password) {
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(email, password));
			UserData user = (UserData) authentication.getPrincipal();
			String token = jwtService.generateToken(user.getUsername(), user.getId());
			return token;
		} catch (AuthenticationException e) {
			throw new AuthenticationFailedException("Authentication failed", e);
		}
	}
}