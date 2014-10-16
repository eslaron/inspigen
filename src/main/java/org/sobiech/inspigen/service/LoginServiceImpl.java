package org.sobiech.inspigen.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sobiech.inspigen.dao.LoginDAO;
import org.sobiech.inspigen.model.LoginAttempts;
import org.sobiech.inspigen.model.Settings;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {
	static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
    @Autowired
    private LoginDAO loginDAO;
     
    @Autowired 
    Settings settings;
    
	// PRÓBY LOGOWANIA
    
	@Override
	public void addLoginAttemptEntry(String username) {
		loginDAO.addLoginAttemptEntry(username);
	}
	
	@Override
	public Timestamp getLastAttemptDate(String username) {
		return loginDAO.getLastAttemptDate(username);
	}
	
	@Override
	public LoginAttempts getLoginAttempts(String username) {
		return loginDAO.getLoginAttempts(username);
	}

	@Override
	public void updateLoginFailAttempts(String username) {

			if (getLoginAttempts(username) == null) addLoginAttemptEntry(username);		
		  		else if (getLoginAttempts(username).getAttempts() < settings.getMAX_ATTEMPTS())				
		  			loginDAO.updateLoginFailAttempts(username);						
	}
	
	@Override
	public void resetLoginFailAttempts(String username) {
		
		int attempts = getLoginAttempts(username).getAttempts();
		
		if(attempts < settings.getMAX_ATTEMPTS() && attempts > 0)
		loginDAO.resetLoginFailAttempts(username);	
	}

	// BLOKOKOWANIE I ODBLOKOWYWANIE KONTA 
		
	@Override
	public void lockAccount(String username) {
		if(getLoginAttempts(username).getAttempts() == settings.getMAX_ATTEMPTS())
		loginDAO.lockAccount(username);
	}
	
	@Override
	public void unlockAccount(String username) {
		
		Timestamp stamp = getLastAttemptDate(username);
		Date lastModifiedDate = new Date(stamp.getTime());
		DateFormat format=new SimpleDateFormat("HH:mm:ss");
		format.format(lastModifiedDate);
		
		Calendar unlock = Calendar.getInstance();
		unlock = format.getCalendar();		
		unlock.add(Calendar.MINUTE, settings.getLOCK_TIME());
		
		if(Calendar.getInstance().getTime().after(unlock.getTime()) == true)
			loginDAO.unlockAccount(username);
	}
}
