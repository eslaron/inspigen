package org.sobiech.inspigen.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.sobiech.inspigen.dao.DuplicateUserException;
import org.sobiech.inspigen.dao.UserNotFoundException;
import org.sobiech.inspigen.model.LoginAttempts;
import org.sobiech.inspigen.model.User;

public interface UserService extends UserDetailsService {

    public void addUser(User user) throws DuplicateUserException;

    public User getUser(int userId) throws UserNotFoundException;

    public User getUser(String username) throws UserNotFoundException;

    public void updateUser(User user) throws UserNotFoundException;

    public void deleteUser(int userId) throws UserNotFoundException;

    public List<User> getUsers();
    
    public Boolean checkIfUserExists(String username);
    
    public void updateLoginFailAttempts(String username);
    
    public void resetLoginFailAttempts(String username);
	
    public LoginAttempts getLoginAttempts(String username);
    
    public void unlockAccount(String username);
}
