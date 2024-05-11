package com.matjarna.service.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.matjarna.dto.SearchFilters;
import com.matjarna.model.user.User;

public interface IUserService {

	User createUser(User user);

	User findByEmail(String email);

	User findByID(long id);

	Page<User> getUsers(Pageable pageable, SearchFilters searchTerm);

	void changeUserState(long id, Boolean active);

	void changeUserPassword(long userId, String currentPassword, String password, String confirmPassword);

	User changeUserImage(long id, String imageUrl);

	User changeUserNames(long id, String firstName, String lastName);
}