package org.sobiech.inspigen.config;

import org.sobiech.inspigen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {

	int i;
	
	@Autowired
	UserService userService;
	
	private String username;
	
		@Override
		public Authentication authenticate(Authentication authentication)
		        throws AuthenticationException {
		      // Could assert pre-conditions here, e.g. rate-limiting
		      // and throw a custom AuthenticationException if necessary

		      try {
		        return super.authenticate(authentication);
		      } catch (BadCredentialsException e) {
		        username = authentication.getName();
		        userService.updateLoginFailAttempts(username);
		        throw e;
		      }
		}
}