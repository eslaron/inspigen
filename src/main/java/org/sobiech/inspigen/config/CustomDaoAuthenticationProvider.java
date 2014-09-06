package org.sobiech.inspigen.config;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;


public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {

	int i;
	
		@Override
		public Authentication authenticate(Authentication authentication)
		        throws AuthenticationException {
		      // Could assert pre-conditions here, e.g. rate-limiting
		      // and throw a custom AuthenticationException if necessary

		      try {
		        return super.authenticate(authentication);
		      } catch (BadCredentialsException e) {
		        System.out.println("Fail "+i++);

		        throw e;
		      }
		}
}