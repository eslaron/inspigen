package org.sobiech.inspigen.service;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sobiech.inspigen.dao.CheckDAO;
import org.sobiech.inspigen.dao.UserDAO;

@Service
@Transactional
public class CheckServiceImpl implements CheckService {

    @Autowired
    private CheckDAO checkDAO;
    
    @Autowired 
    private UserDAO userDAO;
    

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
	public Boolean checkIfTokenExpired(String tokenType, String token) {
				
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.format(userDAO.getTokenExpirationDate(tokenType, token));
			
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

	@Override
	public Boolean checkIfTokenExists(String tokenType, String token) {
		Query query = checkDAO.checkIfTokenExists(tokenType, token);
		if (query.list().size() == 0 ) {
			return false;
		} else return true;
	}
}
