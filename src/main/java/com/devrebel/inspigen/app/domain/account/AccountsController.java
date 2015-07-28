package com.devrebel.inspigen.app.domain.account;

import com.devrebel.inspigen.app.domain.user.IUserService;
import com.devrebel.inspigen.app.domain.user.User;
import com.devrebel.inspigen.app.domain.user.UserRepository;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/accounts")
public class AccountsController {
	
	String message = "";	
	
	@Autowired
    IUserService userService;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
    AccountService accountService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody User data) {

    	return userService.addUser(data);
    }

	@RequestMapping(value ="/{email:.+}", method = RequestMethod.GET)
	public ResponseEntity<String> sendResetPasswordMail(@PathVariable String email) {

		HttpStatus responseStatus = HttpStatus.OK;
		User userFoundByEmail = userRepository.findByEmail(email);

		if(userFoundByEmail != null) {
			userFoundByEmail.toBuilder().passwordTokenExpiration(userService.setTokenExpirationDate());
			userService.updateUser(userFoundByEmail);
			accountService.sendTokenMail(email, "passwordToken", userFoundByEmail.getPasswordToken());
			message = "resetLinkSent";
		} else {
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

    	if(userRepository.findByActivationToken(user.getActivationToken()) != null) {
			if (userService.checkIfTokenExpired("activationToken", user.getActivationToken()) == true) {
				message = "activationLinkExpired";
				responseStatus = HttpStatus.OK;
			} else {
						User userFoundByToken = userRepository.findByActivationToken(user.getActivationToken());

						if(userFoundByToken.getEnabled() == true) {
							responseStatus = HttpStatus.OK;
							message = "alreadyActivated";
						}
							else {
								userFoundByToken.toBuilder().enabled(true);
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

     	if(userRepository.findByPasswordToken(user.getPasswordToken()) != null) {
 			if (userService.checkIfTokenExpired("passwordToken", user.getPasswordToken()) == true) {
 				responseStatus = HttpStatus.OK;
 				message = "resetLinkExpired";
 			} else {
 						User userFoundByToken = userRepository.findByPasswordToken(user.getPasswordToken());

 							String newToken = userService.setToken();
                            String newPassword = userService.encodePassword(userFoundByToken);
 							userFoundByToken.toBuilder()
                                    .password(newPassword)
 							        .passwordToken(newToken);
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
