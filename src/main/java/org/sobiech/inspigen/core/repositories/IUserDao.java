package org.sobiech.inspigen.core.repositories;

import org.sobiech.inspigen.core.models.entities.User;

//Interfejs zawierajacy prototypy metod operujących na klasie User
public interface IUserDao {
	
	public User findUserByUsername(String username);				//Wyszukiwanie użytkownika po jego nazwie
	
	public User findUserByEmail(String email);						//Wyszukiwanie użytkownika po emailu
	
	public User findUserByToken(String tokenType, String token);  	//Wyszukiwanie użytkownika po tokenie
}