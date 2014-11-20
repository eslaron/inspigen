package org.sobiech.inspigen.rest.controllers;

import org.sobiech.inspigen.core.models.entities.User;
import org.sobiech.inspigen.core.services.EmailService;
import org.sobiech.inspigen.core.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import javax.validation.ConstraintViolationException;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
	
	String message = "";	
	
	@Autowired
	UserService userService;
	
	@Autowired
	EmailService emailService;
	
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody User data) {

    	HttpStatus responseStatus = HttpStatus.CREATED;
    	JsonObject jsonResponse = new JsonObject();
    	User userFoundByName = userService.findUserByName(data.getUsername());
    	User userFoundByEmail = userService.findUserByEmail(data.getEmail());
    	
		try {
			
			boolean userNameFound = false;
			boolean emailFound =  false;
				
		    if (userFoundByName != null) {
		    	userNameFound = true;
		    	responseStatus = HttpStatus.CONFLICT;
		    	jsonResponse.addProperty("id", "Resource Conflict");
				jsonResponse.addProperty("description", "duplicateUser");
		    } 	
	        			    		    
		    if (userFoundByEmail != null) {
		    	emailFound = true;
		    	responseStatus = HttpStatus.CONFLICT;
		    	jsonResponse.addProperty("id", "Resource Conflict");
				jsonResponse.addProperty("description", "duplicateEmail");
		    }	
		    
		    if(userNameFound == true && emailFound == true) {
		    	responseStatus = HttpStatus.CONFLICT;
		    	jsonResponse.addProperty("id", "Resource Conflict");
				jsonResponse.addProperty("description", "duplicateUser&Email");
		    }
		    	
		    if(userNameFound == false && emailFound == false) {
		    	userService.createUser(data);
		    	jsonResponse.addProperty("message", "Create Success");
		    }
		} 
		    
		catch (ConstraintViolationException e) {
		
			jsonResponse.addProperty("id", "Create Failed");
			jsonResponse.addProperty("description", e.getConstraintViolations().iterator().next().getMessage());
			
			return new ResponseEntity<String>(jsonResponse.toString(), HttpStatus.PRECONDITION_FAILED);
		} 
		
    	return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
    }
 
	@RequestMapping(value ="/{email:.+}", method = RequestMethod.GET)
	public ResponseEntity<String> sendResetPasswordMail(@PathVariable String email) {
		
		HttpStatus responseStatus = HttpStatus.OK;
		
		User userFoundByEmail = userService.findUserByEmail(email);
		
		if(userFoundByEmail != null) {
			userFoundByEmail.setPasswordTokenExpiration(userService.setTokenExpirationDate());
			userService.updateUser(userFoundByEmail);
			emailService.sendTokenMail(email, "passwordToken", userFoundByEmail.getPasswordToken());
			message = "resetLinkSent";
		}
		else {
				responseStatus = HttpStatus.NOT_FOUND;
				message = "emailNotRegistered";
		}
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    @RequestMapping(value ="/accountActivation",method = RequestMethod.PUT)
	public ResponseEntity<String> activateAccount(@RequestBody User user) {
    	
    	HttpStatus responseStatus = HttpStatus.NOT_FOUND;
    	message = "invalidActivationLink";

    	if(userService.findUserByToken("activationToken", user.getActivationToken()) != null) {
			if (userService.checkIfTokenExpired("activationToken", user.getActivationToken()) == true) {
				message = "activationLinkExpired";
				responseStatus = HttpStatus.OK;
			} 
				else {
						User userFoundByToken = userService.findUserByToken("activationToken", user.getActivationToken());
						if(userFoundByToken.getEnabled() == true) {
							responseStatus = HttpStatus.OK;
							message = "alreadyActivated";
						}
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
			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
	}
    
    @RequestMapping(value ="/passwordReset",method = RequestMethod.PUT)
 	public ResponseEntity<String> resetPassword(@RequestBody User user) {
     	
     	HttpStatus responseStatus = HttpStatus.NOT_FOUND;
     	message = "invalidResetLink";

     	if(userService.findUserByToken("passwordToken", user.getPasswordToken()) != null) {
 			if (userService.checkIfTokenExpired("passwordToken", user.getPasswordToken()) == true) {
 				responseStatus = HttpStatus.OK;
 				message = "resetLinkExpired";
 			} 
 				else {
 						User userFoundByToken = userService.findUserByToken("passwordToken", user.getPasswordToken());
 					
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
 			return new ResponseEntity<String>(jsonResponse.toString(), responseStatus);
 	}
}
