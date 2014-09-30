package org.sobiech.inspigen.dao;

import org.hibernate.Query;

public interface CheckDAO {
  
    // SPRAWDZANIE
        
    public Query checkIfUserExists(String username);
    
    public Query checkIfEmailIsRegistered(String email);
    
    public Query checkIfUserIsLocked(String username);
    
    public Query checkIfPasswordTokenExpired(String token);
}
