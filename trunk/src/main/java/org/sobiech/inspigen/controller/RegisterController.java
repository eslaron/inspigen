package org.sobiech.inspigen.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sobiech.inspigen.model.User;
import org.sobiech.inspigen.service.CheckService;
import org.sobiech.inspigen.service.EmailService;
import org.sobiech.inspigen.service.UserService;


@Controller
public class RegisterController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	CheckService checkService;
	
	@Autowired
	EmailService mailService;
	
	private String message;
	
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public @ResponseBody String addUser(@RequestBody User user) {
    	return userService.addUser(user);
    }
    
	@RequestMapping(value = "/accountActivationMessage", method = RequestMethod.GET)
	@ResponseBody
	public String accountActivationMessage(String error) { 
		 return message;
	}
		
	@RequestMapping(value = "*/activationLinkError", method = RequestMethod.GET)
	@ResponseBody
	public String activationLinkError(String error) { 
		 return message;
	}
	  
    @RequestMapping(value = "/sendActivationLink", method = RequestMethod.POST)
    @ResponseBody
    public void sendActivationLink(String email) {
      	
    	if (checkService.checkIfEmailIsRegistered(email) == true) {
			
			/*String token = userService.getToken("activationToken", email);
			
			Date expirationDate = userService.setTokenExpirationDate();
			
			mailService.sendMail(email, token);
			userService.updateTokenExpirationDate("activationToken", email, expirationDate);*/
		
			message = "activationLinkSent";
		}
		else {
			message = "emailNotRegistered";
		}
    }

    
    @RequestMapping(value = "/redirectToLogin", method = RequestMethod.GET)
    public String redirectToLogin() {
		    return "login";
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
