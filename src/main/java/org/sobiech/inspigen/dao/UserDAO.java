package org.sobiech.inspigen.dao;

import java.util.Date;
import java.util.List;

import org.sobiech.inspigen.model.User;

public interface UserDAO {

	// UŻYTKOWNIK
	
    public void addUser(User user);

    public User getUserById(int userId);
    
    public User getUserByName(String username);
    
    public User getUserByEmail(String email);

    public void updateUser(User user);

    public void deleteUser(int userId);

    public List<User> getUsers();
    
    public void deleteNotActivatedUsers();
    
    // TOKEN
    
    public String getToken(String tokenType, String email);
       
    public void updateToken(String tokenType ,String username, String token);
    
    
    // TOKEN - data wygaśnięcia
    
    public Date getTokenExpirationDate(String tokenType, String token);
        
    public void updateTokenExpirationDate(String tokenType, String email, Date expirationDate); 
    
    // KONTO - aktywacja/deaktywacja
    
    public void activateAccount(String token);
	
    public void deactivateAccount(String username);
}
