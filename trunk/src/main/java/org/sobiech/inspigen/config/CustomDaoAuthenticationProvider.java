package org.sobiech.inspigen.config;

import org.sobiech.inspigen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {

	
	@Autowired
	UserService userService;
	
	private String username;
	
		@Override
		public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		    
			username = authentication.getName();
				
		      try {
		    	  	userService.unlockAccount(username);
		    	  	
		    	  	if (super.authenticate(authentication).isAuthenticated() == true)
		    	  	userService.resetLoginFailAttempts(username);
		    	  	
		    	  	return super.authenticate(authentication);
		        
		      } catch (BadCredentialsException e) {
		        
			        userService.updateLoginFailAttempts(username);
			        
			        throw e;
		      }
		}
}