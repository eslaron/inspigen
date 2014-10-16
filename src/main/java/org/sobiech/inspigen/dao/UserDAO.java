package org.sobiech.inspigen.dao;

import java.util.Date;
import java.util.List;

import org.sobiech.inspigen.model.User;

public interface UserDAO {

	//U¯YTKOWNIK
	
    public void addUser(User user);

    public User getUserById(int userId);
    
    public User getUserByName(String username);
    
    public User getUserByEmail(String email);

    public void updateUser(User user);

    public void deleteUser(int userId);

    public List<User> getUsers();
    
    // TOKEN
    
    public String getToken(String tokenType, String email);
       
    public void updateToken(String tokenType ,String username, String token);
    
    
    // TOKEN - data wygaœniêcia
    
    public Date getTokenExpirationDate(String tokenType, String token);
        
    public void updateTokenExpirationDate(String tokenType, String email, Date expirationDate); 
    
}
