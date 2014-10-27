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
@RequestMapping(value = "/")
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
   
    @RequestMapping("/forgotPassword")
    public String getForgotPage() {
        return "index";
    }

   
    @RequestMapping("/admin")
    public String getAdminPage() {
        return "index";
    }
	
	@RequestMapping("/admin/settings")
	    public String getSettingsPage() {
	        return "index";
	}



    // Partiale
	
	@RequestMapping("/partials/navbar")
	public String getNavbarPartial() {
	    return "partials/navbar";
	}
	
	@RequestMapping("/partials/admin/navbar")
    public String getAdminNavbarPartial() {
        return "partials/admin/navbar";
	}
	
	@RequestMapping("/partials/admin/sidebar")
    public String getAdminSidebarPartial() {
        return "partials/admin/sidebar";
	}
	
	@RequestMapping("/partials/admin/dashboard")
    public String getAdminDashboardPartial() {
        return "partials/admin/dashboard";
	}
    
    @RequestMapping("/partials/signup")
    public String getSignupPartialPage() {
        return "partials/signup";
    }
    
	@RequestMapping("/partials/login")
    public String getLoginPartialPage() {
        return "partials/login";
    }

	@RequestMapping("/partials/forgotPassword")
	public String getForgotPartialPage() {
		return "partials/forgotPassword";
	}
	
	@RequestMapping("/partials/settings")
    public String getSettingsPartialPage() {
        return "partials/settings";
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
    
    @RequestMapping(value = "/message", method = RequestMethod.GET)
	public @ResponseBody String redarectionMessage(String error) { 
    		message = "admin";
		 return "{\"message\":\"" +message+"\"}";
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
