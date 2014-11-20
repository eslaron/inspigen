package org.sobiech.inspigen.core.services;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.sobiech.inspigen.core.models.entities.User;
import org.sobiech.inspigen.core.services.util.UsersList;

public interface UserService extends UserDetailsService {
	
	// UŻYTKOWNIK
	
	public void createUser(User data);
	
	public User findUserById(int id);
	
	public User findUserByName(String username);
	
	public User findUserByEmail(String email);
	
	public User findUserByToken(String tokenType, String token);
	
	public UsersList findAllUsers();
	
	public void updateUser(User data);
	
	public void deleteUser(User data);
	  
    // TOKEN
       
    public String setToken();
      
    public Date setTokenExpirationDate();
    
	public Boolean checkIfTokenExpired(String tokenType, String token);
	
	// PASSWORD
	
	public String encodePassword(User data);
}
