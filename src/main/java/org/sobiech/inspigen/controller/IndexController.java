package org.sobiech.inspigen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@RequestMapping("/partials/home")
    public String getHomePartialPage() {
        return "partials/home";
    }
	
	@RequestMapping("/partials/forgotPassword")
	public String getForgotPartialPage() {
		return "partials/forgotPassword";
	}
}
