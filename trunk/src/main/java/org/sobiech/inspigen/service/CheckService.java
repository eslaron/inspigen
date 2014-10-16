package org.sobiech.inspigen.service;


public interface CheckService {
    
	// SPRAWDZANIE
    
    public Boolean checkIfUserIsLocked(String username);
    
    public Boolean checkIfUserExists(String username);
    
    public Boolean checkIfTokenExists(String tokenType, String token);
    
    public Boolean checkIfTokenExpired(String tokenType, String token);
    
    public Boolean checkIfEmailIsRegistered(String email);
   
}
