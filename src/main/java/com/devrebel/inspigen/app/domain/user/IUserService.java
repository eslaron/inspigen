package com.devrebel.inspigen.app.domain.user;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;

//Interfejs zawierajacy prototypy metod operujących na klasie User
public interface IUserService {
	
	// UŻYTKOWNIK
	
	public void createUser(UserDto data);									//Dodawanie użytkownika do tabeli
	
	public User findUserById(long id);										//Wyszukiwanie użytkownika po id
	
	public User findUserByUsername(String username);						//Wyszukiwanie użytkownika po nazwie
	
	public User findUserByEmail(String email);								//Wyszukiwanie użytkownika po emailu
	
	public User findUserByToken(String tokenType, String token);			//Wyszukiwanie użytkownika po tokenie
	
	public List<UserDto> findAllUsers();									//Wyszukiwanie wszystkich użytkownikow
	
	public void updateUser(User data);										//Aktualizacja użytkownika
	
	public void updateUser(UserDto data);									//Aktualizacja użytkownika na podstawie obiektu transportowego
	
	public void deleteUser(User data);										//Usuwanie użytkownika
	
	public void deleteUserById(long id);									//Usuwanie użytkownika po id
	
	public ResponseEntity<String> addUser(UserDto data);					//Rejestracja użytkownika		
	  
    // TOKEN
       
    public String setToken();												//Generowanie tokena
      
    public Date setTokenExpirationDate();									//Ustawianie daty wygaśnięcia
    
	public Boolean checkIfTokenExpired(String tokenType, String token);		//Sprawdzanie czy token wygasł
	
	// HASŁO
	
	public String encodePassword(User data);								//Szyfrowanie hasła
}
