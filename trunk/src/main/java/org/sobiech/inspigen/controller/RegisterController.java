package org.sobiech.inspigen.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sobiech.inspigen.model.User;
import org.sobiech.inspigen.service.CheckService;
import org.sobiech.inspigen.service.UserService;


@Controller
public class RegisterController {
	
	/*@Autowired
	UserService userService;
	
	@Autowired
	CheckService checkService;
	
	private String message;
	
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public @ResponseBody String addUser(@RequestBody User user) {
    	message = userService.addUser(user);		
    	String response = "{\"message\":\"" +message+"\"}";
    		    
    	return response;
    }
    
    @RequestMapping(value = "/activationMessage", method = RequestMethod.GET)
	public @ResponseBody String forgotPasswordMessage(String error) { 
		 return "{\"message\":\"" +message+"\"}";
	}
		 
	@RequestMapping(value="/activateAccount/{token}")
	public String activateAccount(@PathVariable String token) {
	
		if(checkService.checkIfTokenExists("activationToken", token) == true) {
			if (checkService.checkIfTokenExpired("activationToken",token) == true) {
				message = "activationLinkExpired";
			} 
				else message = "accountActivated";
		}
		else {
				message = "invalidActivationLink";
		}
			return "index";
	}	*/
}
