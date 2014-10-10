package org.sobiech.inspigen.service;

import java.util.Date;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.sobiech.inspigen.model.User;

public interface UserService extends UserDetailsService {
	
	// U�YTKOWNIK
	
    public String addUser(User user);

    public User getUserById(int userId);

    public User getUserByName(String username);
    
    public User getUserByEmail(String email);

    public void updateUser(User user);

    public void deleteUser(int userId);

    public List<User> getUsers();
          
    // TOKEN
    
    public String getToken(String tokenType, String email);
    
    public String setToken();
    
    public void updateToken(String tokenType, String username, String token);
    
	// TOKEN - data wyga�ni�cia
    
    public Date getTokenExpirationDate(String tokenType, String email);
    
    public Date setTokenExpirationDate();
    
    public void updateTokenExpirationDate(String tokenType, String email, Date expirationDate);        
}