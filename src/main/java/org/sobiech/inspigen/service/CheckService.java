package org.sobiech.inspigen.service;


public interface CheckService {
    
	// SPRAWDZANIE
    
    public Boolean checkIfUserIsLocked(String username);
    
    public Boolean checkIfUserExists(String username);
    
    public Boolean checkIfTokenExpired(String tokenType, String email);
    
    public Boolean checkIfEmailIsRegistered(String email);
   
}
