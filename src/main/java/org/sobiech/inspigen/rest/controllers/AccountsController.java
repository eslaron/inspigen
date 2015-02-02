package org.sobiech.inspigen.rest.controllers;

import org.sobiech.inspigen.core.models.dto.UserDto;
import org.sobiech.inspigen.core.models.entities.User;
import org.sobiech.inspigen.core.services.IEmailService;
import org.sobiech.inspigen.core.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;


@RestController							//Oznaczenie jako kontroler typu REST
@RequestMapping("/api/v1/accounts")		//Mapowanie zasobu
public class AccountsController {
	
	String message = "";	
	
	@Autowired
	IUserService userService;
	
	@Autowired
	IEmailService emailService;
	
	//Ządanie POST dodające nowego użytkownika
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody UserDto data) {

    	return userService.addUser(data);
    }
 
    //Ządanie GET pobierające adres email i wysyłające email resetujący hasło
	@RequestMapping(value ="/{email:.+}", method = RequestMethod.GET)
	public ResponseEntity<String> sendResetPasswordMail(@PathVariable String email) {
		
		//Status odpowiedzi
		HttpStatus responseStatus = HttpStatus.OK;
		
		//Wyszukaj użytkownika po emailu
		User userFoundByEmail = userService.findUserByEmail(email);
		
		//Jeśli znaleziono użytkownika, ta ustaw datę wygaśnięcia linku, zaktualizuj użytkownika i wyślij email
		if(userFoundByEmail != null) {
			userFoundByEmail.setPasswordTokenExpiration(userService.setTokenExpirationDate());
			userService.updateUser(userFoundByEmail);
			emailService.sendTokenMail(email, "passwordToken", userFoundByEmail.getPasswordToken());
			message = "resetLinkSent";
		}
		//W przeciwnym przypadku nadaj komunikat o nieznalezieniu użytkownika
		else {
				responseStatus = HttpStatus.NOT_FOUND;
				message = "emailNotRegistered";
		}
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			
			//Wyślij odpowiedź zawierajacą status i komunikat JSON
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
	//Ządanie PUT aktualizujące(aktywujące) użytkownika
    @RequestMapping(value ="/accountActivation",method = RequestMethod.PUT)
	public ResponseEntity<String> activateAccount(@RequestBody User user) {
    	
    	//Status odpowiedzi
    	HttpStatus responseStatus = HttpStatus.NOT_FOUND;
    	message = "invalidActivationLink";

    	//Jeśli istnieje użytkownik, do ktorego należy token
    	if(userService.findUserByToken("activationToken", user.getActivationToken()) != null) {
    		//Jeśli link wygasł to nadaj komunikat o jego wygaśnięciu
			if (userService.checkIfTokenExpired("activationToken", user.getActivationToken()) == true) {
				message = "activationLinkExpired";
				responseStatus = HttpStatus.OK;
			} //W przeciwnym przypadku
				else {
						User userFoundByToken = userService.findUserByToken("activationToken", user.getActivationToken());
						//Jeśli użytkownik jest już aktywny, to nadaj komunikat o aktualnym statusie
						if(userFoundByToken.getEnabled() == true) {
							responseStatus = HttpStatus.OK;
							message = "alreadyActivated";
						} //W przeciwnym wypadku aktywuj użytkownika i nadaj odpowiedni komunikat
							else {
								userFoundByToken.setEnabled(true);
								userService.updateUser(userFoundByToken);
								responseStatus = HttpStatus.OK;
								message = "accountActivated";
							}
				}
		}
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			
			//Wyślij odpowiedź zawierajacą status i komunikat JSON
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    //Ządanie PUT aktualizujące(resetujące) hasło użytkownika
    @RequestMapping(value ="/passwordReset",method = RequestMethod.PUT)
 	public ResponseEntity<String> resetPassword(@RequestBody User user) {
     	
    	//Status odpowiedzi
     	HttpStatus responseStatus = HttpStatus.NOT_FOUND;
     	message = "invalidResetLink";

     	//Jeśli istnieje użytkownik, do ktorego należy token
     	if(userService.findUserByToken("passwordToken", user.getPasswordToken()) != null) {
     		//Jeśli link wygasł to nadaj komunikat o jego wygaśnięciu
 			if (userService.checkIfTokenExpired("passwordToken", user.getPasswordToken()) == true) {
 				responseStatus = HttpStatus.OK;
 				message = "resetLinkExpired";
 			}  //W przeciwnym przypadku
 				else {
 						User userFoundByToken = userService.findUserByToken("passwordToken", user.getPasswordToken());
 					
 							//Zaktualizuj hasło w rekordzie użytkownika
 							String newToken = userService.setToken();
 							userFoundByToken.setPassword(user.getPassword());
 							userFoundByToken.setPasswordToken(newToken);
 							userFoundByToken.setPassword(userService.encodePassword(userFoundByToken));
							userService.updateUser(userFoundByToken);
							responseStatus = HttpStatus.OK;
							message = "passwordChanged";							
 				}
     	}
 			JsonObject jsonResponse = new JsonObject();
 			jsonResponse.addProperty("message", message);
 			
 			//Wyślij odpowiedź zawierajacą status i komunikat JSON
 			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
 	}
}
