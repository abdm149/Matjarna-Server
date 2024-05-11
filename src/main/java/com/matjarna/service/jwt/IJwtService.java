package com.matjarna.service.jwt;

import java.util.Date;

import io.jsonwebtoken.Claims;

public interface IJwtService {

	String generateToken(String email, long id);

	Date extractExpiration(String token);

	String extractEmail(String token);

	boolean isTokenValid(String token);

	long extractId(String token);

	Claims extractClaims(String token);
}