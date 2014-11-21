package org.sobiech.inspigen.core.repositories;

import org.sobiech.inspigen.core.models.entities.User;

public interface IUserDao {
	
	public User findUserByName(String username);
	
	public User findUserByEmail(String email);
	
	public User findUserByToken(String tokenType, String token);  	
}