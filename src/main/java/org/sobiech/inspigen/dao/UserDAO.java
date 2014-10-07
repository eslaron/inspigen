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
    
    // PASSWORD TOKEN
    
    public String getPasswordToken(String email);
       
    public void updatePasswordToken(String username, String token);
    
    // PASSWORD TOKEN - data wygaœniêcia
    
    public Date getPasswordTokenExpirationDate(String email);
        
    public void updatePasswordTokenExpirationDate(String email, Date expirationDate); 
}
