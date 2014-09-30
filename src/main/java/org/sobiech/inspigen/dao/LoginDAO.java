package org.sobiech.inspigen.dao;

import java.sql.Timestamp;

import org.sobiech.inspigen.model.LoginAttempts;

public interface LoginDAO {

    // PRÓBY LOGOWANIA
    
    public void addLoginAttemptEntry(String username);
    
    public Timestamp getLastAttemptDate(String username);
    
    public LoginAttempts getLoginAttempts(String username);
    
    public void updateLoginFailAttempts(String username);
    
    public void resetLoginFailAttempts(String username);
    
    // BLOKOKOWANIE I ODBLOKOWYWANIE KONTA 
    
    public void lockAccount(String username);
	
    public void unlockAccount(String username);
     
}
