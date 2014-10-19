package org.sobiech.inspigen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sobiech.inspigen.service.CheckService;
import org.sobiech.inspigen.service.EmailService;
import org.sobiech.inspigen.service.UserService;

import java.security.Principal;
import java.util.Date;


@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	CheckService checkService;
	
	@Autowired
	EmailService mailService;
	
	private String message;

	// Linki
	
	@RequestMapping
    public String getUserPage() {
        return "user";
    }
	
	@RequestMapping("/profile")
	    public String getLoginPage() {
	        return "user";
	}
	
	// Partiale
	
	@RequestMapping("/partials/profile")
    public String getSignupPartialPage() {
        return "partials/profile";
    }
	
    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Principal principal) {
        return principal.getName();
    }
       
    @RequestMapping(value="/forgotPassword")
	public String forgotPassword()
	{
    	message = "";
		return "forgotPassword"; 
	}
    
	@RequestMapping(value = "/forgotPassowordMessage", method = RequestMethod.GET)
	@ResponseBody
	public String forgotPasswordMessage(String error) { 
		 return message;
	}
		
	@RequestMapping(value = "*/resetLinkError", method = RequestMethod.GET)
	@ResponseBody
	public String resetLinkError(String error) { 
		 return message;
	}
	
	@RequestMapping(value="/resetPassword" , method=RequestMethod.POST)
	public String sendResetLink(@RequestParam(value="email") String email)
	{	
		message = "";
		
		if (checkService.checkIfEmailIsRegistered(email) == true) {
			
			String token = userService.getToken("passwordToken",email);
			
			Date expirationDate = userService.setTokenExpirationDate();
		
			mailService.sendTokenMail(email,"passwordToken", token);
			userService.updateTokenExpirationDate("passwordToken", email, expirationDate);
			
			message = "resetLinkSent";
			return "forgotPassword";
		}
		else {
			
			message = "emailNotRegistered";
			return "forgotPassword";
		}		
	}

	@RequestMapping(value="/newPassword/{token}", method = RequestMethod.GET)
	public String resetPassword(@PathVariable String token)
	{
		try {
			if (checkService.checkIfTokenExpired("passwordToken",token) == true) {
				
				message = "resetLinkExpired";
				return "resetLinkError";
			}
			
			else return "newPassword";
		
		} catch(IndexOutOfBoundsException e) {
			
			message = "invalidResetLink";
			return "resetLinkError";
		}
	}
}
