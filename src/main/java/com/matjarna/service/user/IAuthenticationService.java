package com.matjarna.service.user;

public interface IAuthenticationService {

	String authenticate(String email, String password);

}