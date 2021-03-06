package org.sobiech.inspigen.core.config.xauth;

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
 */
public class XAuthTokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private UserDetailsService detailsService;

	public XAuthTokenConfigurer(UserDetailsService detailsService) {
		this.detailsService = detailsService;
	}
	
	//Nadpisana metoda w ktorej dodajemy nasz filtr
	@Override
	public void configure(HttpSecurity http) throws Exception {
		XAuthTokenFilter customFilter = new XAuthTokenFilter(detailsService);
		http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
