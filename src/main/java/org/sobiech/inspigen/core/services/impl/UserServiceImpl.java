package org.sobiech.inspigen.core.services.impl;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sobiech.inspigen.core.models.entities.Settings;
import org.sobiech.inspigen.core.models.entities.User;
import org.sobiech.inspigen.core.repositories.common.IGenericDao;
import org.sobiech.inspigen.core.repositories.IUserDao;
import org.sobiech.inspigen.core.services.EmailService;
import org.sobiech.inspigen.core.services.UserService;
import org.sobiech.inspigen.core.services.util.UsersList;

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
    	data.setActivationToken(activationToken);
    	data.setActivationTokenExpiration(activationTokenExpiration);

		dao.create(data);
		
    	emailService.sendTokenMail(data.getEmail(), "activationToken", activationToken);
	}
	
	@Override
	public User findUserById(int id) {
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
	public UsersList findAllUsers() {
		return new UsersList(dao.findAll());
	}

	@Override
	public void updateUser(User data) {
		dao.update(data);
	}

	@Override
	public void deleteUser(User data) {
		dao.delete(data);
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
}
