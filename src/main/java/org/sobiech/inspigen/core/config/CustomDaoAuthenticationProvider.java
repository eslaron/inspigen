package org.sobiech.inspigen.core.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.sobiech.inspigen.app.domain.user.User;
import org.sobiech.inspigen.app.domain.user.UserRepository;
import org.sobiech.inspigen.core.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

//Klasa zawierająca nadpisane metody odpowiedzialne za autentykację
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

		//Nadpisana metoda wychwytująca nieudane proby logowania i blokująca konta po N nieudanych probach
		@Override
		public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		    
			//Pobieramy nazwę użytkownika podaną podczas logowania
			username = authentication.getName();
			
			//Sprawdzamy czy użytkownik znajduje się w bazie danych
			User userByName = repository.findByUsername(username);
			
		      try {
		    	
		    	//Jeśli użytkownik został znaleziony w bazie  
		    	if (userByName != null) {
		    		
		    		//Jeśli konto jest odblokowane, to wyzeruj liczbę prob i zaktualizuj użytkownika
		    		if(userByName.getAccountNonLocked() == true) {
			    		userByName.setFailedLogins(0);
			    		repository.saveAndFlush(userByName);
		    		}
		    		
		    		//Pobieramy datę ostatniej proby i formatujemy ją
		    		Date lastAttempt  = userByName.getLastLoginAttempt();
			  		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			  		format.format(lastAttempt);
			  		
			  		//Ustawiamy czas po ktorym konto ma być odblokowane
			  		Calendar unlockTime = Calendar.getInstance();
			  		unlockTime = format.getCalendar();
			  		unlockTime.add(Calendar.MINUTE, 15);
			  		
			  		//Jeżeli czas blokady upłynął, to wyzeruj proby, odblokuj i zaktualizuj użytkownika
			  		if(Calendar.getInstance().getTime().after(unlockTime.getTime()) == true) {
			  			userByName.setFailedLogins(0);
			  			userByName.setAccountNonLocked(true);
			  			repository.saveAndFlush(userByName);
			  		}
		      }
		    	  	return super.authenticate(authentication);

		      } catch (BadCredentialsException e) {	
		    	  
		    	  setLoginFailureError("invalidCredentials");
		    	  
		    	//Jeśli użytkownik został znaleziony w bazie 
		    	  if (userByName != null) {
		    		
		    		//Pobierz liczbę nieudanych prob  
		    		int attempts = userByName.getFailedLogins(); 
		    		
		    		//Zwiększ za każdym błędnym logowaniem
		    		attempts++;
		    		userByName.setFailedLogins(attempts);
		    		userByName.setLastLoginAttempt(new Date());
		    		
		    		//Jeśli zostanie przekroczona liczba N prob, to zablokuj konto
		    		if (attempts > 3-1) {
		    			userByName.setAccountNonLocked(false);
		    			setLoginFailureError("accountLocked");
		    		}
					  repository.saveAndFlush(userByName);
		    	  }
			        throw e;
		      }
		}
}