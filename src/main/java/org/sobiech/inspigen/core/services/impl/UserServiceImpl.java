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
import org.sobiech.inspigen.core.models.DTO.UserDTO;
import org.sobiech.inspigen.core.models.entities.Settings;
import org.sobiech.inspigen.core.models.entities.User;
import org.sobiech.inspigen.core.repositories.common.IGenericDao;
import org.sobiech.inspigen.core.repositories.IUserDao;
import org.sobiech.inspigen.core.services.EmailService;
import org.sobiech.inspigen.core.services.UserService;

import com.google.gson.JsonObject;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	IGenericDao<User> dao;
	 
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
	EmailService emailService;
    
    // UŻYTKOWNIK
	@Override
	public void createUser(UserDTO data){
		
		User newUser = new User(); 
		
		newUser.setUsername(data.getUsername());
	
     	String password = data.getPassword();
    	String encodedPassword = passwordEncoder.encodePassword(password, saltSource.getSalt(newUser));
    	newUser.setPassword(encodedPassword);
    	
    	newUser.setEmail(data.getEmail());
    	
    	String passwordToken = setToken();
      	newUser.setPasswordToken(passwordToken);
      	
    	String activationToken = setToken();
    	newUser.setActivationToken(activationToken);
    	
    	Date activationTokenExpiration = setTokenExpirationDate();
    	newUser.setActivationTokenExpiration(activationTokenExpiration);
    	
    	if(data.getRole() == null)
    		newUser.setRole("ROLE_USER");
    	
    	if(data.getRole().equals("Wolontariusz"))
    		newUser.setRole("ROLE_USER");
    	
    	if(data.getRole().equals("Koordynator"))
    		newUser.setRole("ROLE_MOD");
    	
    	if(data.getRole().equals("Administrator"))
    		newUser.setRole("ROLE_ADMIN");
    	
    	if(data.getEnabled() == null)
    		newUser.setEnabled(false);
    	
    	if(data.getEnabled().equals("Tak"))
    		newUser.setEnabled(true);
    	
    	if(data.getEnabled().equals("Nie"))
        	newUser.setEnabled(false);
    	
    	newUser.setAccountNonLocked(true);
    	newUser.setAccountNonExpired(true);
    	newUser.setCredentialsNonExpired(true);
    
		dao.create(newUser);
		
		if(newUser.getEnabled() == false)
			emailService.sendTokenMail(data.getEmail(), "activationToken", activationToken);
	}
	
	@Override
	public User findUserById(long id) {
		return dao.findOneById(id);
	}
	
	@Override
	public User findUserByName(String username) {
		return userDao.findUserByName(username);
	}

	@Override
	public User findUserByEmail(String email) {
		return userDao.findUserByEmail(email);
	}

	@Override
	public User findUserByToken(String tokenType, String token) {
		return userDao.findUserByToken(tokenType, token);
	}
	
	@Override
	public List<UserDTO> findAllUsers() {
		
		List<UserDTO> userDtoList = new ArrayList<UserDTO>();
		
		List<User> users = dao.findAll();
		
		for(User user : users) {
			
			UserDTO userDto = new UserDTO();
			
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
	
	@Override
	public void updateUser(User data) {
		dao.update(data);
	}
	
	@Override
	public void updateUser(UserDTO data) {
		
		User user = findUserById(data.getId());
		
		user.setUsername(data.getUsername());
		
		if(data.getPassword() != null) 
			user.setPassword(data.getPassword());
		
		user.setEmail(data.getEmail());
		
		if(data.getRole().equals("Wolontariusz"))
			user.setRole("ROLE_USER");
		
		if(data.getRole().equals("Koordynator"))
			user.setRole("ROLE_MOD");
		
		if(data.getRole().equals("Administrator"))
			user.setRole("ROLE_ADMIN");
	
		if(data.getEnabled().equals("Tak"))
			user.setEnabled(true);
		
		if(data.getEnabled().equals("Nie"))
			user.setEnabled(false);
		
		if(data.getLocked().equals("Tak")) 
			user.setAccountNonLocked(false);
		
		if(data.getLocked().equals("Nie")) {
			user.setAccountNonLocked(true);
			user.setFailedLogins(0);
		}
		
		dao.update(user);
	}

	@Override
	public void deleteUser(User data) {
		dao.delete(data);
	}
	

	@Override
	public void deleteUserById(long id) {
		dao.deleteById(id);
	}
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return findUserByName(username);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
    
	// TOKEN
	
	@Override
	public String setToken() {
		
		char[] VALID_CHARACTERS =
	    	    "abcdefghijklmnopqrstuvwxyz0123456879".toCharArray();
		
		SecureRandom srand = new SecureRandom();
	    Random random = new Random();
	    char[] buff = new char[16];

	    for (int i = 0; i < 16; ++i) {
	      // reseed random once you've used up all available entropy bits
	      if ((i % 10) == 0) {
	          random.setSeed(srand.nextLong()); // 64 bits of random!
	      }
	      buff[i] = VALID_CHARACTERS[random.nextInt(VALID_CHARACTERS.length)];
	    }		    
	    return  String.valueOf(buff);
	}
	
	@Override
	public Date setTokenExpirationDate() {
		
		Date currentDate = new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.format(currentDate);
			
		Calendar cal=Calendar.getInstance();
		cal=format.getCalendar();		
		cal.add(Calendar.MINUTE, settings.getEXPIRATION_TIME());
	
		return (Date)cal.getTime();
	}
	
	@Override
	public Boolean checkIfTokenExpired(String tokenType, String token) {
		
		User userByToken = userDao.findUserByToken(tokenType, token);
		
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
		if(tokenType == "activationToken")
		format.format(userByToken.getActivationTokenExpiration());
		
		if(tokenType == "passwordToken")
		format.format(userByToken.getPasswordTokenExpiration());
		
		Calendar expire=Calendar.getInstance();
		expire = format.getCalendar();		
			
		if(Calendar.getInstance().getTime().after(expire.getTime()) == true) {
			return true;
		} else return false;
	}

	@Override
	public String encodePassword(User data) {
	
		String password = data.getPassword();
    	String encodedPassword = passwordEncoder.encodePassword(password, saltSource.getSalt(data));
		
    	return encodedPassword;
	}

	@Override
	public ResponseEntity<String> addUser(UserDTO data) {
		
		HttpStatus responseStatus = HttpStatus.CREATED;
    	JsonObject jsonResponse = new JsonObject();
    	User userFoundByName = findUserByName(data.getUsername().toLowerCase());
    	User userFoundByEmail = findUserByEmail(data.getEmail().toLowerCase());
    	
			boolean userNameFound = false;
			boolean emailFound =  false;
				
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
		
    	return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
}