package org.sobiech.inspigen.core.services.impl;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sobiech.inspigen.core.models.dto.UserDto;
import org.sobiech.inspigen.core.models.entities.Settings;
import org.sobiech.inspigen.core.models.entities.User;
import org.sobiech.inspigen.core.repositories.common.IGenericDao;
import org.sobiech.inspigen.core.repositories.IUserDao;
import org.sobiech.inspigen.core.services.IEmailService;
import org.sobiech.inspigen.core.services.IUserService;

import com.google.gson.JsonObject;

//Klasa implementujaca interfejs IUserService
@Service
@Transactional
public class UserServiceImpl implements IUserService {
	
	IGenericDao<User> dao;
	
	//Ustawienie klasy, na ktorej ma operowac DAO
	   @Autowired
	   public void setDao(IGenericDao<User> daoToSet){
	      dao = daoToSet;
	      dao.setClazz(User.class);
	   }
	
	@Autowired
	IUserDao userDao;
	
    @Autowired
    Md5PasswordEncoder passwordEncoder;
    
    @Autowired 
    ReflectionSaltSource saltSource;
    
    @Autowired
    Settings settings;
    
    @Autowired
	IEmailService emailService;
    
    // UŻYTKOWNIK
    
    //Implementacja dodawania użytkownika do tabeli
	@Override
	public void createUser(UserDto data){
		
		User newUser = new User(); 
		
		newUser.setUsername(data.getUsername());
	
		//Pobieramy niezaszyfrowane hasło
     	String password = data.getPassword();
     	
     	//Szyfrujemy hasło
    	String encodedPassword = passwordEncoder.encodePassword(password, saltSource.getSalt(newUser));
    	
    	//Ustawiamy zaszyfrowane hasło
    	newUser.setPassword(encodedPassword);
    	
    	newUser.setEmail(data.getEmail());
    	
    	//Generujemy token dla hasła
    	String passwordToken = setToken();
      	newUser.setPasswordToken(passwordToken);
      	
      	//Generujemy datę wygaśnięcia tokenu dla hasła
      	Date passwordTokenExpiration = setTokenExpirationDate();
    	newUser.setPasswordTokenExpiration(passwordTokenExpiration);
    	
    	//Generujemy datę wygaśnięcia tokenu aktywacyjnego
      	Date activationTokenExpiration = setTokenExpirationDate();
    	newUser.setActivationTokenExpiration(activationTokenExpiration);
      	
    	//Generujemy token aktywacyjny
    	String activationToken = setToken();
    	newUser.setActivationToken(activationToken);
    	
    	//Jeśli rola jest pusta, to domyślnie nadajemy rolę użytkownika
    	if(data.getRole() == null)
    		newUser.setRole("ROLE_USER");
    	
    	//w przeciwnym przypadku nadajemy odpowiednie role
    	else {
    		
    	if(data.getRole().equals("Wolontariusz"))
    		newUser.setRole("ROLE_USER");
    	
    	if(data.getRole().equals("Koordynator"))
    		newUser.setRole("ROLE_MOD");
    	
    	if(data.getRole().equals("Administrator"))
    		newUser.setRole("ROLE_ADMIN");
    	}
    	
    	//Jeśli status aktywności jest pusty, to ustaw fałsz
    	if(data.getEnabled() == null)
    		newUser.setEnabled(false);
    	
    	//W przeciwnym przypadku ustaw odpowiednie statusy
    	else {  		
    		if(data.getEnabled().equals("Tak"))
        		newUser.setEnabled(true);
        	
        	if(data.getEnabled().equals("Nie"))
            	newUser.setEnabled(false);
    	}
    	
    	newUser.setAccountNonLocked(true);
    	newUser.setAccountNonExpired(true);
    	newUser.setCredentialsNonExpired(true);
    
    	//Dodaj nowego użytkownika
		dao.create(newUser);
		
		//Jeśli użytkownik jest nieaktywny, to wyślij email aktywacyjny
		if(newUser.getEnabled() == false)
			emailService.sendTokenMail(data.getEmail(), "activationToken", activationToken);
	}
	
	//Implementacja wyszukiwania użytkownika po id
	@Override
	public User findUserById(long id) {
		return dao.findOneById(id);
	}
	
	//Implementacja wyszukiwania użytkownika po nazwie
	@Override
	public User findUserByUsername(String username) {
		return userDao.findUserByUsername(username);
	}

	//Implementacja wyszukiwania użytkownika po emailu
	@Override
	public User findUserByEmail(String email) {
		return userDao.findUserByEmail(email);
	}

	//Implementacja wyszukiwania użytkownika po tokenie
	@Override
	public User findUserByToken(String tokenType, String token) {
		return userDao.findUserByToken(tokenType, token);
	}
	
	//Implementacja wyszukiwania wszystkich użytkownikow
	@Override
	public List<UserDto> findAllUsers() {
		
		//Tworzymy listę transportową
		List<UserDto> userDtoList = new ArrayList<UserDto>();
		
		//Wyszukujemy użytkownikow
		List<User> users = dao.findAll();
		
		//Przepisujemy wybrane informacje o użytkownikach do listy transportowej
		for(User user : users) {
			
			UserDto userDto = new UserDto();
			
			userDto.setId(user.getId());
			userDto.setUsername(user.getUsername());
			userDto.setEmail(user.getEmail());
			
			String role = user.getRole();

			if(role.contains("ROLE_USER"))
				userDto.setRole("Wolontariusz");
			
			if(role.contains("ROLE_MOD"))
				userDto.setRole("Koordynator");
			
			if(role.contains("ROLE_ADMIN"))
				userDto.setRole("Administrator");
			
			if(user.getEnabled() == true)
				userDto.setEnabled("Tak");
			
			if(user.getEnabled() == false)
				userDto.setEnabled("Nie");
			
			if(user.getAccountNonLocked() == true)
				userDto.setLocked("Nie");
			
			if(user.getAccountNonLocked() == false)
				userDto.setLocked("Tak");
			
			userDto.setFailedLogins(user.getFailedLogins());
			userDto.setLastLoginAttempt(user.getLastLoginAttempt());

			userDtoList.add(userDto);
		}
		
		return userDtoList;
	}
	
	//Implementacja aktualizacji użytkownika
	@Override
	public void updateUser(User data) {
		dao.update(data);
	}
	
	//Implementacja aktualizacji użytkownika na podstawie obiektu transportowego
	@Override
	public void updateUser(UserDto data) {
		
		//Wyszukaj użytkownika po id
		User user = findUserById(data.getId());
		
		//Jeśli nazwa użytkownika jest podana, to nadaj ją obiektowi do aktualizacji
		if(data.getUsername() != null)
		user.setUsername(data.getUsername());
		
		//Jeśli hasło zostało podane, to zaszyfruj i nadaj je obiektowi do aktualizacji
		if(data.getPassword() != null) {
			
			String password = data.getPassword();
	    	String encodedPassword = passwordEncoder.encodePassword(password, saltSource.getSalt(user));
	    	user.setPassword(encodedPassword);
		} 
		
		//Jeśli email jest podany, to nadaj go obiektowi do aktualizacji
		if(data.getEmail() != null)			
		user.setEmail(data.getEmail());
		
		if(data.getRole().equals("Wolontariusz"))
			user.setRole("ROLE_USER");
		
		if(data.getRole().equals("Koordynator"))
			user.setRole("ROLE_MOD");
		
		if(data.getRole().equals("Administrator"))
			user.setRole("ROLE_ADMIN");

		//Jeśli status aktywności jest pusty, to ustaw status na fałsz
		if(data.getEnabled() == null)
			user.setEnabled(false);
		
		//W przeciwnym przypadku nadaj odpowiednie statusy
		else {	
			if(data.getEnabled().equals("Tak"))
				user.setEnabled(true);
			
			if(data.getEnabled().equals("Nie"))
				user.setEnabled(false);
		}
		
		//Jeśli status niezablokowania jest pusty, to wyzeruj proby i ustaw na fałsz
		if(data.getLocked() == null) {
			user.setAccountNonLocked(true);
			user.setFailedLogins(0);
		}
		//W przeciwnym przypadku ustaw odpowiednie statusy
		else {
			if(data.getLocked().equals("Tak")) 
				user.setAccountNonLocked(false);
			
			if(data.getLocked().equals("Nie")) {
				user.setAccountNonLocked(true);
				user.setFailedLogins(0);
			}
		}
		
		//Zaktualizuj użytkownika
		dao.update(user);
	}

	//Implementacja usuwania użytkownika
	@Override
	public void deleteUser(User data) {
		dao.delete(data);
	}
	
	//Implementacja usuwania użytkownika po id
	@Override
	public void deleteUserById(long id) {
		dao.deleteById(id);
	}
	
	//Nadpisana metoda ładowania użytkownika pod nazwie
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return findUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
    
	// TOKEN
	
    //Implementacja generowania tokena
	@Override
	public String setToken() {
		
		//Zestaw znakow do losowania
		char[] VALID_CHARACTERS =
	    	    "abcdefghijklmnopqrstuvwxyz0123456879".toCharArray();
		
		//Instancja Secure Random
		SecureRandom srand = new SecureRandom();
		
		//Instancja pseudolosowania
	    Random random = new Random();
	    
	    //Bufor na 16 znakow
	    char[] buff = new char[16];

	    //Generowanie tokena i zapis do bufora
	    for (int i = 0; i < 16; ++i) {

	      if ((i % 10) == 0) {
	          random.setSeed(srand.nextLong());
	      }
	      buff[i] = VALID_CHARACTERS[random.nextInt(VALID_CHARACTERS.length)];
	    }
	    
	    //Zwrocenie wartości tekstowej bufora
	    return  String.valueOf(buff);
	}
	
	//Implementacja ustawiania daty wygaśnięcia
	@Override
	public Date setTokenExpirationDate() {
		
		//Pobieramy i formatujemy aktualną datę
		Date currentDate = new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.format(currentDate);

		//Pobieramy aktualną datę z kalendarza, formatujemy i dodajemy czas wygasania linkow
		Calendar cal=Calendar.getInstance();
		cal=format.getCalendar();	
		cal.add(Calendar.MINUTE, settings.getLinkExpirationTime());
	
		//Zwracamy nową datę
		return (Date)cal.getTime();
	}
	
	//Implementacja sprawdzania czy token wygasł
	@Override
	public Boolean checkIfTokenExpired(String tokenType, String token) {
		
		//Znajdź użytkownika po tokenie
		User userByToken = userDao.findUserByToken(tokenType, token);
		
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
		//Formatowanie odpowiednich dat wygaśnięcia według podanego wzorca
		if(tokenType == "activationToken")
		format.format(userByToken.getActivationTokenExpiration());
		
		if(tokenType == "passwordToken")
		format.format(userByToken.getPasswordTokenExpiration());
		
		//Pobranie aktualnej daty z kalendarza
		Calendar expire=Calendar.getInstance();
		expire = format.getCalendar();		
		
		//Sprawdzenie czy link z tokenem wygasł
		if(Calendar.getInstance().getTime().after(expire.getTime()) == true) {
			return true;
		} else return false;
	}

	//Implementacja szyfrowania hasła
	@Override
	public String encodePassword(User data) {
	
		String password = data.getPassword();
    	String encodedPassword = passwordEncoder.encodePassword(password, saltSource.getSalt(data));
		
    	return encodedPassword;
	}

	//Implementacja rejestracji użytkownika
	@Override
	public ResponseEntity<String> addUser(UserDto data) {
		
		//Staus(kod) odpowiedzi
		HttpStatus responseStatus = HttpStatus.CREATED;
		
		//Odpowiedź w notacji JSON
    	JsonObject jsonResponse = new JsonObject();
    	
    	//Wyszukaj użytkownika po nazwie
    	User userFoundByName = findUserByUsername(data.getUsername().toLowerCase());
    	
    	//Wyszukaj użytkownika po emailu
    	User userFoundByEmail = findUserByEmail(data.getEmail().toLowerCase());
    	
			boolean userNameFound = false;
			boolean emailFound =  false;
			
			//Wysyłanie adekwatnych odpowiedzi w zależności czy znaleziono duplikat nazwy użytkownika lub emailu
		    if (userFoundByName != null) {
		    	userNameFound = true;
		    	responseStatus = HttpStatus.CONFLICT;
		    	jsonResponse.addProperty("id", "Resource Conflict");
				jsonResponse.addProperty("description", "duplicateUser");
		    } 	
	        			    		    
		    if (userFoundByEmail != null) {
		    	emailFound = true;
		    	responseStatus = HttpStatus.CONFLICT;
		    	jsonResponse.addProperty("id", "Resource Conflict");
				jsonResponse.addProperty("description", "duplicateEmail");
		    }	
		    
		    if(userNameFound == true && emailFound == true) {
		    	responseStatus = HttpStatus.CONFLICT;
		    	jsonResponse.addProperty("id", "Resource Conflict");
				jsonResponse.addProperty("description", "duplicateUser&Email");
		    }
		    	
		    if(userNameFound == false && emailFound == false) {
		    	createUser(data);
		    	jsonResponse.addProperty("message", "Create Success");
		    }
		
		//Wyślij odpowiedź zawierającą status i komunikat JSON
    	return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
}