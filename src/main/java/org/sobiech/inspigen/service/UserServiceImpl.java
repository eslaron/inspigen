package org.sobiech.inspigen.service;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sobiech.inspigen.dao.DuplicateUserException;
import org.sobiech.inspigen.dao.UserDAO;
import org.sobiech.inspigen.dao.CheckDAO;
import org.sobiech.inspigen.dao.UserNotFoundException;
import org.sobiech.inspigen.model.Settings;
import org.sobiech.inspigen.model.User;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private CheckDAO checkDAO;
    
    @Autowired
    Md5PasswordEncoder passwordEncoder;
    
    @Autowired 
    ReflectionSaltSource saltSource;
    
    @Autowired
    Settings settings;
    
	@Override
	public void addUser(User user) throws DuplicateUserException {
		
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
	}

    @Override
    public User getUser(int userId) throws UserNotFoundException {
        return userDAO.getUser(userId);
    }

	@Override
	public User getUser(String username) throws UserNotFoundException {
		return userDAO.getUser(username);
	}

	@Override
	public void updateUser(User user) throws UserNotFoundException {
		userDAO.updateUser(user);
	}

	@Override
	public void deleteUser(int userId) throws UserNotFoundException {
		userDAO.deleteUser(userId);
	}

	@Override
	public List<User> getUsers() {
		return userDAO.getUsers();
	}
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return getUser(username);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
    
    // SPRAWDZANIE
    
	@Override
	public Boolean checkIfPasswordTokenExpired(String email) {
			
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.format(getPasswordTokenExpirationDate(email));
		
		Calendar expire=Calendar.getInstance();
		expire = format.getCalendar();		
		
		if(Calendar.getInstance().getTime().after(expire.getTime()) == true) {
			return true;
		} else return false;
	}
	
	@Override
	public Boolean checkIfUserExists(String username) {
		Query query = checkDAO.checkIfUserExists(username);
		
		if (query.list().size() == 0 ) {
			return false;
		} else return true;
	}
	
	@Override
	public Boolean checkIfEmailIsRegistered(String email) {
		Query query = checkDAO.checkIfEmailIsRegistered(email);
		
		if (query.list().size() == 0 ) {
			return false;
		} else return true;
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
