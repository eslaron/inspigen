package org.sobiech.inspigen.core.services;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.sobiech.inspigen.core.models.dto.UserDto;
import org.sobiech.inspigen.core.models.entities.User;

public interface IUserService extends UserDetailsService {
	
	// UŻYTKOWNIK
	
	public void createUser(UserDto data);
	
	public User findUserById(long id);
	
	public User findUserByUsername(String username);
	
	public User findUserByEmail(String email);
	
	public User findUserByToken(String tokenType, String token);
	
	public List<UserDto> findAllUsers();
	
	public void updateUser(User data);
	
	public void updateUser(UserDto data);
	
	public void deleteUser(User data);
	
	public void deleteUserById(long id);
	
	public ResponseEntity<String> addUser(UserDto data);
	  
    // TOKEN
       
    public String setToken();
      
    public Date setTokenExpirationDate();
    
	public Boolean checkIfTokenExpired(String tokenType, String token);
	
	// HASŁO
	
	public String encodePassword(User data);
}
