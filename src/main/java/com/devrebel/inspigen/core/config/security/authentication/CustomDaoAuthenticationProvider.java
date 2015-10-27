package com.devrebel.inspigen.core.config.security.authentication;

import com.devrebel.inspigen.app.domain.user.User;
import com.devrebel.inspigen.app.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {

	@Autowired
	UserRepository repository;

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

			User userByName = repository.findByUsername(username);
			
		      try {

		    	if (userByName != null) {

		    		if(userByName.getAccountNonLocked() == true) {
						userByName.toBuilder().failedLogins(0);
			    		repository.saveAndFlush(userByName);
		    		}

		    		Date lastAttempt  = userByName.getLastLoginAttempt();
			  		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			  		format.format(lastAttempt);

			  		Calendar unlockTime = Calendar.getInstance();
			  		unlockTime = format.getCalendar();
			  		unlockTime.add(Calendar.MINUTE, 15);

			  		if(Calendar.getInstance().getTime().after(unlockTime.getTime()) == true) {
						userByName.toBuilder()
								.failedLogins(0)
								.accountNonLocked(true);
			  			repository.saveAndFlush(userByName);
			  		}
		      }
		    	  	return super.authenticate(authentication);

		      } catch (BadCredentialsException e) {	
		    	  
		    	  setLoginFailureError("invalidCredentials");

		    	  if (userByName != null) {

		    		int attempts = userByName.getFailedLogins();
		    		attempts++;
		    		userByName.toBuilder()
							.failedLogins(attempts)
		    				.lastLoginAttempt(new Date());

		    		if (attempts > 3-1) {
		    			userByName.toBuilder().accountNonLocked(false);
		    			setLoginFailureError("accountLocked");
		    		}
					  repository.saveAndFlush(userByName);
		    	  }
			        throw e;
		      }
		}
}