package org.sobiech.inspigen.service;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sobiech.inspigen.dao.CheckDAO;
import org.sobiech.inspigen.dao.LoginDAO;
import org.sobiech.inspigen.dao.UserDAO;
import org.sobiech.inspigen.model.Settings;

@Service
@Transactional
public class CheckServiceImpl implements CheckService {
	static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
    @Autowired
    private LoginDAO loginDAO;
    
    @Autowired
    private CheckDAO checkDAO;
    
    @Autowired 
    private UserDAO userDAO;
    
    @Autowired
    Md5PasswordEncoder passwordEncoder;
    
    @Autowired 
    ReflectionSaltSource saltSource;
    
    @Autowired 
    Settings settings;
    
	@Override
	public Boolean checkIfUserIsLocked(String username) {
		Query query = checkDAO.checkIfUserIsLocked(username);
    	
    	if (query.list().size() == 0 ) {
			return false;
		} else return true;
	}
	
	@Override
	public Boolean checkIfUserExists(String username) {
		Query query = checkDAO.checkIfUserExists(username);
		
		if (query.list().size() == 0 ) {
			return false;
		} else return true;
	}	
		
	@Override
	public Boolean checkIfPasswordTokenExpired(String email) {
				
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.format(userDAO.getPasswordTokenExpirationDate(email));
			
		Calendar expire=Calendar.getInstance();
		expire = format.getCalendar();		
			
		if(Calendar.getInstance().getTime().after(expire.getTime()) == true) {
			return true;
		} else return false;
	}
				
	@Override
	public Boolean checkIfEmailIsRegistered(String email) {
		Query query = checkDAO.checkIfEmailIsRegistered(email);
			
		if (query.list().size() == 0 ) {
			return false;
		} else return true;
	}
}
