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
	public void createUser(User data){
		
     	String password = data.getPassword();
    	String encodedPassword = passwordEncoder.encodePassword(password, saltSource.getSalt(data));
    	String passwordToken = setToken();
    	String activationToken = setToken();
    	Date activationTokenExpiration = setTokenExpirationDate();
    	
    	data.setPassword(encodedPassword);
    	data.setPasswordToken(passwordToken);
    	
    	if(data.getRole() == null)
    		data.setRole("ROLE_USER");
    	
    	data.setEnabled(false);
    	data.setAccountNonExpired(true);
    	data.setAccountNonLocked(true);
    	data.setActivationToken(activationToken);
    	data.setCredentialsNonExpired(true);
    	data.setActivationTokenExpiration(activationTokenExpiration);

		dao.create(data);
		
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
			userDto.setRole(user.getRole());
			userDto.setEnabled(user.getEnabled());
			
			if(user.getAccountNonLocked() == true)
				userDto.setLocked(false);
			
			if(user.getAccountNonLocked() == false)
				userDto.setLocked(true);
			
			userDto.setFailedLogins(user.getFailedLogins());
			userDto.setLastLoginAttempt(user.getLastLoginAttempt());

			userDtoList.add(userDto);
		}
		
		return userDtoList;
	}
	
	@Override
	public List<User> findFirstPage(int size) {
		return dao.findFirstPage(size);
	}

	@Override
	public List<User> findPage(int page, int size) {
		return dao.findPage(page, size);
	}

	@Override
	public List<User> findLastPage(int size) {
		return dao.findLastPage(size);
	}

	@Override
	public Long entitySize() {
		return dao.entitySize();
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
		user.setRole(data.getRole());
		user.setEnabled(data.getEnabled());
		
		if(data.getLocked() == true) 
			user.setAccountNonLocked(false);
		
		if(data.getLocked() == false) {
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
	public ResponseEntity<String> addUser(User data) {
		
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
