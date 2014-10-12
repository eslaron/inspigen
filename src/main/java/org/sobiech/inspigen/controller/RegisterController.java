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
	
	@Autowired
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
    		
	@RequestMapping(value = "*/activationLinkError", method = RequestMethod.GET)
	@ResponseBody
	public String activationLinkError(String error) { 
		 return message;
	}
	  
	@RequestMapping(value="/activateAccount/{token}", method = RequestMethod.GET)
	public String activateAccount(@PathVariable String token)
	{
		try {
			if (checkService.checkIfTokenExpired("activationToken",token) == true) {
				
				message = "activationLinkExpired";
				return "activationLinkError";
			}
			
			else return "login";
		
		} catch(IndexOutOfBoundsException e) {
			
			message = "invalidResetLink";
			return "activationLinkError";
		}
	}
}
