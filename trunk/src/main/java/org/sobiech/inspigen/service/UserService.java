package org.sobiech.inspigen.service;

import java.util.Date;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.sobiech.inspigen.dao.DuplicateUserException;
import org.sobiech.inspigen.dao.UserNotFoundException;
import org.sobiech.inspigen.model.User;

public interface UserService extends UserDetailsService {
	
	// UŻYTKOWNIK
	
    public void addUser(User user) throws DuplicateUserException;

    public User getUser(int userId) throws UserNotFoundException;

    public User getUser(String username) throws UserNotFoundException;

    public void updateUser(User user) throws UserNotFoundException;

    public void deleteUser(int userId) throws UserNotFoundException;

    public List<User> getUsers();
    
    // SPRAWDZANIE
    
    public Boolean checkIfPasswordTokenExpired(String email);
    
    public Boolean checkIfUserExists(String username);
    
    public Boolean checkIfEmailIsRegistered(String email);
      
    //	PASSWORD TOKEN
    
    public String getPasswordToken(String email);
    
    public String setPasswordToken();
    
    public void updatePasswordToken(String username, String token);
    
	// PASSWORD TOKEN - data wygaśnięcia
    
    public Date getPasswordTokenExpirationDate(String email);
    
    public Date setPasswordTokenExpirationDate();
    
    public void updatePasswordTokenExpirationDate(String email, Date expirationDate);        
}
