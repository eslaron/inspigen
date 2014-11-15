package org.sobiech.inspigen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.sobiech.inspigen.model.User;
import org.sobiech.inspigen.service.CheckService;
import org.sobiech.inspigen.service.UserService;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/api/v1/accounts")
public class UserAccountController { 
	
	String message = "";	
	
	@Autowired
	CheckService checkService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<String> activateAccount(@RequestBody User user) {
		if(checkService.checkIfTokenExists("activationToken", user.getActivationToken()) == true) {
			if (checkService.checkIfTokenExpired("activationToken", user.getActivationToken()) == true) {
				message = "activationLinkExpired";
			} 
				else {
						if(checkService.checkIfUserIsActivated(user.getActivationToken()) == true) {
							message = "alreadyActivated";
						}
							else {
								userService.activateAccount(user.getActivationToken());
								message = "accountActivated";
							}
				}
		}
		else {
				message = "invalidActivationLink";
		}
			JsonObject jsonResponse = new JsonObject();
			jsonResponse.addProperty("message", message);
			return new ResponseEntity<String>(jsonResponse.toString(), HttpStatus.OK);
	}
}