package com.matjarna.service.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService implements IJwtService {

	private static final String ID_CLAIMS = "ID";

	@Value("${jwt.secretKey}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private long expiration;

	@Override
	public String generateToken(String email, long id) {

		Map<String, Object> claims = new HashMap<>();
		claims.put(ID_CLAIMS, id);

		return Jwts.builder().setClaims(claims).setSubject(email).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS512).compact();
	}

	@Override
	public Date extractExpiration(String token) {
		return extractClaims(token).getExpiration();
	}

	@Override
	public String extractEmail(String token) {
		return extractClaims(token).getSubject();
	}

	@Override
	public long extractId(String token) {
		return extractClaims(token).get(ID_CLAIMS, Long.class);
	}

	@Override
	public boolean isTokenValid(String token) {
		return extractExpiration(token).after(new Date());
	}

	@Override
	public Claims extractClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().parseClaimsJws(token).getBody();
	}
}