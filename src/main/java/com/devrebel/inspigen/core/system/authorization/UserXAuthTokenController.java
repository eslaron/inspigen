package com.devrebel.inspigen.core.system.authorization;

import com.devrebel.inspigen.core.system.CustomDaoAuthenticationProvider;
import com.devrebel.inspigen.core.system.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

/**
 * Kontroler obsługujący proces autoryzacji użytkownika (logowania się do systemu)
 * 
 *
 * @author Philip W. Sorst (philip@sorst.net)
 * @author Josh Long (josh@joshlong.com)
 * @author Sebastian Sobiech (sebastian.sobiech@gmail.com)
 */

@RestController
public class UserXAuthTokenController {

	private final TokenUtils tokenUtils = new TokenUtils();
	private final AuthenticationManager authenticationManager;
	private final CustomUserDetailsService userDetailsService;
	private final CustomDaoAuthenticationProvider provider;

	
	//Konstruktor z atrybutami zawierajacymi instancję kluczowych klas i interfejsow biorących udział przy logowaniu użytkownika
	@Autowired
	public UserXAuthTokenController(AuthenticationManager am, CustomUserDetailsService userDetailsService, CustomDaoAuthenticationProvider p) {
		this.authenticationManager = am;
		this.userDetailsService = userDetailsService;
		this.provider = p;
	}

	//Zmapowana metoda obsługująca żądanie POST autoryzujące użytkownika
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<String> authorize(@RequestParam String username, @RequestParam String password) {

		HttpStatus responseStatus = HttpStatus.OK;		
		JsonObject jsonResponse = new JsonObject();
		
		try {	
			
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
			Authentication authentication = this.authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			UserDetails details = this.userDetailsService.loadUserByUsername(username);

			String role ="";
			for (GrantedAuthority authority : details.getAuthorities())
				role = authority.toString();
		
			jsonResponse.addProperty("username", details.getUsername());
			jsonResponse.addProperty("role", role);
			jsonResponse.addProperty("token", tokenUtils.createToken(details)); 
		
		} catch (AuthenticationException failed) {

			responseStatus = HttpStatus.UNAUTHORIZED;
			jsonResponse.addProperty("id", "Authentication Failed");
			jsonResponse.addProperty("description", provider.getLoginFailureError());
		}
			
		return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
}
