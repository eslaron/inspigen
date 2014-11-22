package org.sobiech.inspigen.core.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.sobiech.inspigen.core.models.entities.Settings;
import org.sobiech.inspigen.core.models.entities.User;
import org.sobiech.inspigen.core.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {

	@Autowired
	UserService userService;
	
	@Autowired
	Settings settings;
	
	private String username;
	
	private String loginFailureError = "";

	public String getLoginFailureError() {
		return loginFailureError;
	}

	public void setLoginFailureError(String loginFailureError) {
		this.loginFailureError = loginFailureError;
	}

		@Override
		public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		    
			username = authentication.getName();
			
			User userByName = userService.findUserByName(username);
			
			int attempts = userByName.getFailedLogins(); 
			Date lastAttempt = userByName.getLastLoginAttempt();
			
		      try {
		    	  
		  		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  		format.format(lastAttempt);
		  		
		  		Calendar unlockTime = Calendar.getInstance();
		  		unlockTime = format.getCalendar();
		  		unlockTime.add(Calendar.MINUTE, settings.getLOCK_TIME());
		  			
		  		if(Calendar.getInstance().getTime().after(unlockTime.getTime()) == true) {
		  			userByName.setFailedLogins(0);
		  			userByName.setAccountNonLocked(true);
		  			userService.updateUser(userByName);
		  		}			
		    	  	return super.authenticate(authentication);
		        
		      } catch (BadCredentialsException e) {	
		    	  
		    	  setLoginFailureError("invalidCredentials");
		    	  
		    	  if (userByName != null) {
		    			
		    		attempts++;
		    		userByName.setFailedLogins(attempts);
		    		userByName.setLastLoginAttempt(new Date());
		    		
		    		if (attempts > settings.getMAX_ATTEMPTS()-1) {
		    			userByName.setAccountNonLocked(false);
		    			setLoginFailureError("accountLocked");
		    		}
		    		userService.updateUser(userByName);
		    	  }
			        throw e;
		      }
		}
}