package org.sobiech.inspigen.dao;

import java.util.Date;
import java.util.List;

import org.sobiech.inspigen.model.User;

public interface UserDAO {

	//U¯YTKOWNIK
	
    public void addUser(User user) throws DuplicateUserException;

    public User getUser(int userId) throws UserNotFoundException;
    
    public User getUser(String username) throws UserNotFoundException;

    public void updateUser(User user) throws UserNotFoundException;

    public void deleteUser(int userId) throws UserNotFoundException;

    public List<User> getUsers();
    
    // PASSWORD TOKEN
    
    public String getPasswordToken(String email);
       
    public void updatePasswordToken(String username, String token);
    
    // PASSWORD TOKEN - data wygaœniêcia
    
    public Date getPasswordTokenExpirationDate(String email);
        
    public void updatePasswordTokenExpirationDate(String email, Date expirationDate); 
}
