package org.sobiech.inspigen.config;


import org.sobiech.inspigen.service.CheckService;
import org.sobiech.inspigen.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {

	@Autowired
	LoginService loginService;
	
	@Autowired
	CheckService checkService;
	
	private String username;
	
		@Override
		public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		    
			username = authentication.getName();
			
		      try {
		    	  
		    	  	if(checkService.checkIfUserIsLocked(username) == true) {
		    	  				loginService.unlockAccount(username);
		    	  		
		    	  	} else if (checkService.checkIfUserIsLocked(username) == false
		    	  				&& super.authenticate(authentication).isAuthenticated() == true) {
		    	  					loginService.resetLoginFailAttempts(username);
		    	  	} 
		    	  					
		    	  	return super.authenticate(authentication);
		        
		      } catch (BadCredentialsException e) {	
		    	  if (checkService.checkIfUserExists(username) == true) {
		    	  	loginService.updateLoginFailAttempts(username);
		    	  	loginService.lockAccount(username);   	
		    	  }
			        throw e;
		      }
		}
}