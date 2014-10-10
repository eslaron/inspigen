package org.sobiech.inspigen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping
    public String getIndexPage() {
        return "index";
    }
    
    @RequestMapping("/signup")
    public String getSignUpPage() {
        return "index";
    }
    
    @RequestMapping("/forgotPassword")
    public String getForgotPasswordPagePage() {
        return "index";
    }
    
	@RequestMapping("/partials/login")
    public String getLoginPartialPage() {
        return "partials/login";
    }
	
	@RequestMapping("/partials/signup")
    public String getSignupPartialPage() {
        return "partials/signup";
    }
	
	@RequestMapping("/partials/forgotPassword")
    public String getForgotPasswordPartialPage() {
        return "partials/forgotPassword";
    }
}
