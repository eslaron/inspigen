package org.sobiech.inspigen.core.config;

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
	
	private String username;
	
		@Override
		public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		    
			username = authentication.getName();
			
			User user = userService.findUserByName(username);
			
		      try {
		    	  
		    	  	if(user.isAccountNonLocked() == true) {
		    	  		user.setAccountNonLocked(true);
		    	  		userService.updateUser(user);
		    	  		
		    	  	} else if (user.isAccountNonLocked() == false
		    	  				&& super.authenticate(authentication).isAuthenticated() == true) {
		    	  					user.setFailedLogins(0);
		    	  					userService.updateUser(user);
		    	  	} 
		    	  					
		    	  	return super.authenticate(authentication);
		        
		      } catch (BadCredentialsException e) {	
		    	  if (user != null) {
		    		int attempts = user.getFailedLogins();  
		    		user.setFailedLogins(attempts++);  
		    		userService.updateUser(user);
		    	  	//loginService.lockAccount(username);   	
		    	  }
			        throw e;
		      }
		}
}