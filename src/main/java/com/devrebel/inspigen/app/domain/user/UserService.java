package com.devrebel.inspigen.app.domain.user;

import org.springframework.http.ResponseEntity;

public interface UserService {

	void createUser(User data);
	void updateUser(User data);
	ResponseEntity<String> addUser(User data);
}