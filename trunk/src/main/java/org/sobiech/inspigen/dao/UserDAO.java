package org.sobiech.inspigen.dao;

import java.util.List;

import org.sobiech.inspigen.model.LoginAttempts;
import org.sobiech.inspigen.model.User;

public interface UserDAO {

	//U¿ytkownik
	
    public void addUser(User user) throws DuplicateUserException;

    public User getUser(int userId) throws UserNotFoundException;
    
    public User getUser(String username) throws UserNotFoundException;

    public void updateUser(User user) throws UserNotFoundException;

    public void deleteUser(int userId) throws UserNotFoundException;

    public List<User> getUsers();
    
    //Sprawdzanie
    
    public Boolean checkIfUserExists(String username);
    
    public Boolean checkIfEmailIsRegistered(String email);
    
    public Boolean checkIfPasswordTokenExpired(String token);
    
    //Tymczasowe blokowanie kont
    
    public LoginAttempts getLoginAttempts(String username);
    
    public void updateLoginFailAttempts(String username);
    
    public void resetLoginFailAttempts(String username);
	
    public void unlockAccount(String username);
    
    //Resetowanie hase³
    
    public void setPasswordTokenExpirationDate(String email);
    
    public void setPasswordToken(int length, String username);
    
    public String getPasswordToken(String email);
}
