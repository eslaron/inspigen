package org.sobiech.inspigen.service;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sobiech.inspigen.dao.UserDAO;
import org.sobiech.inspigen.model.Settings;
import org.sobiech.inspigen.model.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    Md5PasswordEncoder passwordEncoder;
    
    @Autowired 
    ReflectionSaltSource saltSource;
    
    @Autowired
    Settings settings;
    
    // U¯YTKOWNIK
    
	@Override
	public String addUser(User user) {
		
		String response = "";
		boolean userNameFound = false;
		boolean emailFound =  false;
			
	    if (userDAO.getUserByName(user.getUsername()) != null) {	
        	userNameFound = true;
        	response = "duplicateUser";
	    }
	    
	    if (userDAO.getUserByEmail(user.getEmail()) != null) {	
	    	emailFound = true;
			response = "duplicateEmail";
	    }
	    
	    if(userDAO.getUserByName(user.getUsername()) != null
	    		&& userDAO.getUserByEmail(user.getEmail()) != null) {
	    	userNameFound = true;
	    	emailFound = true;
	    	response = "duplicateUser&duplicateEmail";
	    }

        if(userNameFound == false && emailFound == false) {
        	String password = user.getPassword();
        	String encodedPassword = passwordEncoder.encodePassword(password, saltSource.getSalt(user));
        	String passwordToken = setPasswordToken();
        	Date tokenExpiration = setPasswordTokenExpirationDate();
        	
        	user.setPassword(encodedPassword);
        	user.setPasswordToken(passwordToken);
        	user.setTokenExpiration(tokenExpiration);
        	user.setEnabled(true);
        	user.setAccountNonLocked(true);
        	user.setAccountNonExpired(true);
        	user.setCredentialsNonExpired(true);
        	
        	userDAO.addUser(user);
        	response = "userAdded";
        }
        
		return response;
	}

	@Override
	public User getUserById(int userId) {
		return userDAO.getUserById(userId);
	}
	
	@Override
	public User getUserByName(String username) {
		return userDAO.getUserByName(username);
	}

	@Override
	public User getUserByEmail(String email) {
		return userDAO.getUserByEmail(email);
	}

	@Override
	public void updateUser(User user) {
		userDAO.updateUser(user);
	}

	@Override
	public void deleteUser(int userId) {
		userDAO.deleteUser(userId);
	}

	@Override
	public List<User> getUsers() {
		return userDAO.getUsers();
	}
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return getUserByName(username);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
    
	// PASSWORD TOKEN
	
	@Override
	public String getPasswordToken(String email) {
		return userDAO.getPasswordToken(email);
	}
	
	@Override
	public String setPasswordToken() {
		
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
	public void updatePasswordToken(String username, String token) {
		userDAO.updatePasswordToken(username, token);	
	}
	
	// PASSWORD TOKEN - data wygaœniêcia
	
	@Override
	public Date getPasswordTokenExpirationDate(String email) {
		return userDAO.getPasswordTokenExpirationDate(email);
	}

	@Override
	public Date setPasswordTokenExpirationDate() {
		
		Date currentDate = new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.format(currentDate);
			
		Calendar cal=Calendar.getInstance();
		cal=format.getCalendar();		
		cal.add(Calendar.MINUTE, settings.getEXPIRATION_TIME());
	
		return cal.getTime();
	}

	@Override
	public void updatePasswordTokenExpirationDate(String email, Date expirationDate) {	
		userDAO.updatePasswordTokenExpirationDate(email, expirationDate);	
	}
}
