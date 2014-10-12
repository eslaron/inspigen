package org.sobiech.inspigen.service;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
	
	
    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    Md5PasswordEncoder passwordEncoder;
    
    @Autowired 
    ReflectionSaltSource saltSource;
    
    @Autowired
    Settings settings;
    
    @Autowired
	EmailService mailService;
    
    
    // UŻYTKOWNIK
    
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
        	String passwordToken = setToken();
        	Date passwordTokenExpiration = setTokenExpirationDate();
        	String activationToken = setToken();
        	Date activationTokenExpiration = setTokenExpirationDate();
        	
        	user.setPassword(encodedPassword);
        	user.setPasswordToken(passwordToken);
        	user.setPasswordTokenExpiration(passwordTokenExpiration);
        	user.setActivationToken(activationToken);
        	user.setActivationTokenExpiration(activationTokenExpiration);
        	user.setEnabled(false);
        	user.setAccountNonLocked(true);
        	user.setAccountNonExpired(true);
        	user.setCredentialsNonExpired(true);
        	
        	userDAO.addUser(user);
			mailService.sendTokenMail(user.getEmail(),"activationToken", activationToken);
		
        	response = "activationLinkSent";
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
	public String getToken(String tokenType, String email) {
		return userDAO.getToken(tokenType, email);
	}
	
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
	public void updateToken(String tokenType, String username, String token) {
		userDAO.updateToken(tokenType, username, token);	
	}
	
	// PASSWORD TOKEN - data wyga�ni�cia
	
	@Override
	public Date getTokenExpirationDate(String tokenType, String email) {
		return userDAO.getTokenExpirationDate(tokenType, email);
	}

	@Override
	public Date setTokenExpirationDate() {
		
		Date currentDate = new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.format(currentDate);
			
		Calendar cal=Calendar.getInstance();
		cal=format.getCalendar();		
		cal.add(Calendar.MINUTE, settings.getEXPIRATION_TIME());
	
		return cal.getTime();
	}

	@Override
	public void updateTokenExpirationDate(String tokenType, String email, Date expirationDate) {	
		userDAO.updateTokenExpirationDate(tokenType, email, expirationDate);	
	}
}
