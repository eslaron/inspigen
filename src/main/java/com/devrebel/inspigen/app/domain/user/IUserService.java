package com.devrebel.inspigen.app.domain.user;

import org.springframework.http.ResponseEntity;

import java.util.Date;


public interface IUserService {

	public void createUser(User data);

	public void updateUser(User data);

	public ResponseEntity<String> addUser(User data);

    public String setToken();
      
    public Date setTokenExpirationDate();
    
	public Boolean checkIfTokenExpired(String tokenType, String token);

	public String encodePassword(User data);
}