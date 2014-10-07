package org.sobiech.inspigen.service;

import java.util.Date;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.sobiech.inspigen.model.User;

public interface UserService extends UserDetailsService {
	
	// U¯YTKOWNIK
	
    public String addUser(User user);

    public User getUserById(int userId);

    public User getUserByName(String username);
    
    public User getUserByEmail(String email);

    public void updateUser(User user);

    public void deleteUser(int userId);

    public List<User> getUsers();
          
    //	PASSWORD TOKEN
    
    public String getPasswordToken(String email);
    
    public String setPasswordToken();
    
    public void updatePasswordToken(String username, String token);
    
	// PASSWORD TOKEN - data wygaœniêcia
    
    public Date getPasswordTokenExpirationDate(String email);
    
    public Date setPasswordTokenExpirationDate();
    
    public void updatePasswordTokenExpirationDate(String email, Date expirationDate);        
}
