package org.sobiech.inspigen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sobiech.inspigen.dao.DuplicateUserException;
import org.sobiech.inspigen.model.User;
import org.sobiech.inspigen.service.EmailService;
import org.sobiech.inspigen.service.UserService;

import java.security.Principal;


@Controller
public class LinkController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	EmailService mailService;
	
	private String message;
	
    @RequestMapping(value = "/")
    public String mainPage() {
        return "home";
    }
  
    @RequestMapping(value = "/signup")
    public String signUp() {
        return "signup";
    }
    
    @RequestMapping(value = "/partials/common-view")
    public String common() {
        return "partials/common-view";
    }
   
    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(Principal principal) {
        return principal.getName();
    }
    
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ResponseBody
    public String getIndexPath(String path) {
    	
    	path = "/inspigen";
        return path;
    }
    
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public @ResponseBody String addUser(@RequestBody User user) throws DuplicateUserException {
    	
    	userService.addUser(user);
    	
    	return "Success";
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
	public String resetRequest(@RequestParam(value="email") String email)
	{	
		message = "";
		
		if (userService.checkIfEmailIsRegistered(email) == true) {
			
			String token = userService.getPasswordToken(email);
			
			userService.setPasswordTokenExpirationDate(email);
			mailService.sendMail(email, token);
			
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
			if (userService.checkIfPasswordTokenExpired(token) == true) {
				
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
