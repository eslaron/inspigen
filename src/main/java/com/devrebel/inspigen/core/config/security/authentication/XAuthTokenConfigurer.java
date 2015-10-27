package com.devrebel.inspigen.core.config.security.authentication;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Klasa dodająca nowy filtr do zabezpieczeń przy autoryzacji tokenem
 * 
 * @author Philip W. Sorst (philip@sorst.net)
 * @author Josh Long (josh@joshlong.com)
 * @author Sebastian Sobiech (sebastian.sobiech@gmail.com)
 */
public class XAuthTokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private UserDetailsService userDetailsService;

	public XAuthTokenConfigurer(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		XAuthTokenFilter tokenFilter = new XAuthTokenFilter(userDetailsService);
		http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
	}
}