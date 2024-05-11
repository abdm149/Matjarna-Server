package com.matjarna.model.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserData implements UserDetails {

	private static final long serialVersionUID = 1L;

	private long id;

	private String email;

	private String password;

	private boolean active;

	public UserData(long id, String email) {
		this.id = id;
		this.email = email;
	}

	public UserData(User user) {
		id = user.getId();
		email = user.getEmail();
		password = user.getPassword();
		active = user.isActive();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	public long getId() {
		return id;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
}