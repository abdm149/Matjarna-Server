package com.matjarna.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.matjarna.model.user.UserData;
import com.matjarna.service.jwt.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final String AUTH_HEADER = "Authorization";

	private static final String BEARER_HEADER = "Bearer ";

	@Autowired
	private JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader(AUTH_HEADER);

		if (authHeader != null && authHeader.startsWith(BEARER_HEADER)) {
			String token = authHeader.substring(BEARER_HEADER.length()).trim();
			if (jwtService.isTokenValid(token)) {
				String userEmail = jwtService.extractEmail(token);
				if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					UserData user = new UserData(jwtService.extractId(token), jwtService.extractEmail(token));
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null,
							user.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);

				}
			}
		}
		filterChain.doFilter(request, response);
	}
}