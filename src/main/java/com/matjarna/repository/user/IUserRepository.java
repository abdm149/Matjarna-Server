package com.matjarna.repository.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.matjarna.model.user.User;

public interface IUserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	User findByid(long id);

	@Query("select distinct u from User u where (:active is null or u.active = :active) and "
			+ "(:searchTerm is null or lower(u.email) like lower(concat('%', :searchTerm, '%')) or "
			+ "lower(u.lastName) like lower(concat('%', :searchTerm, '%')) or "
			+ "lower(u.firstName) like lower(concat('%', :searchTerm, '%')))")
	Page<User> getUsers(Pageable pageable, Boolean active, String searchTerm);
}