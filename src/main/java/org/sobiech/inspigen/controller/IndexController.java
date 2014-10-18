package org.sobiech.inspigen.controller;

import org.sobiech.inspigen.model.User;
import org.sobiech.inspigen.service.CheckService;
import org.sobiech.inspigen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class IndexController {

	
	// Linki
	
	@RequestMapping
    public String getIndexPage() {
        return "index";
    }
    
    @RequestMapping("/signup")
    public String getSignUpPage() {
        return "index";
    }
    
    @RequestMapping("/login")
    public String getLoginPage() {
        return "index";
    }
    
    @RequestMapping("/home")
    public String getHomePage() {
        return "index";
    }
    
    @RequestMapping("/forgotPassword")
    public String getForgotPage() {
        return "index";
    }

    
    // Partiale
    
    @RequestMapping("/partials/signup")
    public String getSignupPartialPage() {
        return "partials/signup";
    }
    
	@RequestMapping("/partials/login")
    public String getLoginPartialPage() {
        return "partials/login";
    }
	
	@RequestMapping("/partials/login2")
    public String getLogin2PartialPage() {
        return "partials/login2";
    }
	
	@RequestMapping("/partials/home")
    public String getHomePartialPage() {
        return "partials/home";
    }
	
	@RequestMapping("/partials/forgotPassword")
	public String getForgotPartialPage() {
		return "partials/forgotPassword";
	}
	
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
    
    @RequestMapping(value = "/activationMessage", method = RequestMethod.GET)
	public @ResponseBody String forgotPasswordMessage(String error) { 
		 return "{\"message\":\"" +message+"\"}";
	}
		 
	@RequestMapping(value="/{token}")
	public String activateAccount(@PathVariable String token) {
	
		if(checkService.checkIfTokenExists("activationToken", token) == true) {
			if (checkService.checkIfTokenExpired("activationToken",token) == true) {
				message = "activationLinkExpired";
			} 
				else if(checkService.checkIfUserIsActivated(token) == true)
					message = "alreadyActivated";
				else message = userService.activateAccount(token);
		}
		else {
				message = "invalidActivationLink";
		}
			return "index";
	}
}
