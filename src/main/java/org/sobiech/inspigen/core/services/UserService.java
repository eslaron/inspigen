package org.sobiech.inspigen.core.services;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.sobiech.inspigen.core.models.DTO.UserDTO;
import org.sobiech.inspigen.core.models.entities.User;

public interface UserService extends UserDetailsService {
	
	// UŻYTKOWNIK
	
	public void createUser(User data);
	
	public User findUserById(long id);
	
	public User findUserByName(String username);
	
	public User findUserByEmail(String email);
	
	public User findUserByToken(String tokenType, String token);
	
	public List<UserDTO> findAllUsers();
	
	public List<User> findFirstPage(int size);
	   
	public List<User> findPage(int page, int size);
	   
	public List<User> findLastPage(int size);
	
	Long entitySize();
	
	public void updateUser(User data);
	
	public void updateUser(UserDTO data);
	
	public void deleteUser(User data);
	
	public void deleteUserById(long id);
	
	public ResponseEntity<String> addUser(User data);
	  
    // TOKEN
       
    public String setToken();
      
    public Date setTokenExpirationDate();
    
	public Boolean checkIfTokenExpired(String tokenType, String token);
	
	// PASSWORD
	
	public String encodePassword(User data);
}
